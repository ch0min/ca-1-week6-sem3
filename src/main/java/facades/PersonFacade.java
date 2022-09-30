package facades;

import dtos.PersonDTO;
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

    public PersonDTO create(PersonDTO p) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p.getEntity());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return p;
    }

    public PersonDTO getPersonByPhone(int phone) {
        return null;
    }

    public List<PersonDTO> getAllPersonsByHobby(String hobbyName) throws EntityNotFoundException {
        return null;
    }

    public List<PersonDTO> getPeopleInCity(int zip) {
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

