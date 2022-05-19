/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Controllers;

import LibreriaSpring.libreria.Entities.Autor;
import LibreriaSpring.libreria.Entities.Editorial;
import LibreriaSpring.libreria.Entities.Libro;
import LibreriaSpring.libreria.Excepciones.Excepciones;
import LibreriaSpring.libreria.Repositories.AutorRepositorio;
import LibreriaSpring.libreria.Repositories.EditorialRepositorio;
import LibreriaSpring.libreria.Repositories.LibroRepositorio;
import LibreriaSpring.libreria.Services.AutorServicio;
import LibreriaSpring.libreria.Services.ClienteServicio;
import LibreriaSpring.libreria.Services.EditorialServicio;
import LibreriaSpring.libreria.Services.LibroServicio;
import LibreriaSpring.libreria.Services.UsuarioAdminServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author joaqu
 */
@Controller
@RequestMapping
public class LibroController {
    
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
   
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/libros")
    public String libros() {
        return "libros.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/modificarlibro")
    public String modificarlibro(ModelMap modelo) {
         List<Libro> libros = libroRepositorio.findAll();
        modelo.put("libros", libros);
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        return "modificarlibro.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/cargarlibro")
    public String cargarlibro(ModelMap modelo) {
         List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        return "cargarlibro.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/borrarlibro")
    public String borrarlibro(ModelMap modelo) {
        List<Libro> libros = libroRepositorio.findAll();
        modelo.put("libros", libros);
         List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        return "borrarlibro.html";
    }
    
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/cargarLibro")
    public String cargarcliente(ModelMap modelo, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplaresTotales, @RequestParam Editorial editorial, @RequestParam Autor autor) {

        try {
            
            libroServicio.cargarLibro(titulo, anio, ejemplaresTotales, autor, editorial);
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplaresTotales", ejemplaresTotales);
            modelo.put("editoriales", editorial);
            modelo.put("autores", autor);
                     

            return "/cargarlibro.html";
        }
        
        modelo.put("registrado", "Libros cargados con éxito");
        return "/inicio.html";
}
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificarLibro")
    public String modificarlibro(ModelMap modelo, @RequestParam String id, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplaresTotales, @RequestParam Editorial editorial, @RequestParam Autor autor) {

        try {
            
            libroServicio.modificarLibro(id, titulo, anio, ejemplaresTotales, autor, editorial);
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         List<Libro> libros = libroRepositorio.findAll();
        modelo.put("libros", libros);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplaresTotales", ejemplaresTotales);
            modelo.put("editoriales", editorial);
            modelo.put("autores", autor);
                     

            return "/modificarlibro.html";
        }
        
        modelo.put("registrado", "Libro modificado con éxito");
        return "/inicio.html";
}
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/borrarLibro")
    public String borrarlibro(ModelMap modelo, @RequestParam String id) {

        try {
            
            libroServicio.bajaLibro(id);
            
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            
           
                     

            return "/borrarlibro.html";
        }
        
        modelo.put("registrado", "Libro dado de baja");
        return "/inicio.html";
}
    
      @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listalibros")
    public String listalibros(ModelMap modelo) throws Excepciones {
        
        List<Libro> libros1 = libroRepositorio.findAll();
        List<Libro> libros = new ArrayList();
        
        
          for (Libro libro : libros1) {
            if(libro.isAlta() == true) {
                libros.add(libro);
            }  
          }
        modelo.put("libros", libros);
        
        
        return "listalibros.html";
    }
}
