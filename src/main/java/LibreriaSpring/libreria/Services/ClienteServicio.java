/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaSpring.libreria.Services;

import LibreriaSpring.libreria.Entities.Autor;
import LibreriaSpring.libreria.Entities.Cliente;

import LibreriaSpring.libreria.Excepciones.Excepciones;
import LibreriaSpring.libreria.Repositories.ClienteRepositorio;
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
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Transactional
    public void cargarcliente(@RequestParam Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono) throws Excepciones {

        Cliente cliente = new Cliente();

        validar(documento, nombre, apellido, telefono);

        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTeléfono(telefono);
        cliente.setAlta(true);

        clienteRepositorio.save(cliente);

    }

    @Transactional
    public void modificarCliente(@RequestParam Long documento, @RequestParam String telefono, @RequestParam String nombre, @RequestParam String apellido) throws Excepciones {

        Cliente cliente = clienteRepositorio.BuscarClientePorDocumento(documento);

        if (cliente == null) {
            throw new Excepciones("Cliente no hallado. Revise el documento");
        }

        Optional<Cliente> respuesta = clienteRepositorio.findById(cliente.getId());
        if (respuesta.isPresent()) {
            Cliente clienteModificar = respuesta.get();

            

            if((nombre == null && apellido == null && telefono == null) || nombre.isEmpty() && apellido.isEmpty() && telefono.isEmpty()) 
            {
                throw new Excepciones("No realizó modificaciones");
            }
            if(nombre != null && !nombre.isEmpty()) {
                clienteModificar.setNombre(nombre);
            }
            if(apellido != null && !apellido.isEmpty()) {
                clienteModificar.setApellido(apellido);
            }
            if(telefono != null && !telefono.isEmpty()) {
                clienteModificar.setTeléfono(telefono);
            }
            validar(clienteModificar.getDocumento(), clienteModificar.getNombre(), clienteModificar.getApellido(), clienteModificar.getTeléfono());
            

            clienteRepositorio.save(cliente);
        }

    }

    @Transactional
    public void bajaCliente(@RequestParam Long documento) throws Excepciones {

        Cliente cliente = clienteRepositorio.BuscarClientePorDocumento(documento);

        if (cliente == null) {
            throw new Excepciones("Cliente no hallado. Revise el documento");
        }

        Optional<Cliente> respuesta = clienteRepositorio.findById(cliente.getId());
        if (respuesta.isPresent()) {
            Cliente clienteBorrar = respuesta.get();

            clienteBorrar.setAlta(false);

            clienteRepositorio.save(clienteBorrar);
        }

    }

    public void validar(Long documento, String nombre, String apellido, String telefono) throws Excepciones {

        if (nombre.trim() == null || nombre.trim().isEmpty()) {
            throw new Excepciones("Nombre nulo o vacío");
        }
        if (apellido.trim() == null || apellido.trim().isEmpty()) {
            throw new Excepciones("Apellido nulo o vacío");
        }
        if (documento == null || documento.toString().length() < 7 || documento.toString().length() < 8) {
            throw new Excepciones("Documento nulo o inválido");
        }

        if (telefono.trim() == null || telefono.trim().isEmpty() || telefono.length() < 10 || telefono.length() > 10) {
            throw new Excepciones("Formato de teléfono  incorrecto");
        }
        List<Cliente> clientes = clienteRepositorio.findAll();

//        for (Cliente cliente : clientes) {
//
//            if (nombre.equals(cliente.getNombre())) {
//                throw new Excepciones("Nombre ya ingresado o repetido");
//            }
//        }

        List<Cliente> DNIs = clienteRepositorio.findAll();

        for (Cliente cliente : DNIs) {

            if (nombre.equals(cliente.getDocumento())) {
                throw new Excepciones("Cliente ya ingresado o documento repetido");
            }
        }

    }
}
