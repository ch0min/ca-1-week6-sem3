/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dtos.PersonDTO;
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
//        PersonDTO pdto1 = new PersonDTO(new Person("oscar@tuff.dk", "Oscar","Oscar",new Address("testadresse 1", 2800)));
//        pf.create(pdto1);
    }
    
    public static void main(String[] args) {
        populate();
    }
}
