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

import ar.edu.unju.fi.entity.Order;
import ar.edu.unju.fi.repository.OrderRepository;
import ar.edu.unju.fi.service.IOrderService;

/**
 * @author Enzo Sandoval
 *
 */
@Service
public class OrderServiceImp implements IOrderService{

	private static final Log LOGGER = LogFactory.getLog(OrderServiceImp.class);

	@Autowired
	private OrderRepository orderRepository;

	
	@Override
	public void guardar(Order order) {
		LOGGER.info("SERVICE: OrderService");
		LOGGER.info("METHOD: guardar()");
		orderRepository.save(order);
		LOGGER.info("RESULT: Se guardó la orden");
	}

	@Override
	public List<Order> obtenerOrdenes() {
		LOGGER.info("SERVICE: OrderService");
		LOGGER.info("METHOD: obtenerOrdenes()");
		List<Order> ordenes = new ArrayList<>();
		orderRepository.findAll().forEach(ordenes::add);
		LOGGER.info("METHOD: Lista tamaño: " +  ordenes.size());
		return ordenes;
	}

	@Override
	public Order buscarOrden(long id) throws Exception {
		LOGGER.info("SERVICE: OrderService");
		LOGGER.info("METHOD: buscarOrden()");
		Optional<Order> optional = orderRepository.findById(id);
		Order order = null;
		if (optional.isPresent()) {
			order = optional.get();
			LOGGER.info("RESULT: Orden: " + order.getNumber());
		} else {
			throw new Exception("Orden no encontrada");
		}
		return order;
	}

	@Override
	public Page<Order> findAll(Pageable pagable) {
		LOGGER.info("SERVICE: OrderService");
		LOGGER.info("METHOD: findAll()");
		LOGGER.info("RESULT: Página de ordenes");
		return orderRepository.findAll(pagable);
	}

	@Override
	public void borrar(long id) {
		LOGGER.info("SERVICE: OrderService");
		LOGGER.info("METHOD: borrar()");
		LOGGER.info("RESULT: ");
		orderRepository.deleteById(id);
	}

	@Override
	public List<Order> findByKeyword(String keyword) {
		LOGGER.info("METHOD: findByKeyword()");
		return orderRepository.findByKeyword(keyword);
	}
}
