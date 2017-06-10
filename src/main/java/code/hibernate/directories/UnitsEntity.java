package code.hibernate.directories;

import code.hibernate.IModel;
import javafx.beans.property.*;

import javax.persistence.*;

/**
 * Created by Алексей on 22.04.2017.
 */
@Entity
@Table(name = "UNITS", schema = "sovtehlit")
public class UnitsEntity implements IModel {
    private int id;
    private String name;
    private double coefficient;

    private IntegerProperty idP = new SimpleIntegerProperty();
    private StringProperty nameP = new SimpleStringProperty();
    private DoubleProperty coefficientP = new SimpleDoubleProperty();

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        setIdP(id);
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
    @Column(name = "coefficient", nullable = false, precision = 0)
    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
        setCoefficientP(coefficient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitsEntity that = (UnitsEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.coefficient, coefficient) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(coefficient);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Transient
    public int getIdP() {
        return idP.get();
    }

    public IntegerProperty idPProperty() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP.set(idP);
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
    public double getCoefficientP() {
        return coefficientP.get();
    }

    public DoubleProperty coefficientPProperty() {
        return coefficientP;
    }

    public void setCoefficientP(double coefficientP) {
        this.coefficientP.set(coefficientP);
    }
}
