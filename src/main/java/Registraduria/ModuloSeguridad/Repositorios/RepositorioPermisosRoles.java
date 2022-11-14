package Registraduria.ModuloSeguridad.Repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import Registraduria.ModuloSeguridad.Modelos.PermisosRoles;


public interface RepositorioPermisosRoles extends MongoRepository<PermisosRoles,String> {
}
