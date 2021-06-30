/**
 * 
 */
package ar.edu.unju.fi.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.edu.unju.fi.entity.Customer;
import ar.edu.unju.fi.entity.Order;
import ar.edu.unju.fi.entity.OrderDetails;
import ar.edu.unju.fi.service.ICustomerService;

import ar.edu.unju.fi.service.IOrderService;

/**
 * @author Enzo Sandoval
 *
 */
@Controller
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private ICustomerService clienteService;
	
	@Autowired
	public Order order;
	
	@Autowired
	public Customer customer;
	
	@Autowired
	public OrderDetails orderDetails;
	
	@Autowired
	public List<OrderDetails> listaOrderDetails;;
	
	@GetMapping("/new-order")
	public String getNewOrderPage(Model model) throws Exception {
		order = new Order();
		customer = new Customer();
		model.addAttribute("order", order);
		model.addAttribute("customers", clienteService.obtenerClientes());
		return "order";
	
	}
	
	//OK
	@GetMapping("/orders")
	public String getEmployeePage(@RequestParam Map<String, Object> params, Model model) {
		int page = params.get("page") != null ? Integer.valueOf(params.get("page").toString()) - 1 : 0;
		PageRequest pageRequest = PageRequest.of(page, 10);
		Page<Order> pageOrders = orderService.findAll(pageRequest);
		int totalPage = pageOrders.getTotalPages();
		if (totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		model.addAttribute("orders", pageOrders.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		return "order-crud";
	}
	
	
	@PostMapping(value = "/order/save")
	public String getSaveOrderAndRedirect(@Valid @ModelAttribute("order") Order order, BindingResult result,
			Model model) throws Exception {
		if (result.hasErrors()) {
			order.setNumber(null);
			model.addAttribute("order", order);
			model.addAttribute("customer", clienteService.obtenerClientes());
			return "/";
		} else {
			customer = clienteService.buscarCliente(order.getCustomer().getNumber());
			order.setCustomer(customer);
			orderService.guardar(order);
			return "redirect:/last-order";
		}
	}

	//Dudoso
	@GetMapping("/last-order")
	public String getLastOrderPage(Model model) {
		Order lOrder = new Order();
		List<Order> orders = orderService.obtenerOrdenes();
	    lOrder = orders.get(orders.size()-1);
		model.addAttribute("last", lOrder);
		return "last-order";
	}
	

	//OK
	@GetMapping("/order/order-details/{id}")
	public String getProductDetail(@PathVariable(value = "id")long id, Model model) throws Exception {
		System.out.println("Hola: " + orderService.buscarOrden(id));
		model.addAttribute("order", orderService.buscarOrden(id));
		return "order-details";
	}
	
	
	
	/*
	if (arrayList != null && !arrayList.isEmpty()) {
		  T item = arrayList.get(arrayList.size()-1);
		}*/

}
