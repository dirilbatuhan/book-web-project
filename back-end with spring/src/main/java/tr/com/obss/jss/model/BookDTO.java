package tr.com.obss.jss.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

public class BookDTO {

    @NotBlank
    @Size(max = 30, min = 1, message = "gecerli bir kitap ismi giriniz")
    private String name;

    @NotBlank
    @Size(max = 30, min = 1, message = "gecerli bir yazar ismi giriniz")
    private String author;


    private Date publishDate; //YYYY-MM-DD

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
