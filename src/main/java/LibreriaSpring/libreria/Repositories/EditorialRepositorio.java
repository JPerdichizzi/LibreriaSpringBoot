/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Repositories;

import LibreriaSpring.libreria.Entities.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author joaqu
 */
@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
    @Query("SELECT a from Editorial a where a.nombre = :nombre")
    public Editorial BuscarEditorialEditorialPorNombre(@Param("nombre") String nombre);
}
