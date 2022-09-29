package dtofacades;

import datafacades.IDataFacade;
import datafacades.PersonFacade;
import dtos.PersonDTO;
import errorhandling.EntityNotFoundException;
import utils.EMF_Creator;

import java.util.List;

public class PersonDTOFacade implements IDataFacade<PersonDTO> {

//    private static IDataFacade<PersonDTO> instance;
//    private static IDataFacade<Person> personFacade;
//
//    //Private Constructor to ensure Singleton
//    private PersonDTOFacade() {}
//
//    public static IDataFacade<PersonDTO> getFacade() {
//        if (instance == null) {
//            personFacade = PersonFacade.getPersonFacade(EMF_Creator.createEntityManagerFactory());
//            instance = new PersonDTOFacade();
//        }
//        return instance;
//    }

    @Override
    public PersonDTO create(PersonDTO personDTO) {
        return null;
    }

    @Override
    public PersonDTO getById(int id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<PersonDTO> getAll() {
        return null;
    }

    @Override
    public PersonDTO update(PersonDTO personDTO) throws EntityNotFoundException {
        return null;
    }

    @Override
    public PersonDTO delete(int id) throws EntityNotFoundException {
        return null;
    }
}