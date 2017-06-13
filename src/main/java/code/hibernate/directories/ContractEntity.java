package code.hibernate.directories;

import code.hibernate.IModel;
import javafx.beans.property.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Алексей on 22.04.2017.
 */
@Entity
@Table(name = "CONTRACT", schema = "sovtehlit")
public class ContractEntity implements IModel {
    private int id;
    private String name;
    private Date date;
    private int customerId;

    private StringProperty nameP = new SimpleStringProperty();
    private ObjectProperty<LocalDate> dateP = new SimpleObjectProperty<>();

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setNameP(name);
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        setDateP(date.toLocalDate());
    }

    @Basic
    @Column(name = "customer_id", nullable = false)
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractEntity that = (ContractEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (customerId != that.customerId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + customerId;
        return result;
    }

    @Transient
    public String getNameP() {
        return nameP.get();
    }

    public StringProperty namePProperty() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP.set(nameP);
    }

    @Transient
    public LocalDate getDateP() {
        return dateP.get();
    }

    public ObjectProperty<LocalDate> datePProperty() {
        return dateP;
    }

    public void setDateP(LocalDate dateP) {
        this.dateP.set(dateP);
    }
}
