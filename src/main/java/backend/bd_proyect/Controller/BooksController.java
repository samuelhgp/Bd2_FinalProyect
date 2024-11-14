package backend.bd_proyect.Controller;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.bd_proyect.Exception.InvalidBookParameterException;
import backend.bd_proyect.Exception.UnauthorizedAccessException;
import backend.bd_proyect.Exception.UnauthorizedDeletionException;
import backend.bd_proyect.Model.BooksModel;
import backend.bd_proyect.Service.IBooksService;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private IBooksService booksService;

    @GetMapping("/allBooksStates")
    public ResponseEntity<List<BooksModel>> allStates(){
        List<BooksModel> Books = booksService.bookStates();
        return new ResponseEntity<>(Books, HttpStatus.OK);
    }

    @GetMapping("/Avalibles")
    public ResponseEntity<List<BooksModel>> AvalibleBooks(){
        List<BooksModel> books = booksService.booksAvalibles();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/allCommentsBooks")
    public ResponseEntity<List<BooksModel>> allComments(){
        List<BooksModel> comments = booksService.allCommentsBooks();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/BooksSort")
    public ResponseEntity<List<BooksModel>> sortComments(){
        List<BooksModel> comments = booksService.BooksSortByComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    // Publicar un nuevo libro para intercambio
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BooksModel book) {
        try {
            booksService.addBook(book);
            return new ResponseEntity<>("Libro agregado exitosamente!", HttpStatus.CREATED);
        } catch (InvalidBookParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar los detalles de un libro existente
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable("id") String id,
            @RequestParam("userId") String userId,
            @RequestBody BooksModel book) {
        try {
            booksService.updateBook(id, book, userId); // Pasar `userId` como argumento
            return ResponseEntity.ok("Libro actualizado con éxito 🙂");
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Eliminar un libro de la lista de publicaciones
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") String id, @RequestParam("idUser") String idUser) {
        try {
            booksService.deleteBook(id, idUser);
            return ResponseEntity.ok("Libro eliminado con éxito 👍🏻");
        } catch (UnauthorizedDeletionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // Obtener los detalles de un libro específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<BooksModel> getBookById(@PathVariable("id") String id) {
        BooksModel book = booksService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // Búsqueda de libros por consulta en título o autor
    @GetMapping("/search")
    public ResponseEntity<List<BooksModel>> searchBooks(@RequestParam String query) {
        List<BooksModel> results = booksService.searchBooks(query);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    // Filtrado de libros por varios criterios
    @GetMapping("/filter")
    public ResponseEntity<List<BooksModel>> filterBooks(@RequestParam Map<String, String> filters) {
        List<BooksModel> filteredBooks = booksService.filterBooks(filters);
        return new ResponseEntity<>(filteredBooks, HttpStatus.OK);
    }
}
