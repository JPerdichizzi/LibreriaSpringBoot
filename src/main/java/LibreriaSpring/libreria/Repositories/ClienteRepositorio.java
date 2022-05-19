/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Repositories;

import LibreriaSpring.libreria.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author joaqu
 */
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
    @Query("SELECT a from Cliente a where a.nombre = :nombre")
    public Cliente BuscarClientePorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT a from Cliente a where a.documento = :documento")
    public Cliente BuscarClientePorDocumento(@Param("documento") Long documento);
}
