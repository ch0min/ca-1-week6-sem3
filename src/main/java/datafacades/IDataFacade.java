package datafacades;

import errorhandling.EntityNotFoundException;

import java.util.List;

public interface IDataFacade<T> {
    T create(T t);
    T getById(int id) throws EntityNotFoundException;
    List<T> getAll();
    T update(T t) throws EntityNotFoundException;
    T delete(int id) throws EntityNotFoundException;
}
