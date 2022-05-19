
package LibreriaSpring.libreria.Services;


import LibreriaSpring.libreria.Entities.Cliente;
import LibreriaSpring.libreria.Entities.Libro;
import LibreriaSpring.libreria.Entities.Prestamo;
import LibreriaSpring.libreria.Excepciones.Excepciones;
import LibreriaSpring.libreria.Repositories.PrestamoRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


@Service
public class PrestamoServicio {
   
    @Autowired
    private PrestamoRepositorio prestamoRepositorio;
   

    @Transactional
    public void cargarPrestamo(@RequestParam Libro libro, @RequestParam Cliente cliente) throws Excepciones {

       Prestamo prestamo = new Prestamo();

       validar(libro, cliente);
       
        prestamo.setAlta(true);
        prestamo.setLibro(libro);
        prestamo.setCliente(cliente);
        prestamo.setFechaPrestamo(new Date());
        
        prestamoRepositorio.save(prestamo);

    }

    @Transactional
    public void devolucionPrestamo(@RequestParam Prestamo prestamoOk) throws Excepciones {

//        System.out.println(libro.getISBN() + " " + cliente.getId());
//        Prestamo prestamo = prestamoRepositorio.BuscarPrestamo(libro.getISBN(), cliente.getId());
//       
//        System.out.println(prestamo);
        
        Optional<Prestamo> respuesta = prestamoRepositorio.findById(prestamoOk.getId());
        if (respuesta.isPresent()) {
            Prestamo prestamoDevolver = respuesta.get();

            prestamoDevolver.setAlta(false);
            prestamoDevolver.setFechaDevolucion(new Date());

            prestamoRepositorio.save(prestamoDevolver);
        }

    }
    
   public void validar(Libro libro, Cliente cliente) throws Excepciones {

        if (libro == null) {
            throw new Excepciones("Libro nulo o vacío");
        }
        if (libro.isAlta()==false){
            throw new Excepciones("Libro dado de baja, no puede ser prestado");
        }
        if (libro.getRestantes() <= 0) {
            throw new Excepciones("No quedan libros disponibles");
        }
        if (cliente == null) {
            throw new Excepciones("Cliente nulo o vacío");
        }
       if (cliente.isAlta()==false){
            throw new Excepciones("Cliente dado de baja, no puede solicitar libros");
        }

        
    }
    }

    

