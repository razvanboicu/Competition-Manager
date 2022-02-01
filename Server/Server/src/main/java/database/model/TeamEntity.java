package database.model;

import jakarta.persistence.*;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.util.Objects;

@Entity
@Table(name = "team", schema = "public", catalog = "competition2")
public class TeamEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_team", nullable = false)
    private long idTeam;
    @Basic
    @Column(name = "name_team", nullable = false, length = -1)
    private String nameTeam;

    public long getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(long idTeam) {
        this.idTeam = idTeam;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamEntity that = (TeamEntity) o;
        return idTeam == that.idTeam && Objects.equals(nameTeam, that.nameTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTeam, nameTeam);
    }
}
