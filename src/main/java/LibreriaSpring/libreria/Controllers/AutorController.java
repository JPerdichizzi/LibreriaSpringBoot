/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Controllers;

import LibreriaSpring.libreria.Excepciones.Excepciones;
import LibreriaSpring.libreria.Repositories.AutorRepositorio;
import LibreriaSpring.libreria.Repositories.EditorialRepositorio;
import LibreriaSpring.libreria.Repositories.LibroRepositorio;
import LibreriaSpring.libreria.Services.AutorServicio;
import LibreriaSpring.libreria.Services.ClienteServicio;
import LibreriaSpring.libreria.Services.EditorialServicio;
import LibreriaSpring.libreria.Services.LibroServicio;
import LibreriaSpring.libreria.Services.UsuarioAdminServicio;
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
public class AutorController {
    
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
    @GetMapping("/autores")
    public String autores() {
        return "autores.html";
    }
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/cargarautor")
  public String cargarautor() {
      return "cargarautor.html";
    }
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/borrarautor")
    public String borrarautor() {
        return "borrarautor.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/cargarAutor")
    public String cargarautor(ModelMap modelo, @RequestParam String nombre) {

        try {
            
            autorServicio.cargarAutor(nombre);
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            modelo.put("nombre", nombre);
           
                     

            return "/cargarautor.html";
        }
        
        modelo.put("registrado", "Autor cargado con éxito");
        return "/inicio.html";
}
    
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/borrarAutor")
    public String borrarautor(ModelMap modelo, @RequestParam String nombre) {

        try {
            
            autorServicio.bajaAutor(nombre);
            
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            modelo.put("nombre", nombre);
           
                     

            return "/borrarautor.html";
        }
        
        modelo.put("registrado", "Autor dado de baja");
        return "/inicio.html";
}
    
}
