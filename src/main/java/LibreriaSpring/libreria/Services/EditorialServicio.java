/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Services;

import LibreriaSpring.libreria.Entities.Editorial;
import LibreriaSpring.libreria.Excepciones.Excepciones;
import LibreriaSpring.libreria.Repositories.EditorialRepositorio;
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
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void cargarEditorial(@RequestParam String nombre) throws Excepciones {

        Editorial editorial = new Editorial();

        validar(nombre);

        editorial.setNombre(nombre);
        editorial.setAlta(true);
        editorialRepositorio.save(editorial);

    }
@Transactional
    public void bajaEditorial(@RequestParam String nombre) throws Excepciones {

         Editorial editorial = editorialRepositorio.BuscarEditorialEditorialPorNombre(nombre);
       
       if(editorial == null) {
           throw new Excepciones("La editorial no fue hallada");
       }
        Optional<Editorial> respuesta = editorialRepositorio.findById(editorial.getId());
        if (respuesta.isPresent()) {
            Editorial editorialABorrar = respuesta.get();

            editorialABorrar.setAlta(false);

            editorialRepositorio.save(editorialABorrar);
        }

    }

    public void validar(String nombre) throws Excepciones {

        if (nombre.trim() == null || nombre.trim().isEmpty()) {
            throw new Excepciones("Nombre nulo o vacío");
        }
  List<Editorial> editoriales = editorialRepositorio.findAll();
        
        for (Editorial editorial : editoriales) {
            
            if(nombre.equals(editorial.getNombre())) {
               throw new Excepciones("Nombre repetido"); 
            }
        }
    }

}
