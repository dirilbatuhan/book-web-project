package tr.com.obss.jss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jss.entity.Book;
import tr.com.obss.jss.model.BookDTO;
import tr.com.obss.jss.service.BookService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService; //buradaki constructorla autowired kısmını anlamadım

    @PostMapping("/add-book")//kitap ekleme
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<?> addBook(@Valid @RequestBody BookDTO bookDTO){

        Book book = bookService.save(bookDTO);

        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")//kitap silme
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity <?> delete(@PathVariable long id){
        Book book = bookService.delete(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")//kitap güncelleme
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<?> update (@PathVariable long id, @Valid @RequestBody BookDTO bookDTO ){
        Book book = bookService.update(id, bookDTO);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/list-of-book")
    @ResponseBody
    public ResponseEntity<?> listOfBook(@RequestParam(name ="pageSize", defaultValue = "2") int pageSize,
                                        @RequestParam(name ="pageNumber", defaultValue = "0") int pageNumber){
        Page<Book> books = bookService.findAll(pageSize, pageNumber);
        return ResponseEntity.ok(books);
    }
    @GetMapping("/search-by-name")
    @ResponseBody
    public ResponseEntity<?> searchByName(@RequestParam (name ="name", defaultValue = "")String name){
        Book book = bookService.findByName(name);
        return ResponseEntity.ok(book);
    }
    @GetMapping("/search-by-author")
    @ResponseBody
    public ResponseEntity<?> SearchByAuthor(@RequestParam (name ="author", defaultValue = "")String author){
        List<Book> bookList = bookService.findByAuthor(author);
        return ResponseEntity.ok(bookList);
    }

}