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


public class EditorialController {
    
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
    @GetMapping("/editoriales")
    public String editoriales() {
        return "editoriales.html";
    }
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/cargaredit")
    public String cargaredit() {
        return "cargaredit.html";
    }
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/borraredit")
    public String borraredit() {
        return "borraredit.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/cargarEdit")
    public String cargaredit(ModelMap modelo, @RequestParam String nombre) {

        try {
            
            editorialServicio.cargarEditorial(nombre);
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            modelo.put("nombre", nombre);
           
                     

            return "/cargaredit.html";
        }
        
        modelo.put("registrado", "Editorial cargada con éxito");
        return "/inicio.html";
}
    
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/borrarEdit")
    public String borraredit(ModelMap modelo, @RequestParam String nombre) {

        try {
            
            editorialServicio.bajaEditorial(nombre);
            
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            modelo.put("nombre", nombre);
           
                     

            return "/borraredit.html";
        }
        
        modelo.put("registrado", "Editorial dada de baja");
        return "/inicio.html";
}
    
}
