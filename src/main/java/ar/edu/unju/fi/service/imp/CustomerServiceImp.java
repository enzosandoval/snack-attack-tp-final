/**
 * 
 */
package ar.edu.unju.fi.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.entity.Customer;
import ar.edu.unju.fi.repository.CustomerRepository;
import ar.edu.unju.fi.service.ICustomerService;

/**
 * @author Enzo Sandoval
 *
 */
@Service
public class CustomerServiceImp implements ICustomerService {

	private static final Log LOGGER = LogFactory.getLog(CustomerServiceImp.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public void guardar(Customer cliente) {
		LOGGER.info("SERVICE: CustomerService");
		LOGGER.info("METHOD: guardar()");
		customerRepository.save(cliente);
		LOGGER.info("RESULT: Se guardó el cliente " + cliente.getContactFirstName());
	}

	@Override
	public List<Customer> obtenerClientes() {
		LOGGER.info("SERVICE: CustomerService");
		LOGGER.info("METHOD: obtenerClientes()");
		List<Customer> clientes = new ArrayList<>();
		customerRepository.findAll().forEach(clientes::add);
		LOGGER.info("METHOD: Lista tamaño: " + clientes.size());
		return clientes;
	}

	@Override
	public Customer buscarCliente(Long id) throws Exception {
		LOGGER.info("SERVICE: CustomerService");
		LOGGER.info("METHOD: buscarCliente()");
		Optional<Customer> optional = customerRepository.findById(id);
		Customer cliente = null;
		if (optional.isPresent()) {
			cliente = optional.get();
			LOGGER.info("RESULT: Producto: " + cliente.getContactFirstName());
		} else {
			throw new Exception("Cliente no encontrado");
		}
		return cliente;
	}

	@Override
	public Page<Customer> findAll(Pageable pagable) {
		LOGGER.info("SERVICE: CustomerService");
		LOGGER.info("METHOD: findAll()");
		LOGGER.info("RESULT: Página de clientes");
		return customerRepository.findAll(pagable);
	}

	@Override
	public void borrar(Long id) {
		LOGGER.info("SERVICE: CustomerService");
		LOGGER.info("METHOD: borrar()");
		LOGGER.info("RESULT: ");
		customerRepository.deleteById(id);
	}

	@Override
	public List<Customer> findByKeyword(String keyword) {
		LOGGER.info("METHOD: findByKeyword()");
		return customerRepository.findByKeyword(keyword);
	}
}
