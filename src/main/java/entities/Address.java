package entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address")
@Table(name = "ADDRESS")
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    @Id
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "zip", nullable = false)
//    private Cityinfo zip2;

//    @ElementCollection
//    @CollectionTable(name = "cityinfo", joinColumns = @JoinColumn(name = "zip"))
//    @Column(name = "zip")
//    private List<String> zip;
    @Column(name = "zip")
    private Integer zip;

    @Column(name = "street")
    private String street;

    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    private Set<Person> people = new LinkedHashSet<>();

    public Address() {
    }

    public Address(Integer zip, String street, Set<Person> people) {
        this.zip = zip;
        this.street = street;
        this.people = people;
    }

    public Address(String street, Integer zip) {
        this.zip = zip;
        this.street = street;
    }


//    public List<String> getCity() {
//        return city;
//    }

//    public void setCity(List<String> city) {
//        this.city = city;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", zip=" + zip +
                ", street='" + street + '\'' +
                ", people=" + people +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getId() == address.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}