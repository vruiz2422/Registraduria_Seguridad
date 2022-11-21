package Registraduria.ModuloSeguridad.Controladores;
import Registraduria.ModuloSeguridad.Modelos.Rol;
import Registraduria.ModuloSeguridad.Repositorios.RepositorioRol;
import Registraduria.ModuloSeguridad.Repositorios.RepositorioUsuario;
import Registraduria.ModuloSeguridad.Modelos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario miRepositorioUsuario;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<Usuario> index(){
        return this.miRepositorioUsuario.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/rol/{id_rol}")
    public Usuario create(@PathVariable String id_rol,@RequestBody  Usuario infoUsuario){
        Rol elRol=this.miRepositorioRol.findById(id_rol).get();
        infoUsuario.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
        infoUsuario.setRol(elRol);
        return this.miRepositorioUsuario.save(infoUsuario);
    }
    @GetMapping("{id}")
    public Usuario show(@PathVariable String id){
        Usuario usuarioActual=this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        return usuarioActual;
    }
    @PutMapping("{id}/rol/{id_rol}")
    public Usuario update(@PathVariable String id,@PathVariable String id_rol,@RequestBody  Usuario infoUsuario){
        Usuario usuarioActual=this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        if (usuarioActual!=null){

            Rol elRol=this.miRepositorioRol.findById(id_rol).get();

            if(elRol != null){
                usuarioActual.setSeudonimo(infoUsuario.getSeudonimo());
                usuarioActual.setCorreo(infoUsuario.getCorreo());
                usuarioActual.setRol(elRol);
                usuarioActual.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
                return this.miRepositorioUsuario.save(usuarioActual);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Usuario usuarioActual=this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        if (usuarioActual!=null){
            this.miRepositorioUsuario.delete(usuarioActual);
        }
    }

    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    @PostMapping("/validar")
    public Usuario validate(@RequestBody  Usuario infoUsuario,
                            final HttpServletResponse response) throws IOException {
        Usuario usuarioActual=this.miRepositorioUsuario
                .getUserByEmail(infoUsuario.getCorreo());
        if (usuarioActual!=null &&
                usuarioActual.getContrasena().equals(convertirSHA256(infoUsuario.getContrasena()))) {
            usuarioActual.setContrasena("");
            return usuarioActual;
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }
}

