package dtofacades;

import datafacades.IDataFacade;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import errorhandling.EntityNotFoundException;

import java.util.List;

public class HobbyDTOFacade implements IDataFacade<HobbyDTO> {

//    private static IDataFacade<HobbyDTO> instance;
//    private static IDataFacade<Hobby> hobbyFacade;
//
//    //Private Constructor to ensure Singleton
//    private HobbyDTOFacade() {
//    }
//
//    public static IDataFacade<PersonDTO> getFacade() {
//        if (instance == null) {
//            hobbyFacade = HobbyFacade.getHobbyFacade(EMF_Creator.createEntityManagerFactory());
//            instance = new PersonDTOFacade();
//        }
//        return instance;
//    }


    @Override
    public HobbyDTO create(HobbyDTO hobbyDTO) {
        return null;
    }

    @Override
    public HobbyDTO getById(int id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<HobbyDTO> getAll() {
        return null;
    }

    @Override
    public HobbyDTO update(HobbyDTO hobbyDTO) throws EntityNotFoundException {
        return null;
    }

    @Override
    public HobbyDTO delete(int id) throws EntityNotFoundException {
        return null;
    }



}
