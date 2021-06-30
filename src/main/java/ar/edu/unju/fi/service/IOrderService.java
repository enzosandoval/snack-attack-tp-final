/**
 * 
 */
package ar.edu.unju.fi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unju.fi.entity.Order;

/**
 * @author Enzo Sandoval
 *
 */
public interface IOrderService {

	public void guardar(Order order);

	public List<Order> obtenerOrdenes();

	public Order buscarOrden(long id) throws Exception;

	public Page<Order> findAll(Pageable pagable);

	public void borrar(long id);
	
	public List<Order> findByKeyword(String keyword);
}
