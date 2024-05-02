package models.repositories;

import java.util.List;

public interface ICrudRepository {
    List findAll();
    Object findById(Long id);
    void save(Object o);
    void update(Object o);
    void delete(Object o);
}
