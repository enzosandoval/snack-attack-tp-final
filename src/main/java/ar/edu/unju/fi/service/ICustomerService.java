/**
 * 
 */
package ar.edu.unju.fi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unju.fi.entity.Customer;

/**
 * @author Enzo Sandoval
 *
 */
public interface ICustomerService {

	public void guardar(Customer cliente);

	public List<Customer> obtenerClientes();

	public Customer buscarCliente(Long id) throws Exception;

	public Page<Customer> findAll(Pageable pagable);

	public void borrar(Long id);
	
	public List<Customer> findByKeyword(String keyword);
}
