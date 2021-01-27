package tr.com.obss.jss.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name ="book")
public class Book extends EntityBase {

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "publishDate")
    private Date publishDate;

    @ManyToMany(mappedBy = "favoriteList")
    @JsonBackReference
    private List<User> usersWhoFav;

    @ManyToMany(mappedBy = "readList")
    @JsonBackReference
    private List<User> usersWhoRead;


    public List<User> getUsersWhoFav() {
        return usersWhoFav;
    }

    public void setUsersWhoFav(List<User> usersWhoFav) {
        this.usersWhoFav = usersWhoFav;
    }

    public List<User> getUsersWhoRead() {
        return usersWhoRead;
    }

    public void setUsersWhoRead(List<User> usersWhoRead) {
        this.usersWhoRead = usersWhoRead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}