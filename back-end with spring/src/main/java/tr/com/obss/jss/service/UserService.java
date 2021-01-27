package tr.com.obss.jss.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jss.entity.Book;
import tr.com.obss.jss.entity.User;
import tr.com.obss.jss.model.BookDTO;
import tr.com.obss.jss.model.MyUserDetails;
import tr.com.obss.jss.model.UserDTO;
import tr.com.obss.jss.model.UserUpdateDTO;
import tr.com.obss.jss.repository.BookRepository;
import tr.com.obss.jss.repository.RoleRepository;
import tr.com.obss.jss.repository.UserRepository;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    public User save(UserDTO userDto){

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_user")));
        user.setUpdateDate(new Date());
        user.setCreateDate(new Date());
        user.setActive(true);
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    public Page<User> findAll(int pageSize, int pageNumber){

        Pageable paged = PageRequest.of(pageNumber, pageSize);

        return userRepository.findAll(paged);
    }

    public List<User> findByRoles(List<String> roles){

        return userRepository.findByRoles_NameIn(roles);
    }

    public Optional<User> findById(long id){

        return userRepository.getById(id);
    }

    public List<User> findByUsername(String name){

        return userRepository.findByUsernameStartingWithAndOperationIsNotNullAndActiveTrueOrderByIdDesc(name);
    }

    public User update(long id, UserUpdateDTO dto){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            User user = byId.get();
            user.setPassword(encoder.encode(dto.getPassword()));

            return userRepository.save(user);
        }
        throw new IllegalArgumentException("Kullanici bulunamadi");
    }

    public User delete (long id){
        Optional<User> byId  = userRepository.findById(id);
        if(byId.isPresent()){
            User user = byId.get();
            user.setActive(!user.isActive());

            return userRepository.save(user);
        }
        throw new IllegalArgumentException("Kullanici bulunamadi");
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if(byUsername.isPresent()){

            return new MyUserDetails(byUsername.get());
        }
        throw new UsernameNotFoundException("Kullanici bulunamadi");
    }


    public List<Book> addToFavoriteList(long id, BookDTO bookDTO){

        Optional <User> byId = userRepository.getByIdNative(id);
        Book book = bookRepository.findByName(bookDTO.getName());
        byId.get().getFavoriteList().add(book);
        userRepository.save(byId.get());
        return byId.get().getFavoriteList();
    }

    public List<Book> addToReadList(long id, BookDTO bookDTO){
        Optional <User> byId = userRepository.getByIdNative(id);
        Book book = bookRepository.findByName(bookDTO.getName());
        byId.get().getReadList().add(book);
        userRepository.save(byId.get());
        return byId.get().getReadList();
    }

    public List<Book> deleteFromFavoriteList(long id, BookDTO bookDTO){
        Optional <User> byId = userRepository.getByIdNative(id);
        Book book = bookRepository.findByName(bookDTO.getName());
        int willDelete = byId.get().getFavoriteList().indexOf(book);
        byId.get().getFavoriteList().remove(willDelete);
        userRepository.save(byId.get());
        return byId.get().getFavoriteList();
    }

    public List<Book> deleteFromReadList(long id, BookDTO bookDTO){
        Optional <User> byId = userRepository.getByIdNative(id);
        Book book = bookRepository.findByName(bookDTO.getName());
        int willDelete = byId.get().getReadList().indexOf(book);
        byId.get().getReadList().remove(willDelete);
        userRepository.save(byId.get());
        return byId.get().getReadList();
    }

}