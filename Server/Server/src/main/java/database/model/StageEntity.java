package database.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "stage", schema = "public", catalog = "competition2")
public class StageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_stage", nullable = false)
    private long idStage;
    @Basic
    @Column(name = "name_stage", nullable = false, length = -1)
    private String nameStage;
    @Basic
    @Column(name = "status", nullable = false)
    private boolean status;

    public long getIdStage() {
        return idStage;
    }

    public void setIdStage(long idStage) {
        this.idStage = idStage;
    }

    public String getNameStage() {
        return nameStage;
    }

    public void setNameStage(String nameStage) {
        this.nameStage = nameStage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StageEntity that = (StageEntity) o;
        return idStage == that.idStage && status == that.status && Objects.equals(nameStage, that.nameStage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStage, nameStage, status);
    }
}
