//package pl.coderslab.model;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;
//import java.sql.Timestamp;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "Packets")
//public class Packet {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @NotBlank
//    @Size(max=100)
//    private String name;
//    @Size(max=1000)
//    private String description;
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "packet_show_fields", joinColumns = @JoinColumn(name = "packet_id"))
//    @Column(name = "show_field")
//    private Set<String> showFields = new HashSet<>();
//    @Column(name = "compare_field")
//    private String compareField;
//    @CreationTimestamp
//    private Timestamp created_at;
//    @UpdateTimestamp
//    private Timestamp updated_at;
//
//    @ManyToMany(mappedBy = "packets", fetch = FetchType.EAGER)
//    private Set<User> users;
//
//    @OneToMany(mappedBy = "pack", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Flashcard> flashcards;
//
//    @OneToMany(mappedBy = "packet", fetch = FetchType.EAGER)
//    private Set<StudySession> studySessions;
package pl.coderslab.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Packets")
public class Packet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=100)
    private String name;

    @Size(max=1000)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "packet_show_fields", joinColumns = @JoinColumn(name = "packet_id"))
    @Column(name = "show_field")
    private Set<String> showFields = new HashSet<>();

    @Column(name = "compare_field")
    private String compareField;

    @CreationTimestamp
    private Timestamp created_at;

    @UpdateTimestamp
    private Timestamp updated_at;

    @ManyToMany(mappedBy = "packets", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "pack", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Flashcard> flashcards;

    @OneToMany(mappedBy = "packet", fetch = FetchType.EAGER)
    private Set<StudySession> studySessions;



    public Packet() {
    }

    public Packet(Long id, String name, String description, Set<String> showFields, String compareField,
                  Timestamp created_at, Timestamp updated_at, Set<User> users, Set<Flashcard> flashcards, Set<StudySession> studySessions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.showFields = showFields;
        this.compareField = compareField;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.users = users;
        this.flashcards = flashcards;
        this.studySessions = studySessions;
    }


    public Set<String> getShowFields() {
        return showFields;
    }

    public void setShowFields(Set<String> showFields) {
        this.showFields = showFields;
    }

    public String getCompareField() {
        return compareField;
    }

    public void setCompareField(String compareField) {
        this.compareField = compareField;
    }

    public Set<StudySession> getStudySessions() {
        return studySessions;
    }

    public void setStudySessions(Set<StudySession> studySessions) {
        this.studySessions = studySessions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(Set<Flashcard> flashcards) {
        this.flashcards = flashcards;
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

    @Override
    public String toString() {
        return "Packet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", showFields=" + showFields +
                ", compareField='" + compareField + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", users=" + users +
                ", flashcards=" + flashcards +
                ", studySessions=" + studySessions +
                '}';
    }
}
