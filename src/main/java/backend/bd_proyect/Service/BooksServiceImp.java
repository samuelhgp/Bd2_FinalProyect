package backend.bd_proyect.Service;

import backend.bd_proyect.Exception.InvalidBookParameterException;
import backend.bd_proyect.Exception.UnauthorizedAccessException;
import backend.bd_proyect.Exception.UnauthorizedDeletionException;
import backend.bd_proyect.Model.BooksModel;
import backend.bd_proyect.Model.Enum.BookState;
import backend.bd_proyect.Model.Enum.Condition;
import backend.bd_proyect.Repository.BooksRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BooksServiceImp implements IBooksService {

    @Autowired
    private BooksRepository booksRepository;

    // Búsqueda de libros por coincidencia en título, autor o género
    @Override
    public List<BooksModel> searchBooks(String query) {
        List<BooksModel> results = new ArrayList<>();
        // Búsqueda por título
        results.addAll(booksRepository.findByTitleContainingIgnoreCase(query));
        // Búsqueda por autor
        results.addAll(booksRepository.findByAuthorContainingIgnoreCase(query));
        // Búsqueda por género (suponiendo que el género se puede buscar como texto en
        // el campo de IDs de ObjectId)
        try {
            ObjectId genreId = new ObjectId(query);
            results.addAll(booksRepository.findByGenreContaining(genreId));
        } catch (IllegalArgumentException e) {
            // Si query no es un ObjectId válido, ignora este filtro
        }
        return results;
    }

    // Filtrado de libros por diferentes criterios
    @Override
    public List<BooksModel> filterBooks(Map<String, String> filters) {
        List<BooksModel> filteredBooks = booksRepository.findAll();

        // Filtro por género (lista de ObjectId)
        if (filters.containsKey("genre")) {
            ObjectId genreId = new ObjectId(filters.get("genre"));
            filteredBooks = booksRepository.findByGenreContaining(genreId);
        }

        // Filtro por autor
        if (filters.containsKey("author")) {
            filteredBooks = booksRepository.findByAuthor(filters.get("author"));
        }

        // Filtro por condición (enum Condition)
        if (filters.containsKey("condition")) {
            try {
                Condition condition = Condition.valueOf(filters.get("condition").toUpperCase());
                filteredBooks = booksRepository.findByCondition(condition);
            } catch (IllegalArgumentException e) {
                // Manejar el caso en que el valor no es una condición válida
            }
        }

        // Filtro por estado del libro (enum BookState)
        if (filters.containsKey("state")) {
            try {
                BookState state = BookState.valueOf(filters.get("state").toUpperCase());
                filteredBooks = booksRepository.findByState(state);
            } catch (IllegalArgumentException e) {
                // Manejar el caso en que el valor no es un estado válido
            }
        }

        // Filtro por usuario (idUser)
        if (filters.containsKey("idUser")) {
            ObjectId userId = new ObjectId(filters.get("idUser"));
            filteredBooks = booksRepository.findByIdUser(userId);
        }

        return filteredBooks;
    }

    // Agregar un nuevo libro a la base de datos
    @Override
    public BooksModel addBook(BooksModel book) {
        // Validación de parámetros
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new InvalidBookParameterException("El título no puede estar vacío.");
        }
        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new InvalidBookParameterException("El autor no puede estar vacío.");
        }
        if (book.getCondition() == null) {
            throw new InvalidBookParameterException("La condición del libro no puede ser nula.");
        }
        if (book.getGenre() == null || book.getGenre().isEmpty()) {
            throw new InvalidBookParameterException("El género no puede estar vacío.");
        }

        BooksModel newBook = booksRepository.save(book);
        System.out.println("Libro agregado exitosamente.");
        return newBook;
    }

    // Actualizar los detalles de un libro existente
    @Override
    public BooksModel updateBook(String id, BooksModel book, String userId) {
        // Busca el libro existente en la base de datos
        BooksModel existingBook = booksRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new InvalidParameterException("Libro no encontrado con el ID proporcionado"));

        // Verifica si el usuario actual es el propietario del libro
        if (!existingBook.getIdUser().toHexString().equals(userId)) {
            throw new UnauthorizedAccessException("No tienes permiso para modificar este libro");
        }

        // Actualiza los datos del libro existente
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setGenre(book.getGenre());
        existingBook.setCondition(book.getCondition());
        existingBook.setCover_image(book.getCover_image());

        return booksRepository.save(existingBook);
    }

    // Eliminar un libro de la base de datos
    @Override
    public void deleteBook(String id, String idUser) {
        BooksModel book = booksRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    
        // Verifica si el idUser coincide con el propietario
        if (!book.getIdUser().toHexString().equals(idUser)) {
            throw new UnauthorizedDeletionException("No puedes eliminar este libro porque no eres el propietario.");
        }
    
        booksRepository.deleteById(new ObjectId(id));
    }

    // Obtener un libro específico por su ID
    @Override
    public BooksModel getBookById(String id) {
        return booksRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
