/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Controllers;

import LibreriaSpring.libreria.Entities.Autor;
import LibreriaSpring.libreria.Entities.Cliente;
import LibreriaSpring.libreria.Entities.Editorial;
import LibreriaSpring.libreria.Entities.Libro;
import LibreriaSpring.libreria.Entities.Prestamo;
import LibreriaSpring.libreria.Excepciones.Excepciones;
import LibreriaSpring.libreria.Repositories.AutorRepositorio;
import LibreriaSpring.libreria.Repositories.ClienteRepositorio;
import LibreriaSpring.libreria.Repositories.EditorialRepositorio;
import LibreriaSpring.libreria.Repositories.LibroRepositorio;
import LibreriaSpring.libreria.Repositories.PrestamoRepositorio;
import LibreriaSpring.libreria.Services.AutorServicio;
import LibreriaSpring.libreria.Services.ClienteServicio;
import LibreriaSpring.libreria.Services.EditorialServicio;
import LibreriaSpring.libreria.Services.LibroServicio;
import LibreriaSpring.libreria.Services.PrestamoServicio;
import LibreriaSpring.libreria.Services.UsuarioAdminServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class PrestamoController {
    
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
     @Autowired
    private ClienteRepositorio clienteRepositorio;
     @Autowired
    private PrestamoRepositorio prestamoRepositorio;
     @Autowired
    private PrestamoServicio prestamoServicio;
     
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/prestamos")
    public String prestamos() {
        return "prestamos.html";
    }
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/cargarprestamo")
    public String cargaprestamo(ModelMap modelo) {
        
        List<Libro> libros = libroRepositorio.findAll();
        modelo.put("libros", libros);
         List<Cliente> clientes = clienteRepositorio.findAll();
        modelo.put("clientes", clientes);
        
        return "cargarprestamo.html";
    }
    
     
    
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listaprestamos")
    public String listaprestamos(ModelMap modelo) throws Excepciones {
        
        List<Prestamo> prestamos = prestamoRepositorio.findAll();
        modelo.put("prestamos", prestamos);
        
        
        return "listaprestamos.html";
    }
    

    
    
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/cargarPrestamo")
    public String cargarprestamo(ModelMap modelo, @RequestParam Libro libro, @RequestParam Cliente cliente) {

        try {
            
           prestamoServicio.cargarPrestamo(libro, cliente);
           libroServicio.prestaLibro(libro.getId());
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            List<Libro> libros = libroRepositorio.findAll();
        modelo.put("libros", libros);
         List<Cliente> clientes = clienteRepositorio.findAll();
        modelo.put("clientes", clientes);  
           
                     

            return "/cargarprestamo.html";
        }
        
        modelo.put("registrado", "Préstamo registrado");
        return "/inicio.html";
}
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/devolver")
    public String buscarCliente(ModelMap modelo) throws Excepciones {
        
        List<Prestamo> prestamosParaCliente = prestamoRepositorio.findAll();
        List<Cliente> clientes = new ArrayList();
        
        
        
         for (Prestamo prestamo : prestamosParaCliente) {
             
            if(!clientes.contains(prestamo.getCliente()))
             clientes.add(prestamo.getCliente());
             
             
         }
       
        modelo.put("clientes", clientes);
        
//        List<Prestamo> prestamos = prestamoRepositorio.findAll();
//        modelo.put("prestamos", prestamos);
        
        return "devolver.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/clienteElegir")
    public String clienteElegir(ModelMap modelo, @RequestParam Cliente cliente) throws Excepciones {
        
        List<Prestamo> prestamosParaCliente = prestamoRepositorio.findAll();
        List<Cliente> clientes = new ArrayList();
        
        
        
         for (Prestamo prestamo : prestamosParaCliente) {
             
            if(!clientes.contains(prestamo.getCliente()))
             clientes.add(prestamo.getCliente());
             
             
         }
       
        modelo.put("clientes", clientes);
        
        List<Prestamo> prestamos = prestamoRepositorio.findAll();
        List<Prestamo> prestamosOk = new ArrayList();
        
        
        
         for (Prestamo prestamo : prestamos) {
             
            if(prestamo.getCliente().getId() == cliente.getId())
             prestamosOk.add(prestamo);
             
             
         }
       
        modelo.put("prestamosOk", prestamosOk);
        modelo.put("cliente", cliente);
        
//        List<Prestamo> prestamos = prestamoRepositorio.findAll();
//        modelo.put("prestamos", prestamos);
        
        return "devolver.html";
    }
    
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/devolucion")
    public String devolver(ModelMap modelo, @RequestParam Prestamo prestamoOk, @RequestParam Cliente cliente) {

        try {
            
            prestamoServicio.devolucionPrestamo(prestamoOk);
            libroServicio.devuelveLibro(prestamoOk.getLibro().getId());
            
        } catch (Excepciones ex) {
            modelo.put("error", ex.getMessage());

         
            
           modelo.put("cliente", cliente);
           modelo.put("prestamoOk", prestamoOk);          

            return "/devolver.html";
        }
        
        modelo.put("registrado", "Préstamo cancelado");
        return "/inicio.html";
} 
}
