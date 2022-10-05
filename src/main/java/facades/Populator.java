/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManagerFactory;

import entities.Address;
import entities.Person;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = PersonFacade.getPersonFacade(emf);

        Address a1 = new Address("Nørgaardsvej 30", 2800);
        Address a2 = new Address("Zinnia 10", 2770);
        Address a3 = new Address("Østergade 7", 2300);
        Address a4 = new Address("Linde 10", 2600);


        pf.createAddress(a1);

        pf.createPerson(new Person("mark@email.dk", "Mark", "Chomin", a1));
        pf.createPerson(new Person("gustav@email.dk", "Gustav", "Boegh", a2));
        pf.createPerson(new Person("rehman@email.dk", "Rehman", "Abra", a3));
        pf.createPerson(new Person("oscar@email.dk", "Oscar", "Tuff", a4));




    }
    
    public static void main(String[] args) {
        populate();
    }
}
