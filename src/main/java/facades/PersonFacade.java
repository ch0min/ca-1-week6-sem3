package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Person;
import entities.Phone;
import errorhandling.EntityNotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
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

    public List<PersonDTO> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<PersonDTO> query = em.createQuery("SELECT p FROM Person p", PersonDTO.class);
        List<PersonDTO> persons = query.getResultList();
        return persons;
    }

    public PersonDTO getPersonByPhone(int phone) {
//        EntityManager em = emf.createEntityManager();
//        Phone p = em.find(Phone.class, phone);
//        if (p != null) {
//            return p.getPerson();
//        }
//        return phone;
        return null;
    }


    public List<PersonDTO> getAllPersonsByHobby(String hobbyName) throws EntityNotFoundException {
//        EntityManager em = emf.createEntityManager();
//
//        try {
//            TypedQuery<PersonDTO> query = em.createQuery("SELECT h FROM Hobby h JOIN h.personSet ps " +
//                    "JOIN ps.hobbies hob WHERE hob.name = :name", PersonDTO.class);
//            query.setParameter("name", hobbyName);
//            return query.getResultList();
//        } finally {
//            em.close();
//        }
        return null;
    }

    public List<PersonDTO> getNumberOfPeopleWithGivenHobby(String hobbyName) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<PersonDTO> query = em.createQuery("SELECT COUNT(p) FROM Person p "+
                    "JOIN p.hobbies h WHERE h.name = :name", PersonDTO.class);
            query.setParameter("name", hobbyName);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


        public List<PersonDTO> getPeopleInCity(int zip) {
        return null;
    }

    public PersonDTO createPerson(PersonDTO p) {
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


    public PersonDTO updatePerson(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            PersonDTO p = em.merge(personDTO);
            em.getTransaction().commit();
            return p;
        } finally {
            em.close();
        }

    }

    public PersonDTO deletePerson(int id) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        PersonDTO pdto = em.find(PersonDTO.class, id);
        if (pdto == null) {
            throw new javax.persistence.EntityNotFoundException("No such Person exist with the id " + id);
        }
        try {
            em.getTransaction().begin();
            em.remove(pdto);
            em.getTransaction().commit();
            return pdto;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) throws EntityNotFoundException {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = getPersonFacade(emf);
//        System.out.println(pf.getAllPersons());
//        System.out.println(pf.getPerson(1));
//        pf.updatePerson(new PersonDTO(1, "oscar@tuff.dk", "Test", "Test"));
//        pf.deletePerson(1);
        ;
    }

}

