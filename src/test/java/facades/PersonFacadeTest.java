package facades;

import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.Set;

public class PersonFacadeTest {
    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    Person p1, p2, p3;

    PersonDTO pdto1, pdto2, pdto3;

    Address a1, a2;
    Cityinfo ci1, ci2;

    Set<Phone> phones = new HashSet<>();
    Phone ph1, ph2;

    Set<Hobby> hobbies = new HashSet<>();
    Hobby h1, h2, h3, h4, h5, h6;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
//            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Person.deleteAllRows").executeUpdate();

            ph1 = new Phone(29842712, "HOME");
            ph2 = new Phone(12345678, "WORK");
            phones.add(ph1);
            phones.add(ph2);

            a1 = new Address("Noegaardsvej 30", 2800);
            a2 = new Address("Lololol 10", 900);

            h1 = new Hobby("3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing", "Generel", "Indendørs");
            h2 = new Hobby("Akrobatik", "https://en.wikipedia.org/wiki/Acrobatics", "Generel", "Indendørs");
            h3 = new Hobby("Skuespil", "https://en.wikipedia.org/wiki/Acting", "Generel", "Indendørs");
            h4 = new Hobby("Amatørradio", "https://en.wikipedia.org/wiki/Amateur_radio", "Generel", "Indendørs");
            h5 = new Hobby("Animation", "https://en.wikipedia.org/wiki/Animation", "Generel", "Indendørs");
            h6 = new Hobby("Aquascaping", "https://en.wikipedia.org/wiki/Aquascaping", "Generel", "Indendørs");
            hobbies.add(h1);
            hobbies.add(h2);
            hobbies.add(h3);
            hobbies.add(h4);
            hobbies.add(h5);
            hobbies.add(h6);

            p1 = new Person("markchomin@gmail.com", "Mark", "Chomin", a1);
            p2 = new Person("gustavboegh@gmail.com", "Gustav", "Boegh", a2);
            p3 = new Person("Fido@dog.com", "Fido", "Lele", a1);

            p1.getPhones().add(ph1);
            p2.getPhones().add(ph2);
            p3.getPhones().add(ph1);

            p1.getHobbies().add(h1);
            p1.getHobbies().add(h2);
            p2.getHobbies().add(h3);
            p2.getHobbies().add(h4);
            p3.getHobbies().add(h5);
            p3.getHobbies().add(h6);

            em.persist(ci1);
            em.persist(ci2);

            em.persist(ph1);
            em.persist(ph2);

            em.persist(a1);
            em.persist(a2);

            em.persist(p1);
            em.persist(p2);
            em.persist(p2);


            em.getTransaction().commit();
        } finally {
            pdto1 = new PersonDTO(p1);
            pdto2 = new PersonDTO(p2);
            pdto3 = new PersonDTO(p3);
            em.close();
        }
    }
    @Test
    void dummytest() {
        System.out.println("hello!");
    }
}