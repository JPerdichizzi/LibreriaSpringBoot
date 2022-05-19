/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Repositories;

import LibreriaSpring.libreria.Entities.UsuarioAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author joaqu
 */

public interface AdminRepositorio extends JpaRepository<UsuarioAdmin, String> {
    
    @Query("SELECT a from UsuarioAdmin a where a.mail = :mail")
    public UsuarioAdmin BuscarUsuarioPorMail(@Param("mail") String mail);
    
    @Query("DELETE from UsuarioAdmin a where a.mail = :mail")
    public UsuarioAdmin BorrarPorMail(@Param("mail") String mail);
}
