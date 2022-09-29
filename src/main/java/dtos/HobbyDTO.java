package dtos;

import entities.Hobby;
import entities.Person;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class HobbyDTO {

    private Integer id;
    private String name;
    private String wikiLink;
    private String category;
    private String type;

    private Set<Person> personSet = new LinkedHashSet<>();

    public HobbyDTO(Hobby h) {
        if (h.getId() != 0)
            this.id = h.getId();
        this.name = h.getName();
        this.wikiLink = h.getWikiLink();
        this.category = h.getCategory();
        this.type = h.getType();

        this.personSet = h.getPersonSet();
    }

    public static List<HobbyDTO> toListHobbies(List<Hobby> hobbies) {
        return hobbies.stream().map(HobbyDTO::new).collect(Collectors.toList());
    }

    public Hobby getEntity(){
        Hobby h = new Hobby(this.name, this.wikiLink, this.category, this.type, this.personSet);
        if(id != 0)
            h.setId(this.id);
        return h;
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

    public Set<Person> getPersonSet() {
        return personSet;
    }

    public void setPersonSet(Set<Person> personSet) {
        this.personSet = personSet;
    }

    @Override
    public String toString() {
        return "HobbyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wikiLink='" + wikiLink + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", personSet=" + personSet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HobbyDTO)) return false;
        HobbyDTO hobbyDTO = (HobbyDTO) o;
        return getId().equals(hobbyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
