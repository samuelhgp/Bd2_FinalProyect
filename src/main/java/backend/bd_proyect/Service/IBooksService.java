package backend.bd_proyect.Service;

import backend.bd_proyect.Model.BooksModel;
import java.util.List;
import java.util.Map;

public interface IBooksService {

    // Firma del método de búsqueda de libros
    List<BooksModel> searchBooks(String query);

    // Firma del método de filtrado de libros
    List<BooksModel> filterBooks(Map<String, String> filters);

    // Firma del método para añadir un nuevo libro
    BooksModel addBook(BooksModel book);

    // Firma del método para actualizar los detalles de un libro
    BooksModel updateBook(String id, BooksModel book, String userId);

    // Firma del método para eliminar un libro por su ID
    void deleteBook(String id, String idUser);

    // Firma del método para obtener un libro específico por su ID
    BooksModel getBookById(String id);

    List<BooksModel>bookStates();

    List<BooksModel>booksAvalibles();

    List<BooksModel>allCommentsBooks();

    List<BooksModel>BooksSortByComments();
}
