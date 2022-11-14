package Registraduria.ModuloSeguridad.Repositorios;

import Registraduria.ModuloSeguridad.Modelos.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RepositorioRol extends MongoRepository<Rol,String> {
}
