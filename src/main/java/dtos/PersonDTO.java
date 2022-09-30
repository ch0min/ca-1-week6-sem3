package dtos;

import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;

    private Address address;
    private Set<Phone> phones = new LinkedHashSet<>();
    private Set<Hobby> hobbies = new LinkedHashSet<>();

    public PersonDTO(Person p) {
//        if (p.getId() != 0)
            this.id = p.getId();
        this.email = p.getEmail();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();

        this.address = p.getAddress();
        this.phones = p.getPhones();
        this.hobbies = p.getHobbies();

    }

    public static List<PersonDTO> toListPersons(List<Person> persons) {
        return persons.stream().map(PersonDTO::new).collect(Collectors.toList());
    }

    public Person getEntity(){
        Person p = new Person(this.email, this.firstName, this.lastName, this.address, this.phones, this.hobbies);
        //if(id != 0)
            p.setId(this.id);
        return p;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                ", hobbies=" + hobbies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDTO)) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return getId().equals(personDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
