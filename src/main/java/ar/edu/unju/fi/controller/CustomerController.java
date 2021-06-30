/**
 * 
 */
package ar.edu.unju.fi.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unju.fi.entity.Customer;
import ar.edu.unju.fi.service.ICustomerService;
import ar.edu.unju.fi.service.IEmployeeService;

/**
 * @author Enzo Sandoval
 *
 */
@Controller
public class CustomerController {

	@Autowired
	private Customer customer;

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private IEmployeeService employeeService;

	@GetMapping("/customers")
	public String getCustomersPage(@RequestParam Map<String, Object> params, Model model, @Param("keyword") String keyword) {
		int page = params.get("page") != null ? Integer.valueOf(params.get("page").toString()) - 1 : 0;
		PageRequest pageRequest = PageRequest.of(page, 10);
		Page<Customer> pageCustomers = customerService.findAll(pageRequest);
		int totalPage = pageCustomers.getTotalPages();
		if (totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		model.addAttribute("customers", pageCustomers.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);

		// ✨ Barra de Busqyeda 🔍
		if (keyword != null) {
			List<Customer> listCustomers = customerService.findByKeyword(keyword);
			model.addAttribute("customers", listCustomers);
			model.addAttribute("keyword", keyword); // Para que "keyword" siga apareciendo en la Barra de Busqueda
		}
		return "customer-crud";
	}

	@GetMapping("/customer/edit/{id}")
	public String getEditCustomerPage(@PathVariable(value = "id") long id, Model model) throws Exception {
		customer = customerService.buscarCliente(id);
		model.addAttribute("customer", customer);
		if (customer.getSalesRepresentative() == null) {
			// Agregar el empleado logueado;
		}
		model.addAttribute("employess", employeeService.obtenerEmpleados());
		return "customer-edit";
	}

	@GetMapping("/customer/new")
	public String getNewCustomerPage(Model model) throws Exception {
		customer = new Customer();
		model.addAttribute("customer", customer);
		model.addAttribute("employess", employeeService.obtenerEmpleados());
		return "customer-edit";
	}

	@PostMapping(value = "/customer/save", consumes = "multipart/form-data")
	public String getSaveCustomerAndRedirect(@RequestParam("file") MultipartFile file,
			@Valid @ModelAttribute("customer") Customer customer, BindingResult result, Model model)
			throws IOException {
		if (!file.isEmpty()) {
			byte[] content = file.getBytes();
			String base64 = Base64.getEncoder().encodeToString(content);
			customer.getUsuarioCliente().setImage(base64);
		} else {
			customer.getUsuarioCliente().setImage(
					"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");
		}
		if (result.hasErrors()) {
			model.addAttribute("customer", customer);
			return "customer-edit";
		} else {
			customerService.guardar(customer);
			return "redirect:/customers";
		}
	}

	@GetMapping(value = "/customer/delete/{id}")
	public String delete(@PathVariable(value = "id") long id) {
		customerService.borrar(id);
		return "redirect:/customers";
	}

}
