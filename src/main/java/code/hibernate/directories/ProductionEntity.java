package code.hibernate.directories;

import code.hibernate.IModel;
import javafx.beans.property.*;

import javax.persistence.*;

/**
 * Created by Алексей on 22.04.2017.
 */
@Entity
@Table(name = "PRODUCTION", schema = "sovtehlit")
public class ProductionEntity implements IModel {
    private int id;
    private int material_id;
    private String dse;
    private String name;
    private double netWeight;
    private double fillerWeightForm;
    private int countInForm;
    private double weightForm;

    private StringProperty dseP = new SimpleStringProperty();
    private StringProperty nameP = new SimpleStringProperty();
    private DoubleProperty netWeightP = new SimpleDoubleProperty();
    private DoubleProperty fillerWeightFormP = new SimpleDoubleProperty();
    private IntegerProperty countInFormP = new SimpleIntegerProperty();
    private DoubleProperty weightFormP = new SimpleDoubleProperty();

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name="material_id",nullable = false)
    public int getMaterialId(){return material_id;}

    public void setMaterialId(int material_id){
        this.material_id = material_id;
    }

    @Basic
    @Column(name = "dse", nullable = false, length = 255)
    public String getDse() {
        return dse;
    }

    public void setDse(String dse) {
        this.dse = dse;
        setDseP(dse);
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
    @Column(name = "net_weight", nullable = false, precision = 0)
    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
        setNetWeightP(netWeight);
    }

    @Basic
    @Column(name = "filler_weight_form", nullable = false, precision = 0)
    public double getFillerWeightForm() {
        return fillerWeightForm;
    }

    public void setFillerWeightForm(double fillerWeightForm) {
        this.fillerWeightForm = fillerWeightForm;
        setFillerWeightFormP(fillerWeightForm);
    }

    @Basic
    @Column(name = "count_in_form", nullable = false)
    public int getCountInForm() {
        return countInForm;
    }

    public void setCountInForm(int countInForm) {
        this.countInForm = countInForm;
        setCountInFormP(countInForm);
    }

    @Basic
    @Column(name = "weight_form", nullable = false, precision = 0)
    public double getWeightForm() {
        return weightForm;
    }

    public void setWeightForm(double weightForm) {
        this.weightForm = weightForm;
        setWeightFormP(weightForm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductionEntity that = (ProductionEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.netWeight, netWeight) != 0) return false;
        if (Double.compare(that.fillerWeightForm, fillerWeightForm) != 0) return false;
        if (countInForm != that.countInForm) return false;
        if (Double.compare(that.weightForm, weightForm) != 0) return false;
        if (dse != null ? !dse.equals(that.dse) : that.dse != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (dse != null ? dse.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(netWeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fillerWeightForm);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + countInForm;
        temp = Double.doubleToLongBits(weightForm);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Transient
    public String getDseP() {
        return dseP.get();
    }

    public StringProperty dsePProperty() {
        return dseP;
    }

    public void setDseP(String dseP) {
        this.dseP.set(dseP);
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
    public double getNetWeightP() {
        return netWeightP.get();
    }

    public DoubleProperty netWeightPProperty() {
        return netWeightP;
    }

    public void setNetWeightP(double netWeightP) {
        this.netWeightP.set(netWeightP);
    }

    @Transient
    public double getFillerWeightFormP() {
        return fillerWeightFormP.get();
    }

    public DoubleProperty fillerWeightFormPProperty() {
        return fillerWeightFormP;
    }

    public void setFillerWeightFormP(double fillerWeightFormP) {
        this.fillerWeightFormP.set(fillerWeightFormP);
    }

    @Transient
    public int getCountInFormP() {
        return countInFormP.get();
    }

    public IntegerProperty countInFormPProperty() {
        return countInFormP;
    }

    public void setCountInFormP(int countInFormP) {
        this.countInFormP.set(countInFormP);
    }

    @Transient
    public double getWeightFormP() {
        return weightFormP.get();
    }

    public DoubleProperty weightFormPProperty() {
        return weightFormP;
    }

    public void setWeightFormP(double weightFormP) {
        this.weightFormP.set(weightFormP);
    }
}
