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
public class ClienteController {
    
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
    @GetMapping("/clientes")
    public String clientes() {
        return "clientes.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/cargarcliente")
    public String cargarcliente() {
        return "cargarcliente.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/modificarcliente")
    public String modificarcliente() {
        return "modificarcliente.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/borrarcliente")
    public String borrarcliente() {
        return "borrarcliente.html";
    }
    
        @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/cargarCliente")
    public String cargarcliente(ModelMap modelo, @RequestParam Long documento,@RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono) {

        try {
            
            clienteServicio.cargarcliente(documento, nombre, apellido, telefono);
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("documento", documento);
            modelo.put("telefono", telefono);
           
                     

            return "/cargarcliente.html";
        }
        
        modelo.put("registrado", "Cliente registrado con éxito");
        return "/inicio.html";
}
    
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/borrarCliente")
    public String borrarcliente(ModelMap modelo, @RequestParam Long documento) {

        try {
            
            clienteServicio.bajaCliente(documento);
            
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            modelo.put("documento", documento);
           
                     

            return "/borrarcliente.html";
        }
        
        modelo.put("registrado", "Cliente dado de baja");
        return "/inicio.html";
}
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificarCliente")
    public String modificarcliente(ModelMap modelo, @RequestParam Long documento, @RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido, @RequestParam(required = false) String telefono) {

        try {
            
            clienteServicio.modificarCliente(documento, telefono, nombre, apellido);
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("documento", documento);
            modelo.put("telefono", telefono);
           
                     

            return "/modificarcliente.html";
        }
        
        modelo.put("registrado", "Cliente modificado con éxito");
        return "/inicio.html";
}
}
