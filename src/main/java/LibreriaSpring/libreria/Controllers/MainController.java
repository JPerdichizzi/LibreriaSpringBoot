package LibreriaSpring.libreria.Controllers;

import LibreriaSpring.libreria.Entities.Autor;
import LibreriaSpring.libreria.Entities.Editorial;
import LibreriaSpring.libreria.Entities.Libro;
import LibreriaSpring.libreria.Entities.UsuarioAdmin;
import LibreriaSpring.libreria.Excepciones.Excepciones;
import LibreriaSpring.libreria.Repositories.AutorRepositorio;
import LibreriaSpring.libreria.Repositories.EditorialRepositorio;
import LibreriaSpring.libreria.Repositories.LibroRepositorio;
import LibreriaSpring.libreria.Services.UsuarioAdminServicio;
import LibreriaSpring.libreria.Services.EditorialServicio;
import LibreriaSpring.libreria.Services.AutorServicio;
import LibreriaSpring.libreria.Services.ClienteServicio;
import LibreriaSpring.libreria.Services.LibroServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping
public class MainController {

     @Autowired
    private UsuarioAdminServicio usuarioAdminServicio;
     @Autowired
    private AutorServicio autorServicio;
     @Autowired
    private EditorialServicio editorialServicio;
     @Autowired
    private LibroServicio libroServicio;
     @Autowired
    private ClienteServicio clienteServicio;
     @Autowired
    private AutorRepositorio autorRepositorio;
     @Autowired
    private EditorialRepositorio editorialRepositorio;
     @Autowired
    private LibroRepositorio libroRepositorio;
     
   @GetMapping("/")
    public String index(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap modelo) {
        
        if(error != null) {
             modelo.put("error", "Nombre de usuario o clave incorrectos");
        }
       
        if (logout != null) {
            modelo.put("logout", "Has salido de tu cuenta");
            

        }
        
       
        return "index.html";
    }
    
    @GetMapping("/registroAdmin")
    public String registroAdmin() {
        return "registroAdmin.html";
    }
    
    @PostMapping("/registrarAdmin")
    public String registroAdmin(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2) {
//        System.out.println(nombre);
//        System.out.println(clave1);

        try {
            
            usuarioAdminServicio.registrarAdmin(mail, clave1, clave2);
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);
              
            

            return "registroAdmin.html";
        }
        
