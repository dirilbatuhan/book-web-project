package tr.com.obss.jss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jss.entity.Book;
import tr.com.obss.jss.entity.User;
import tr.com.obss.jss.model.BookDTO;
import tr.com.obss.jss.model.UserDTO;
import tr.com.obss.jss.model.UserUpdateDTO;
import tr.com.obss.jss.service.UserService;


import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<?> get(@RequestParam(name ="pageSize", defaultValue = "2") int pageSize,
                                 @RequestParam(name ="pageNumber", defaultValue = "0") int pageNumber){
        return ResponseEntity.ok(userService.findAll(pageSize, pageNumber));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable long id){
        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }
        throw new IllegalArgumentException("Kullanici bulunamadi");
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<?> get(@RequestParam(name = "username", defaultValue = "") String username){
        List<User> userList = userService.findByUsername(username);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/has-role-user")
    @ResponseBody
    public ResponseEntity<?> findByRoles(){
        List<User> userList = userService.findByRoles(Arrays.asList("role_user"));
        return ResponseEntity.ok(userList);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody UserUpdateDTO userDTO){
        User user = userService.update(id, userDTO);
        return ResponseEntity.ok(user);

    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable long id){
        User user = userService.delete(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<?> post(@Valid @RequestBody UserDTO userDTO){

        User user = userService.save(userDTO);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/add-favorite")
    @ResponseBody
    public ResponseEntity <?> addToFavoriteList(@PathVariable long id, @RequestBody BookDTO bookDTO){

        List <Book> books = userService.addToFavoriteList(id,bookDTO);

        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}/delete-favorite")
    @ResponseBody
    public ResponseEntity <?> deleteFromFavoriteList(@PathVariable long id, @RequestBody BookDTO bookDTO){
        List <Book> books = userService.deleteFromFavoriteList(id,bookDTO);

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}/add-read")
    @ResponseBody
    public ResponseEntity <?> addToReadList(@PathVariable long id, @RequestBody BookDTO bookDTO){
        List <Book> books = userService.addToReadList(id,bookDTO);

        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}/delete-read")
    @ResponseBody
    public ResponseEntity <?> deleteFromReadList(@PathVariable long id, @RequestBody BookDTO bookDTO){
        List <Book> books = userService.deleteFromReadList(id,bookDTO);

        return ResponseEntity.ok(books);
    }

}