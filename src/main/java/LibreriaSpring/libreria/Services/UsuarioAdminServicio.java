/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Services;

import LibreriaSpring.libreria.Entities.UsuarioAdmin;
import LibreriaSpring.libreria.Excepciones.Excepciones;
import LibreriaSpring.libreria.Repositories.AdminRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author joaqu
 */
@Service
public class UsuarioAdminServicio implements UserDetailsService {

    @Autowired
    private AdminRepositorio adminRepositorio;

    @Transactional
    public void registrarAdmin(@RequestParam String mail, @RequestParam String password, @RequestParam String password1) throws Excepciones {

        validar(mail, password, password1);

        UsuarioAdmin admin = new UsuarioAdmin();

        admin.setMail(mail);
        String encriptada = new BCryptPasswordEncoder().encode(password);
        admin.setPassword(encriptada);

        adminRepositorio.save(admin);
    }

    @Transactional
    public void eliminarAdmin(@RequestParam String mail, @RequestParam String clave) throws Excepciones {

        UsuarioAdmin prueba = adminRepositorio.BuscarUsuarioPorMail(mail);

        if (prueba == null) {
            throw new Excepciones("Usuario no encontrado");

        }

        Optional<UsuarioAdmin> respuesta = adminRepositorio.findById(adminRepositorio.BuscarUsuarioPorMail(mail).getId());

        if (respuesta.isPresent()) {

            UsuarioAdmin admin = respuesta.get();

            System.out.println("claves ok");

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(clave, admin.getPassword())) {

                adminRepositorio.delete(admin);
            } else {

                throw new Excepciones("Clave incorrecta");
            }

        } else {
            throw new Excepciones("Usuario no encontrado");
        }
    }

    public void validar(String mail, String password, String password1) throws Excepciones {

        if (mail.trim() == null || mail.trim().isEmpty()) {
            throw new Excepciones("E-mail inválido");
        }
        if (password.trim() == null || password.trim().isEmpty() || password.trim().length() < 5) {
            throw new Excepciones("Contraseña nula o muy corta");
        }
        if (!password.trim().equalsIgnoreCase(password1.trim())) {
            throw new Excepciones("Las contraseñas deben coincidir");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        UsuarioAdmin admin = adminRepositorio.BuscarUsuarioPorMail(mail);

        if (admin != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_ADMIN");

            permisos.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("UsuarioSession", admin);

            User user = new User(admin.getMail(), admin.getPassword(), permisos);

            return user;
        } else {

            throw new UsernameNotFoundException("ADMIN USER NOT FOUND");
        }
    }

}
