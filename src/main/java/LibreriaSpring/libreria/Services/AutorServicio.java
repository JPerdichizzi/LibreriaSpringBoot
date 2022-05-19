/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Services;

import LibreriaSpring.libreria.Entities.Autor;
import LibreriaSpring.libreria.Excepciones.Excepciones;
import LibreriaSpring.libreria.Repositories.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author joaqu
 */
@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public void cargarAutor(@RequestParam String nombre) throws Excepciones {
        
       
        Autor autor= new Autor();
        
        
        validar(nombre);
        
        
        autor.setNombre(nombre);
        autor.setAlta(true);
        autorRepositorio.save(autor);
        
    }
    
    
    @Transactional
    public void bajaAutor(@RequestParam String nombre) throws Excepciones {
        
       Autor autor = autorRepositorio.BuscarAutorPorNombre(nombre);
       
       if(autor == null) {
           throw new Excepciones("El autor no fue hallado");
       }
       
        Optional<Autor> respuesta = autorRepositorio.findById(autor.getId());
        if(respuesta.isPresent()) {
            
         Autor autorABorrar = respuesta.get();
        
         
        
        autorABorrar.setAlta(false);
       
        autorRepositorio.save(autorABorrar);
        }
        
        
    }
    
    public void validar(String nombre) throws Excepciones {
        
        if(nombre.trim() == null || nombre.trim().isEmpty()){
            throw new Excepciones("Nombre nulo o vacío");
        }
        
        List<Autor> autores = autorRepositorio.findAll();
        
        for (Autor autor : autores) {
            
            if(nombre.equals(autor.getNombre())) {
               throw new Excepciones("Nombre repetido"); 
            }
        }
        
    }
        
        

}
