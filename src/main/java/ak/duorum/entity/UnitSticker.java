package ak.duorum.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/25/14
 */
@Entity
@Table(name = "V_UNIT_STICKERS")
@Immutable
public class UnitSticker implements Serializable {

    private static final long serialVersionUID = -3737088855331021387L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "LOT")
    private String lot;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "BARCODE")
    private String barcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitSticker that = (UnitSticker) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UnitSticker{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", lot='" + lot + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}
