package tr.com.obss.jss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tr.com.obss.jss.entity.Book;
import tr.com.obss.jss.entity.User;
import tr.com.obss.jss.model.BookDTO;
import tr.com.obss.jss.model.UserDTO;
import tr.com.obss.jss.repository.BookRepository;
import tr.com.obss.jss.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public Book save(BookDTO bookDTO){  //kitap eklemek
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublishDate(bookDTO.getPublishDate());
        book.setUpdateDate(new Date());
        book.setCreateDate(new Date());
        book.setActive(true);
        Book savedBook = bookRepository.save(book);

        return savedBook;
    }

    public Book update(long id, BookDTO bookDTO){   //kitap güncellemek
        Optional<Book> byId = bookRepository.findById(id);
        if(byId.isPresent()){
            Book book = byId.get();
            book.setName(bookDTO.getName());
            book.setAuthor(bookDTO.getName());
            book.setPublishDate(bookDTO.getPublishDate());
            return bookRepository.save(book);
        }
        throw new IllegalArgumentException("Kitap bulunamadi");
    }

    public Book delete(long id){    //kitap silmek
        Optional<Book> byId = bookRepository.findById(id);
        if(byId.isPresent()){
            Book book = byId.get();
            book.setActive(false);
            return bookRepository.save(book);
        }
        throw new IllegalArgumentException("Kitap bulunamadi");
    }

    public Page<Book> findAll(int pageSize, int pageNumber){

        Pageable paged = PageRequest.of(pageNumber, pageSize);

        return bookRepository.findAll(paged);
    }

    public Book findByName(String name){
        return bookRepository.findByName(name);
    }
    public List<Book> findByAuthor(String author){
        return bookRepository.findByAuthor(author);
    }


}
