package be.techifutur.labo.adoptadev.services;

import java.util.List;

public interface CrudService<T,ID> {

    ID add(T entity);

    List<T> getAll();

    T getOne(ID id);

    void update(ID id,T entity);

    void delete(ID id);

}
