package tr.com.obss.jss.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends EntityBase {

    @Column(name = "username", length = 255, unique = true)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @JsonManagedReference
    private List<Role> roles;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name ="read_list", joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")})
    @JsonManagedReference
    private List<Book> readList;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name ="favorite_list", joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")})
    @JsonManagedReference
    private List<Book> favoriteList;

    public List<Book> getReadList() {
        return readList;
    }

    public void setReadList(List<Book> readList) {
        this.readList = readList;
    }

    public List<Book> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<Book> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