        modelo.put("registrado", "Administrador registrado con éxito");
        return "index.html";
}
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admins")
    public String admins() {
        return "admins.html";
    }
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/borrarAdmin")
    public String borrarAdmin() {
        return "borrarAdmin.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/borrarAdministrador")
    public String borrarAdmin(ModelMap modelo, @RequestParam String mail, @RequestParam String clave) {
 
             try {
            
                 usuarioAdminServicio.eliminarAdmin(mail, clave);
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            
            modelo.put("mail", mail);
            modelo.put("clave1", clave);
          
              return "borrarAdmin.html";
        }
        
        modelo.put("borrado", "Administrador eliminado con éxito");
        return "borrarAdmin.html";
}
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/editoriales")
//    public String editoriales() {
//        return "editoriales.html";
//    }
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/cargaredit")
//    public String cargaredit() {
//        return "cargaredit.html";
//    }
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/borraredit")
//    public String borraredit() {
//        return "borraredit.html";
//    }
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/autores")
//    public String autores() {
//        return "autores.html";
//    }
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/cargarautor")
//  public String cargarautor() {
//      return "cargarautor.html";
//    }
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/borrarautor")
//    public String borrarautor() {
//        return "borrarautor.html";
//    }
    
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/clientes")
//    public String clientes() {
//        return "clientes.html";
//    }
//    
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/cargarcliente")
//    public String cargarcliente() {
//        return "cargarcliente.html";
//    }
//    
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/modificarcliente")
//    public String modificarcliente() {
//        return "modificarcliente.html";
//    }
//    
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/borrarcliente")
//    public String borrarcliente() {
////        return "borrarcliente.html";
////    }
//    
////    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
////    @PostMapping("/cargarAutor")
////    public String cargarautor(ModelMap modelo, @RequestParam String nombre) {
////
////        try {
////            
////            autorServicio.cargarAutor(nombre);
////            
////        } catch (Excepciones ex) {
////            modelo.put("error", ex.getMessage());
////
////         
////            modelo.put("nombre", nombre);
////           
////                     
////
////            return "/cargarautor.html";
////        }
////        
////        modelo.put("registrado", "Autor cargado con éxito");
////        return "/inicio.html";
////}
////    
////     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
////    @PostMapping("/borrarAutor")
////    public String borrarautor(ModelMap modelo, @RequestParam String nombre) {
////
////        try {
////            
////            autorServicio.bajaAutor(nombre);
////            
////            
////        } catch (Excepciones ex) {
////            modelo.put("error", ex.getMessage());
////
////         
////            modelo.put("nombre", nombre);
////           
////                     
////
////            return "/borrarautor.html";
////        }
////        
////        modelo.put("registrado", "Autor dado de baja");
////        return "/inicio.html";
////}
////    
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @PostMapping("/cargarEdit")
//    public String cargaredit(ModelMap modelo, @RequestParam String nombre) {
//
//        try {
//            
//            editorialServicio.cargarEditorial(nombre);
//            
//        } catch (Excepciones ex) {
//            modelo.put("error", ex.getMessage());
//
//         
//            modelo.put("nombre", nombre);
//           
//                     
//
//            return "/cargaredit.html";
//        }
//        
//        modelo.put("registrado", "Editorial cargada con éxito");
//        return "/inicio.html";
//}
//    
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @PostMapping("/borrarEdit")
//    public String borraredit(ModelMap modelo, @RequestParam String nombre) {
//
//        try {
//            
//            editorialServicio.bajaEditorial(nombre);
//            
//            
//        } catch (Excepciones ex) {
//            modelo.put("error", ex.getMessage());
//
//         
//            modelo.put("nombre", nombre);
//           
//                     
//
//            return "/borraredit.html";
//        }
//        
//        modelo.put("registrado", "Editorial dada de baja");
//        return "/inicio.html";
//}
    
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @PostMapping("/cargarCliente")
//    public String cargarcliente(ModelMap modelo, @RequestParam Long documento,@RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono) {
//
//        try {
//            
//            clienteServicio.cargarcliente(documento, nombre, apellido, telefono);
//            
//        } catch (Excepciones ex) {
//            modelo.put("error", ex.getMessage());
//
//         
//            modelo.put("nombre", nombre);
//            modelo.put("apellido", apellido);
//            modelo.put("documento", documento);
//            modelo.put("telefono", telefono);
//           
//                     
//
//            return "/cargarcliente.html";
//        }
//        
//        modelo.put("registrado", "Cliente registrado con éxito");
//        return "/inicio.html";
//}
//    
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @PostMapping("/borrarCliente")
//    public String borrarcliente(ModelMap modelo, @RequestParam Long documento) {
//
//        try {
//            
//            clienteServicio.bajaCliente(documento);
//            
//            
//        } catch (Excepciones ex) {
//            modelo.put("error", ex.getMessage());
//
//         
//            modelo.put("documento", documento);
//           
//                     
//
//            return "/borrarcliente.html";
//        }
//        
//        modelo.put("registrado", "Cliente dado de baja");
//        return "/inicio.html";
//}
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @PostMapping("/modificarCliente")
//    public String modificarcliente(ModelMap modelo, @RequestParam Long documento, @RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido, @RequestParam(required = false) String telefono) {
//
//        try {
//            
//            clienteServicio.modificarCliente(documento, telefono, nombre, apellido);
//            
//        } catch (Excepciones ex) {
//            modelo.put("error", ex.getMessage());
//
//         
//            modelo.put("nombre", nombre);
//            modelo.put("apellido", apellido);
//            modelo.put("documento", documento);
//            modelo.put("telefono", telefono);
//           
//                     
//
//            return "/modificarcliente.html";
//        }
//        
//        modelo.put("registrado", "Cliente modificado con éxito");
//        return "/inicio.html";
//}
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/libros")
//    public String libros() {
//        return "libros.html";
//    }
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/modificarlibro")
//    public String modificarlibro(ModelMap modelo) {
//         List<Libro> libros = libroRepositorio.findAll();
//        modelo.put("libros", libros);
//        List<Autor> autores = autorRepositorio.findAll();
//        modelo.put("autores", autores);
//        List<Editorial> editoriales = editorialRepositorio.findAll();
//        modelo.put("editoriales", editoriales);
//        return "modificarlibro.html";
//    }
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/cargarlibro")
//    public String cargarlibro(ModelMap modelo) {
//         List<Autor> autores = autorRepositorio.findAll();
//        modelo.put("autores", autores);
//        List<Editorial> editoriales = editorialRepositorio.findAll();
//        modelo.put("editoriales", editoriales);
//        return "cargarlibro.html";
//    }
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/borrarlibro")
//    public String borrarlibro(ModelMap modelo) {
//        List<Libro> libros = libroRepositorio.findAll();
//        modelo.put("libros", libros);
//         List<Autor> autores = autorRepositorio.findAll();
//        modelo.put("autores", autores);
//        List<Editorial> editoriales = editorialRepositorio.findAll();
//        modelo.put("editoriales", editoriales);
//        return "borrarlibro.html";
//    }
//    
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @PostMapping("/cargarLibro")
//    public String cargarcliente(ModelMap modelo, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplaresTotales, @RequestParam Editorial editorial, @RequestParam Autor autor) {
//
//        try {
//            
//            libroServicio.cargarLibro(titulo, anio, ejemplaresTotales, autor, editorial);
//            
//        } catch (Excepciones ex) {
//            modelo.put("error", ex.getMessage());
//
//         
//            modelo.put("titulo", titulo);
//            modelo.put("anio", anio);
//            modelo.put("ejemplaresTotales", ejemplaresTotales);
//            modelo.put("editoriales", editorial);
//            modelo.put("autores", autor);
//                     
//
//            return "/cargarlibro.html";
//        }
//        
//        modelo.put("registrado", "Libros cargados con éxito");
//        return "/inicio.html";
//}
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @PostMapping("/modificarLibro")
//    public String modificarlibro(ModelMap modelo, @RequestParam String id, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplaresTotales, @RequestParam Editorial editorial, @RequestParam Autor autor) {
//
//        try {
//            
//            libroServicio.modificarLibro(id, titulo, anio, ejemplaresTotales, autor, editorial);
//            
//        } catch (Excepciones ex) {
//            modelo.put("error", ex.getMessage());
//
//         List<Libro> libros = libroRepositorio.findAll();
//        modelo.put("libros", libros);
//            modelo.put("titulo", titulo);
//            modelo.put("anio", anio);
//            modelo.put("ejemplaresTotales", ejemplaresTotales);
//            modelo.put("editoriales", editorial);
//            modelo.put("autores", autor);
//                     
//
//            return "/modificarlibro.html";
//        }
//        
//        modelo.put("registrado", "Libro modificado con éxito");
//        return "/inicio.html";
//}
//    
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @PostMapping("/borrarLibro")
//    public String borrarlibro(ModelMap modelo, @RequestParam String id) {
//
//        try {
//            
//            libroServicio.bajaLibro(id);
//            
//            
//        } catch (Excepciones ex) {
//            modelo.put("error", ex.getMessage());
//
//         
//            
//           
//                     
//
//            return "/borrarlibro.html";
//        }
//        
//        modelo.put("registrado", "Libro dado de baja");
//        return "/inicio.html";
//}
} 