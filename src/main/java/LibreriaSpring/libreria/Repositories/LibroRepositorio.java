/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Repositories;

import LibreriaSpring.libreria.Entities.Editorial;
import LibreriaSpring.libreria.Entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author joaqu
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    
}
