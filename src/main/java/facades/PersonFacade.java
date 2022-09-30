package facades;

import dtos.PersonDTO;
import entities.Person;
import errorhandling.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    // Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public PersonDTO getPersonByPhone(String phone) {
        return null;
    }

    public List<PersonDTO> getAllPersonsByHobby(int hobbyId) throws EntityNotFoundException {
        return null;
    }

    public List<PersonDTO> getPeopleInCity() {
        return null;
    }

    public PersonDTO createPerson(PersonDTO personDTO) throws EntityNotFoundException {
        return null;
    }


    public PersonDTO updatePerson(PersonDTO personDTO) throws EntityNotFoundException {
        return null;
    }

    public PersonDTO deletePerson(int id) throws EntityNotFoundException {
        return null;
    }
}

