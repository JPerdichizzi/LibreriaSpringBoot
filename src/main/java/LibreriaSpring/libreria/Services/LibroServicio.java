/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Services;

import LibreriaSpring.libreria.Entities.Autor;
import LibreriaSpring.libreria.Entities.Editorial;
import LibreriaSpring.libreria.Entities.Libro;
import LibreriaSpring.libreria.Excepciones.Excepciones;
import LibreriaSpring.libreria.Repositories.LibroRepositorio;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author joaqu
 */
@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public void cargarLibro(@RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplaresTotales, @RequestParam Autor autor, @RequestParam Editorial editorial) throws Excepciones {

        Libro libro = new Libro();

        Long ISBN = new Random().nextLong();

        validar(titulo, anio, ejemplaresTotales, autor, editorial, ISBN);

        libro.setISBN(ISBN);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplaresTotales(ejemplaresTotales);
        libro.setRestantes(ejemplaresTotales);
        libro.setPrestados(0);
        libro.setAlta(true);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);

    }

    @Transactional
    public void modificarLibro(@RequestParam String id, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplaresTotales, @RequestParam Autor autor, @RequestParam Editorial editorial) throws Excepciones {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            if ((titulo == null || titulo.isEmpty()) && anio == null && ejemplaresTotales == null && autor == null && editorial == null) {
                throw new Excepciones("No realizó modificaciones");
            }
            if (titulo != null && !titulo.isEmpty()) {
                libro.setTitulo(titulo);
            }
            if (anio != null) {
                libro.setAnio(anio);
            }
            if (ejemplaresTotales != null) {
                libro.setEjemplaresTotales(ejemplaresTotales);
            }
            if (autor != null) {
                libro.setAutor(autor);
            }
            if (editorial != null) {
                libro.setEditorial(editorial);
            }

            validar(libro.getTitulo(), libro.getAnio(), libro.getEjemplaresTotales(), libro.getAutor(), libro.getEditorial(), libro.getISBN());

////        libro.setISBN(ISBN);
//            libro.setTitulo(titulo);
//            libro.setAnio(anio);
//            libro.setEjemplaresTotales(ejemplaresTotales);
//            libro.setRestantes(ejemplaresTotales - libro.getPrestados());
//            libro.setPrestados(libro.getPrestados());
//            libro.setAlta(true);
//            libro.setAutor(autor);
//            libro.setEditorial(editorial);

            libroRepositorio.save(libro);
        }

    }

    @Transactional
    public void bajaLibro(@RequestParam String id) throws Excepciones {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            libro.setAlta(false);

            libroRepositorio.save(libro);
        }

        
    }
    
     @Transactional
    public void prestaLibro(@RequestParam String id) throws Excepciones {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            libro.setPrestados(libro.getPrestados() + 1);
           libro.setRestantes(libro.getEjemplaresTotales() - libro.getPrestados());
            
            libroRepositorio.save(libro);
        }
        
        

        
    }
    
    @Transactional
    public void devuelveLibro(@RequestParam String id) throws Excepciones {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

           libro.setRestantes(libro.getEjemplaresTotales() + 1);
            libro.setPrestados(libro.getPrestados() - 1);
            libroRepositorio.save(libro);
        }
        }

    public void validar(String titulo, Integer anio, Integer ejemplaresTotales, Autor autor, Editorial editorial, Long ISBN) throws Excepciones {

        if (titulo.trim() == null || titulo.trim().isEmpty()) {
            throw new Excepciones("Nombre nulo o vacío");
        }
        if (anio == null || anio < 0 || anio < (new Date().getYear())){
            throw new Excepciones("Año inválido");
        }
        if (ejemplaresTotales == null || ejemplaresTotales < 0) {
            throw new Excepciones("Cantidad de ejemplares incorrecta");
        }
        if (autor == null) {
            throw new Excepciones("Autor inválido");
        }
        if (editorial == null) {
            throw new Excepciones("Editorial inválida");
        }

        List<Libro> libros = libroRepositorio.findAll();

        for (Libro libro : libros) {
            if (ISBN == libro.getISBN()) {
                throw new Excepciones("Se duplicó el ISBN. Cargue el libro nuevamente");

            }
        }
//        List<Cliente> clientes = clienteRepositorio.findAll();

//        for (Cliente cliente : clientes) {
//
//            if (nombre.equals(cliente.getNombre())) {
//                throw new Excepciones("Nombre ya ingresado o repetido");
//            }
//        }

        List<Libro> Libros = libroRepositorio.findAll();

        for (Libro libro : Libros) {

            if (titulo.equals(libro.getTitulo())) {
                throw new Excepciones("Libro ya ingresado o repetido");
            }
        }
    }
}
