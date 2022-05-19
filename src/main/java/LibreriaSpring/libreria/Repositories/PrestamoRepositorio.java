/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Repositories;

import LibreriaSpring.libreria.Entities.Cliente;
import LibreriaSpring.libreria.Entities.Editorial;
import LibreriaSpring.libreria.Entities.Libro;
import LibreriaSpring.libreria.Entities.Prestamo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author joaqu
 */
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {
    @Query("SELECT a from Prestamo a where a.libro.ISBN = :libro AND a.cliente.id = :cliente")
    public Prestamo BuscarPrestamo(@Param("libro") Long libro, @Param("cliente") String cliente);

   
    
    
}
