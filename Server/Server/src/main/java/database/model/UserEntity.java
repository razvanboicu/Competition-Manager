package database.model;

import database.model.enums.Role;
import database.model.util.RoleConverter;
import jakarta.persistence.*;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.util.Objects;

@Entity
@Table(name = "user", schema = "public", catalog = "competition2")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    private long idUser;
    @Basic
    @Column(name = "username", nullable = false, length = -1)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = -1)
    private String password;
    @Basic
    @Column(name = "first_name", nullable = false, length = -1)
    private String firstName;
    @Basic
    @Column(name = "second_name", nullable = false, length = -1)
    private String secondName;

    @Basic
    @Column(name = "role", nullable = false)
    @Convert(converter = RoleConverter.class)
    private Role role;

    @Basic
    @Column(name = "id_team", nullable = false)
    private long idTeam;

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(long idTeam) {
        this.idTeam = idTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return idUser == that.idUser && idTeam == that.idTeam && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(firstName, that.firstName) && Objects.equals(secondName, that.secondName) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, username, password, firstName, secondName, role, idTeam);
    }
}
