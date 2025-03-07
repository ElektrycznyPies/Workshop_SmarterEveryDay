package pl.coderslab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "first_name")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 100)
    @Column(name = "nick")
    private String nick;
    @NotBlank
    @Email
    @Size(max = 50)
    private String email;
    @Column(length = 255)
    private String password;
    @CreationTimestamp
    private Timestamp created_at;
    @UpdateTimestamp
    private Timestamp updated_at;
    @Column(nullable = false)
    private Long role = 0L; // 0 = user, 1 = admin, other numbers for future roles


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_packet",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "packet_id")
    )
    private Set<Packet> packets = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<StudySession> studySessions;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, String password,
                Timestamp created_at, Timestamp updated_at, Long role, String nick) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.role = role;
        this.nick = nick;
    }

    public Long getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Set<StudySession> getStudySessions() {
        return studySessions;
    }

    public void setStudySessions(Set<StudySession> studySessions) {
        this.studySessions = studySessions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
    public Set<Packet> getPackets() {
    if (this.packets == null) {
        this.packets = new HashSet<>();
    }
    return this.packets;
}

    public void setPackets(Set<Packet> packets) {
        this.packets = packets;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }
}
