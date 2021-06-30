/**
 * 
 */
package ar.edu.unju.fi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ar.edu.unju.fi.service.IProductLinesService;

/**
 * @author Enzo Sandoval
 *
 */
@Controller
public class HomeController {

	@Autowired
	private IProductLinesService productLinesService;

	@GetMapping("/")
	public String getHomePage(Model model) throws Exception {
		// ✨ Nombre de las Categorias 👇
		model.addAttribute("productLines", productLinesService.obtenerLineasDeProductos());

		// ✨ Mini Catalogo 👇
		String id = "Trucks and Buses";
		model.addAttribute("productos", productLinesService.buscarLineaDeProducto(id).getProductos());
		return "home";
	}

	@GetMapping("/search/{category}")
	public String getCategoryPage(@PathVariable(value = "category") String id, Model model) throws Exception {
		model.addAttribute("categoria", productLinesService.buscarLineaDeProducto(id));
		model.addAttribute("productos", productLinesService.buscarLineaDeProducto(id).getProductos());
		return "category";
	}

	@GetMapping("/terms-and-conditions")
	public String getTermsAndConditionsPage() {
		return "terms-and-conditions";
	}

	/**
	 * 
	 * @return Home del usuario conectado
	 */
	@GetMapping("/admin")
	public String getAdminPanel() {
		return "admin-panel";
	}

}
