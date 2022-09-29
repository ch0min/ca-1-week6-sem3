package entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PHONE")
public class Phone {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false, referencedColumnName = "id")
    private Person person;

    @Size(max = 45)
    @Column(name = "description", length = 45)
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}