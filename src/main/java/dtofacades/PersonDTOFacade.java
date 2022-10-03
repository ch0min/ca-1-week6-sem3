package dtofacades;

import datafacades.IPersonFacade;
import datafacades.PersonFacade;
import dtos.PersonDTO;
import entities.Person;
import errorhandling.EntityNotFoundException;
import utils.EMF_Creator;

import java.util.List;

public class PersonDTOFacade implements IPersonFacade<PersonDTO> {
    private static IPersonFacade<PersonDTO> instance;
    private static IPersonFacade<Person> personFacade;

    //Private Constructor to ensure Singleton
    private PersonDTOFacade() {}

    public static IPersonFacade<PersonDTO> getFacade() {
        if (instance == null) {
            personFacade = PersonFacade.getPersonFacade(EMF_Creator.createEntityManagerFactory());
            instance = new PersonDTOFacade();
        }
        return instance;
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        return PersonDTO.toListPersons(personFacade.getAllPersons());
    }

    @Override
    public List<PersonDTO> getPersonByPhone(int phone) throws EntityNotFoundException {
        return PersonDTO.toListPersons(personFacade.getPersonByPhone(phone));
    }

    @Override
    public List<PersonDTO> getAllPersonsByHobby(String hobby) {
        return PersonDTO.toListPersons(personFacade.getAllPersonsByHobby(hobby));
    }

    @Override
    public List<PersonDTO> getPersonByHobby(String hobby) throws EntityNotFoundException {
        return PersonDTO.toListPersons(personFacade.getPersonByHobby(hobby));
    }

    @Override
    public List<PersonDTO> getPersonsByCityZip(int zip) {
        return PersonDTO.toListPersons(personFacade.getPersonsByCityZip(zip));

    }

    @Override
    public PersonDTO createPerson(PersonDTO personDTO) {
        Person p = personDTO.getEntity();
        p = personFacade.createPerson(p);
        return new PersonDTO(p);
    }

    @Override
    public PersonDTO updatePerson(PersonDTO personDTO) throws EntityNotFoundException {
        Person p = personFacade.updatePerson(personDTO.getEntity());
        return new PersonDTO(p);
    }

    @Override
    public PersonDTO deletePerson(int id) throws EntityNotFoundException {
        return new PersonDTO(personFacade.deletePerson(id));
    }
}
