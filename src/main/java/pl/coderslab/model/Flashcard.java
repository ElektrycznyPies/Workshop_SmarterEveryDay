package pl.coderslab.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "Flashcards")
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max=50)
    private String name;
    @Size(max=100)
    private String word;
    @Size(max=100)
    private String word2;
    @Size(max=100)
    private String imageLink;
    @Size(max=100)
    private String imageLink2;
    @Size(max=100)
    private String soundLink;
    @Size(max=1000)
    private String additionalText;
    @ManyToOne
    @JoinColumn(name = "packet_id", nullable = false)
    private Packet pack;
    @CreationTimestamp
    private Timestamp created_at;
    @UpdateTimestamp
    private Timestamp updated_at;

    public Flashcard(){
    }
    public Flashcard(Long id, String name, String word, String word2, String imageLink, String imageLink2, String soundLink, String additionalText, Packet pack, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.word = word;
        this.word2 = word2;
        this.imageLink = imageLink;
        this.imageLink2 = imageLink2;
        this.soundLink = soundLink;
        this.additionalText = additionalText;
        this.pack = pack;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    public String getImageLink2() {
        return imageLink2;
    }

    public void setImageLink2(String imageLink2) {
        this.imageLink2 = imageLink2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Packet getPack() {
        return pack;
    }

    public void setPack(Packet pack) {
        this.pack = pack;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getAdditionalText() {
        return additionalText;
    }

    public void setAdditionalText(String additionalText) {
        this.additionalText = additionalText;
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

    public String getSoundLink() {
        return soundLink;
    }

    public void setSoundLink(String soundLink) {
        this.soundLink = soundLink;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", additionalText='" + additionalText + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", soundLink='" + soundLink + '\'' +
                '}';
    }
}
