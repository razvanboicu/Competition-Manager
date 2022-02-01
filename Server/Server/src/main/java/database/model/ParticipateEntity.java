package database.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "participate", schema = "public", catalog = "competition2")
public class ParticipateEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_participate", nullable = false)
    private long idParticipate;
    @Basic
    @Column(name = "id_stage", nullable = false)
    private long idStage;
    @Basic
    @Column(name = "id_user", nullable = false)
    private long idUser;
    @Basic
    @Column(name = "score", nullable = true)
    private Integer score;

    public long getIdParticipate() {
        return idParticipate;
    }

    public void setIdParticipate(long idParticipate) {
        this.idParticipate = idParticipate;
    }

    public long getIdStage() {
        return idStage;
    }

    public void setIdStage(long idStage) {
        this.idStage = idStage;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public Integer getScore() {
        if(score==null) return 0;
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipateEntity that = (ParticipateEntity) o;
        return idParticipate == that.idParticipate && idStage == that.idStage && idUser == that.idUser && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParticipate, idStage, idUser, score);
    }
}
