package Registraduria.ModuloSeguridad.Repositorios;

import Registraduria.ModuloSeguridad.Modelos.Permiso;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RepositorioPermiso extends MongoRepository<Permiso,String> {
}