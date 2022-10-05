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
import java.util.Set;

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

    public List<PersonDTO> getPersonByPhone(int phone) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.phones ph WHERE ph.phoneNumber = :phone",Person.class);
//                    "SELECT ph FROM Phone ph JOIN ph.person p " +
//                    "JOIN p.hobbies h JOIN p.phones phph WHERE phph.phoneNumber = :phone", Person.class);
            query.setParameter("phone", phone);
            List<Person> persons = query.getResultList();
            return PersonDTO.getDTOs(persons);
        } finally {
            em.close();
        }
    }


    public List<PersonDTO> getAllPersonsByHobby(String hobbyName) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p " +
                    "JOIN p.hobbies h WHERE h.name = :name", Person.class);
            query.setParameter("name", hobbyName);
            List<Person> persons = query.getResultList();
            return PersonDTO.getDTOs(persons);
        } finally {
            em.close();
        }
    }


    public List<PersonDTO> getPersonByHobby(String hobbyName) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT COUNT(p) FROM Person p " +
                    "JOIN p.hobbies h WHERE h.name = :name", Person.class);
            query.setParameter("name", hobbyName);
            List<Person> persons = query.getResultList();
            return PersonDTO.getDTOs(persons);
        } finally {
            em.close();
        }
    }


    public List<PersonDTO> getPersonsByCityZip(int zip) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.address a " +
                    "WHERE a.zip = :zip", Person.class);
            query.setParameter("zip", zip);
            List<Person> persons = query.getResultList();
            return PersonDTO.getDTOs(persons);
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
            return personEntity;
        } finally {
            em.close();
        }
    }

//    public PersonDTO create(PersonDTO p) throws EntityNotFoundException {
//        Person personEntity = p.getEntity();
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            p.getHobbies().forEach(hobby -> {
//                if (hobby.getId() != 0)
//                    hobby = em.find(PersonDTO.HobbyInnerDTO.class, hobby.getId());
//                else {
//                    em.persist(hobby);
//                }
//            });
//            em.persist(personEntity);
//            em.getTransaction().commit();
//            return new PersonDTO(personEntity);
//        } finally {
//            em.close();
//        }
//    }

    public PersonDTO createPersonDTO(PersonDTO p) throws EntityNotFoundException {
        Person personEntity = new Person(p.getEmail(), p.getFirstName(), p.getLastName(), p.getAddress().getEntity(), p.getEntity().getPhones());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(personEntity);
            em.getTransaction().commit();
            return new PersonDTO(personEntity);
        } finally {
            em.close();
        }
    }


    public PersonDTO updatePerson(PersonDTO person) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        if (person.getId() == 0)
            throw new javax.persistence.EntityNotFoundException("No such Person with id: " + person.getId());
        em.getTransaction().begin();
        em.find(Person.class, person.getId());
        Person p = em.merge(person.getEntity());
        em.getTransaction().commit();
        return new PersonDTO(p);
    }

    public PersonDTO deletePerson(int id) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
        try {
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }


    /* *** ADDRESS ***/
    public PersonDTO.AddressInnerDTO createAddress(PersonDTO.AddressInnerDTO a) throws EntityNotFoundException {
        Address addressEntity = new Address(a.getStreet(), a.getZip());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(addressEntity);
            em.getTransaction().commit();
            return new PersonDTO.AddressInnerDTO(addressEntity);
        } finally {
            em.close();
        }
    }

    /* *** PHONE ***/
    public Phone createPhone(Phone ph) throws EntityNotFoundException {
        Phone phoneEntity = new Phone(ph.getPhoneNumber(), ph.getDescription(), ph.getPerson());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(phoneEntity);
            em.getTransaction().commit();
            return phoneEntity;
        } finally {
            em.close();
        }
    }
    public PersonDTO.PhoneInnerDTO createPhoneDTO(PersonDTO.PhoneInnerDTO ph) throws EntityNotFoundException {
        Phone phoneEntity = new Phone(ph.getPhoneNumber(), ph.getDescription(), ph.getEntity().getPerson());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(phoneEntity);
            em.getTransaction().commit();
            return new PersonDTO.PhoneInnerDTO(phoneEntity);
        } finally {
            em.close();
        }
    }

    /* *** Hobbies ***/
    public PersonDTO assignHobbyToPerson(int personId, int hobbyId) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, personId);
        Hobby hobby = em.find(Hobby.class, hobbyId);
        em.getTransaction().begin();
        person.assignHobby(hobby);
        em.getTransaction().commit();
        em.close();
        return new PersonDTO(person);
    }

    public Hobby createHobby(Hobby h) throws EntityNotFoundException {
        Hobby hobbyEntity = new Hobby(h.getName(), h.getWikiLink(), h.getCategory(), h.getType());
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
    public PersonDTO.HobbyInnerDTO createHobbyDTO(PersonDTO.HobbyInnerDTO h) throws EntityNotFoundException {
        Hobby hobbyEntity = new Hobby(h.getCategory(), h.getName(), h.getType(), h.getWikiLink());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(hobbyEntity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO.HobbyInnerDTO(hobbyEntity);
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

        List<PersonDTO> pl = pf.getPersonByPhone(12341234);

        System.out.println(pl.get(0).getHobbies());
//        System.out.println(pf.getPeopleInCity(2800));

    }

}

