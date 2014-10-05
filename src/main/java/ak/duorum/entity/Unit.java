package ak.duorum.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/15/14
 */
@Entity
@Table(name = "UNITS")
@DynamicInsert
@DynamicUpdate
public class Unit implements Serializable {

    private static final long serialVersionUID = 6412234569604364781L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE", insertable = false, updatable = false)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFICATION_DATE", insertable = false, updatable = false)
    private Date lastModificationDate;

    @NotNull
    @Size(max = 128)
    @Column(name = "DESIGNATION", nullable = false, unique = true, length = 128)
    private String designation;

    @NotNull
    @Size(min = 4, max = 4, message = "{validation.staticLength.message}")
    @Column(name = "CODE", nullable = false, length = 4)
    private String code;

    @Size(max = 512)
    @Column(name = "NAME", length = 512)
    private String name;

    @Size(max = 512)
    @Column(name = "DESCRIPTION", length = 512)
    private String description;

    @Size(max = 128)
    @Column(name = "LABEL_NAME", length = 128)
    private String labelName;

    @Size(max = 512)
    @Column(name = "LABEL_DESCRIPTION", length = 512)
    private String labelDescription;

    @Size(min = 2, max = 2, message = "{validation.staticLength.message}")
    @Column(name = "LANGUAGE", nullable = false, length = 2)
    private String language;

    @Size(min = 4, max = 4, message = "{validation.staticLength.message}")
    @Column(name = "LOT", length = 4)
    private String lot;

    @Size(min = 5, max = 5, message = "{validation.staticLength.message}")
    @Column(name = "SERIAL_NUMBER", length = 5)
    private String serialNumber;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STICKER_STATUS", nullable = false)
    private StickerStatus stickerStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelDescription() {
        return labelDescription;
    }

    public void setLabelDescription(String labelDescription) {
        this.labelDescription = labelDescription;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public StickerStatus getStickerStatus() {
        return stickerStatus;
    }

    public void setStickerStatus(StickerStatus stickerStatus) {
        this.stickerStatus = stickerStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit unit = (Unit) o;

        if (id != null ? !id.equals(unit.id) : unit.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", lastModificationDate=" + lastModificationDate +
                ", designation='" + designation + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", labelName='" + labelName + '\'' +
                ", labelDescription='" + labelDescription + '\'' +
                ", language='" + language + '\'' +
                ", lot='" + lot + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", stickerStatus=" + stickerStatus +
                '}';
    }
}
