package facades;

import dtos.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
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


    public List<PersonDTO> getAllPersons() throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> persons = query.getResultList();

            return PersonDTO.getDTOs(persons);
        } finally {
            em.close();
        }
    }

    public List<Person> getPersonByPhone(int phone) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Person> query = em.createQuery("SELECT ph FROM Phone ph JOIN ph.person p " +
                    "JOIN p.hobbies h JOIN p.phones phph WHERE phph.phoneNumber = :phone", Person.class);
            query.setParameter("phone", phone);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


    public List<Person> getAllPersonsByHobby(String hobbyName) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p " +
                    "JOIN p.hobbies h WHERE h.name = :name", Person.class);
            query.setParameter("name", hobbyName);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


    public List<Person> getPersonByHobby(String hobbyName) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT COUNT(p) FROM Person p " +
                    "JOIN p.hobbies h WHERE h.name = :name", Person.class);
            query.setParameter("name", hobbyName);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


    public List<Person> getPersonsByCityZip(int zip) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.address a " +
                    "WHERE a.zip = :zip", Person.class);
            query.setParameter("zip", zip);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Person createPerson(Person p) throws EntityNotFoundException {
        Person personEntity = new Person(p.getEmail(), p.getFirstName(), p.getLastName(), p.getAddress());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(personEntity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return personEntity;
    }


    public Person updatePerson(Person person) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        if (person.getId() == 0)
            throw new javax.persistence.EntityNotFoundException("No such Person with id: " + person.getId());
        em.getTransaction().begin();
        Person p = em.merge(person);
        em.getTransaction().commit();
        return p;
    }

    public Person deletePerson(int id) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
        try {
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
            return person;
        } finally {
            em.close();
        }
    }


    /* *** ADDRESS ***/
    public Address createAddress(Address a) throws EntityNotFoundException {
        Address addressEntity = new Address(a.getStreet(), a.getZip());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(addressEntity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return addressEntity;
    }

    /* *** PHONE ***/
    public Phone createPhone(Phone ph) throws EntityNotFoundException {
        Phone phoneEntity = new Phone(ph.getPhoneNumber(), ph.getDescription(), ph.getPerson());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(phoneEntity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return phoneEntity;
    }

    /* *** Hobbies ***/
    public Person assignHobbyToPerson(int personId, int hobbyId) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, personId);
        Hobby hobby = em.find(Hobby.class, hobbyId);
        em.getTransaction().begin();
        person.assignHobby(hobby);
        em.getTransaction().commit();
        em.close();
        return person;
    }

    public Hobby createHobby(Hobby h) throws EntityNotFoundException {
        Hobby hobbyEntity = new Hobby(h.getCategory(), h.getName(), h.getType(), h.getWikiLink());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(hobbyEntity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return hobbyEntity;
    }



    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = getPersonFacade(emf);
//        System.out.println(pf.getAllPersons());
//        System.out.println(pf.getPerson(1));
//        pf.updatePerson(new PersonDTO(1, "oscar@tuff.dk", "Test", "Test"));
//        pf.deletePerson(1);
//        Address a1 = new Address("New Test 10", 2800);
//        pf.updatePerson(new Person(1, "Markel@Markel", "Markel", "Lele", a1));
//        Person newPerson = new Person("Gustav@Gustav.dk", "Gustav", "Gus", a1);
//        pf.createPerson(newPerson);
//        pf.deletePerson(4);
//        System.out.println(pf.getNumberOfPeopleWithGivenHobby("3D-udskrivning"));
//        System.out.println(pf.getAllPersonsByHobby("3D-udskrivning"));

        List<Person> pl = pf.getPersonByPhone(12341234);

        System.out.println(pl.get(0).getHobbies());
//        System.out.println(pf.getPeopleInCity(2800));

    }

}

