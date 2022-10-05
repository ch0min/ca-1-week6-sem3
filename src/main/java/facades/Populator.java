/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManagerFactory;

import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import utils.EMF_Creator;

public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = PersonFacade.getPersonFacade(emf);

        Address a1, a2, a3, a4;
        Hobby h1, h2, h3, h4;
        Person p1, p2, p3, p4;
        Phone ph1, ph2, ph3, ph4;

        a1 = new Address("Nørgaardsvej 30", 2800);
        a2 = new Address("Zinnia 10", 2770);
        a3 = new Address("Østergade 7", 2300);
        a4 = new Address("Linde 10", 2600);

        h1 = pf.createHobby(new Hobby("3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing", "Generel", "Indendørs"));
        h2 = pf.createHobby(new Hobby("Akrobatik", "https://en.wikipedia.org/wiki/Acrobatics", "Generel", "Indendørs"));
        h3 = pf.createHobby(new Hobby("Skuespil", "https://en.wikipedia.org/wiki/Acting", "Generel", "Indendørs"));

        p1 = pf.createPerson(new Person("mark@email.dk", "Mark", "Chomin", a1));
        p2 = pf.createPerson(new Person("gustav@email.dk", "Gustav", "Boegh", a2));
        p3 = pf.createPerson(new Person("rehman@email.dk", "Rehman", "Abra", a3));
        p4 = pf.createPerson(new Person("oscar@email.dk", "Oscar", "Tuff", a4));

        ph1 = pf.createPhone(new Phone(29842712, "HOME", p1));
        ph2 = pf.createPhone(new Phone(12345678, "WORK", p2));
        ph3 = pf.createPhone(new Phone(43214321, "HOME", p3));
        ph4 = pf.createPhone(new Phone(98765432, "WORK", p4));

        pf.assignHobbyToPerson(p1.getId(), h1.getId());
        pf.assignHobbyToPerson(p1.getId(), h2.getId());
        pf.assignHobbyToPerson(p2.getId(), h1.getId());
        pf.assignHobbyToPerson(p3.getId(), h2.getId());
        pf.assignHobbyToPerson(p4.getId(), h3.getId());


    }
    
    public static void main(String[] args) {
        populate();
    }
}
