package datafacades;

import entities.Person;
import errorhandling.EntityNotFoundException;

import java.util.List;

public interface IPersonFacade<T> {
    List<T> getAllPersons();
    List<T> getPersonByPhone(int phone) throws EntityNotFoundException;
    List<T> getAllPersonsByHobby(String hobby);
    List<T> getPersonByHobby(String hobby) throws EntityNotFoundException;
    List<T> getPersonsByCityZip(int zip);
    T createPerson(T t);
    T updatePerson(T t) throws EntityNotFoundException;
    T deletePerson(int id) throws EntityNotFoundException;
}
