package grupo6.demo.service;

import java.util.List;

public interface IService<Clase> {
    Clase create(Clase t) throws Exception;
    void delete(Integer id) throws Exception;
    Clase update(Clase t) throws Exception;
    Clase getById(Integer id) throws Exception;
    List<Clase> getAll();
}
