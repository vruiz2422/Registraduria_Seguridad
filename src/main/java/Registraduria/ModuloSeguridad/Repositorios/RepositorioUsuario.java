package Registraduria.ModuloSeguridad.Repositorios;

import Registraduria.ModuloSeguridad.Modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioUsuario extends MongoRepository<Usuario,String> {
}
