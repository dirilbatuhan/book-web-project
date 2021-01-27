package tr.com.obss.jss.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class EntityBase implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="update_date")
    private Date updateDate;

    @Column(name="active")
    private boolean active;

    @Column(name="operation")
    private String operation;

    @PrePersist
    public void onPrePersist(){
        this.setOperation("save");
        this.setCreateDate(new Date());
        this.setUpdateDate(new Date());
        this.setActive(true);
    }

    @PreUpdate
    public void onPreUpdate(){
        this.setOperation("update");
        this.setUpdateDate(new Date());
    }

    @PreRemove
    public void onPreRemove(){
        this.setOperation("delete");
        this.setUpdateDate(new Date());
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}