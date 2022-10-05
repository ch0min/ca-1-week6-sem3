/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManagerFactory;

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

        pf.createPerson(new Person("mark@email.dk", "Mark", "Chomin"));
        pf.createPerson(new Person("gustav@email.dk", "Gustav", "Boegh"));
        pf.createPerson(new Person("rehman@email.dk", "Rehman", "Abra"));
        pf.createPerson(new Person("oscar@email.dk", "Oscar", "Tuff"));



    }
    
    public static void main(String[] args) {
        populate();
    }
}
