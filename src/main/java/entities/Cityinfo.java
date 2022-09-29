package entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CITYINFO")
public class Cityinfo {
    @Id
    @Column(name = "zip", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "city", length = 45)
    private String city;

    @OneToMany(mappedBy = "zip")
    private Set<Address> addresses = new LinkedHashSet<>();

    public Cityinfo() {
    }

    public Cityinfo(Integer id, String city, Set<Address> addresses) {
        this.id = id;
        this.city = city;
        this.addresses = addresses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cityinfo)) return false;
        Cityinfo cityinfo = (Cityinfo) o;
        return getId().equals(cityinfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Cityinfo{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}