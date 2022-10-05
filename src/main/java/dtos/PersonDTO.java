package dtos;

import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.io.Serializable;
import java.util.*;

public class PersonDTO implements Serializable {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;

    private AddressInnerDTO address;

    private List<PhoneInnerDTO> phones = new ArrayList<>();
    private List<HobbyInnerDTO> hobbies = new ArrayList<>();

    public PersonDTO(Integer id, String email, String firstName, String lastName, AddressInnerDTO address) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public PersonDTO(Person p) {
        this.id = p.getId();
        this.email = p.getEmail();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();

        if(p.getAddress() != null) {
            this.address = new AddressInnerDTO(p.getAddress());
        }

        p.getPhones().forEach( phone -> {
            phones.add(new PhoneInnerDTO(phone));
        });

        p.getHobbies().forEach( hobby -> {
            hobbies.add(new HobbyInnerDTO(hobby));
        });
    }

    public static List<PersonDTO> getDTOs(List<Person> persons) {
        List<PersonDTO> personDTOList = new ArrayList<>();
        persons.forEach(person -> {
            personDTOList.add(new PersonDTO(person));
        });
        return personDTOList;
    }
    public Person getEntity(){
        Set<Phone> phones = new HashSet<>();
        for (PhoneInnerDTO phoneDTO : this.phones) {
            phones.add(phoneDTO.getEntity());
        }
        Set<Hobby> hobbySet = new HashSet<>();
        for (HobbyInnerDTO hobbyDTO: this.hobbies) {
            hobbySet.add(hobbyDTO.getEntity());
        }

        return new Person(this.email, this.firstName, this.lastName, this.address.getEntity(), phones, hobbySet);
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

    public AddressInnerDTO getAddress() {
        return address;
    }

    public void setAddress(AddressInnerDTO address) {
        this.address = address;
    }

    public List<PhoneInnerDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneInnerDTO> phones) {
        this.phones = phones;
    }

    public List<HobbyInnerDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyInnerDTO> hobbies) {
        this.hobbies = hobbies;
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

    // INNER ADDRESS DTO CLASS
    public static class AddressInnerDTO implements Serializable {
        private Integer id;
        private Integer zip;
        private String street;

        public AddressInnerDTO(Integer id, Integer zip, String street) {
            this.id = id;
            this.zip = zip;
            this.street = street;
        }

        public AddressInnerDTO(Address address) {
            this.id = address.getId();
            this.zip = address.getZip();
            this.street = address.getStreet();
        }

        public Address getEntity(){
            return new Address(this.street,this.zip);
        }

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

        @Override
        public String toString() {
            return "AddressInnerDTO{" +
                    "id=" + id +
                    ", zip=" + zip +
                    ", street='" + street + '\'' +
                    '}';
        }
    }


    // INNER PHONE DTO CLASS
    public static class PhoneInnerDTO implements Serializable {
        private Integer phoneNumber;
        private String description;

        public PhoneInnerDTO(Integer phoneNumber, String description) {
            this.phoneNumber = phoneNumber;
            this.description = description;
        }

        public PhoneInnerDTO(Phone phone) {
            this.phoneNumber = phone.getPhoneNumber();
            this.description = phone.getDescription();
        }

        public Phone getEntity(){
            return new Phone(this.phoneNumber,this.description);
        }

        public Integer getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(Integer phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "PhoneInnerDTO{" +
                    "phoneNumber=" + phoneNumber +
                    ", description='" + description + '\'' +
                    '}';
        }
    }


    // INNER HOBBY DTO CLASS
    public static class HobbyInnerDTO implements Serializable {
        private Integer id;
        private String name;
        private String wikiLink;
        private String category;
        private String type;

        public HobbyInnerDTO(Integer id, String name, String wikiLink, String category, String type) {
            this.id = id;
            this.name = name;
            this.wikiLink = wikiLink;
            this.category = category;
            this.type = type;
        }

        public HobbyInnerDTO(Hobby hobby) {
            this.id = hobby.getId();
            this.name = hobby.getName();
            this.wikiLink = hobby.getWikiLink();
            this.category = hobby.getCategory();
            this.type = hobby.getType();
        }

        public Hobby getEntity(){
            return new Hobby(this.getId(), this.getName(),this.getWikiLink(), this.category, this.getType());
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWikiLink() {
            return wikiLink;
        }

        public void setWikiLink(String wikiLink) {
            this.wikiLink = wikiLink;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "HobbyInnerDTO{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", wikiLink='" + wikiLink + '\'' +
                    ", category='" + category + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
