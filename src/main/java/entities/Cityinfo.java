package entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
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

    @OneToMany(mappedBy = "cityinfo")
    private Set<Address> addresses = new LinkedHashSet<>();

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

}