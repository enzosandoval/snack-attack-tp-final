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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unju.fi.entity.Product;
import ar.edu.unju.fi.service.IProductLinesService;
import ar.edu.unju.fi.service.IProductService;
import ar.edu.unju.fi.util.ProductPDFExporter;

import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lowagie.text.DocumentException;

/**
 * @author Enzo Sandoval
 *
 */
@Controller
public class ProductController {

	@Autowired
	private Product product;

	@Autowired
	private IProductService productService;

	@Autowired
	private IProductLinesService productLinesService;

	/**
	 * 
	 * @param params
	 * @param model
	 * @return Tabla de productos
	 */
	@GetMapping("/products")
	public String getProductsPage(@RequestParam Map<String, Object> params, Model model, @Param("keyword") String keyword) {
		int page = params.get("page") != null ? Integer.valueOf(params.get("page").toString()) - 1 : 0;
		PageRequest pageRequest = PageRequest.of(page, 10);
		Page<Product> pageProducts = productService.findAll(pageRequest);
		int totalPage = pageProducts.getTotalPages();
		if (totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		model.addAttribute("products", pageProducts.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);

		// ✨ Barra de Busqyeda 🔍
		if (keyword != null) {
			List<Product> listProducts = productService.findByKeyword(keyword);
			model.addAttribute("products", listProducts);
			model.addAttribute("keyword", keyword); // Paraque siga apareciendo en la Barra de Busqueda
		}
		return "product-crud";
	}

	/**
	 * 
	 * @param code
	 * @param model
	 * @return Página editar producto
	 * @throws Exception
	 */
	@GetMapping("/product/edit/{code}")
	public String getEditProductPage(@PathVariable(value = "code") String code, Model model) throws Exception {
		product = productService.buscarProducto(code);
		model.addAttribute("product", product);
		model.addAttribute("productLines", productLinesService.obtenerLineasDeProductos());
		return "product-edit";
	}

	/**
	 * 
	 * @param model
	 * @return Página nuevo producto
	 * @throws Exception
	 */
	@GetMapping("/product/new")
	public String getNewProductPage(Model model) throws Exception {
		product = new Product();
		model.addAttribute("product", product);
		model.addAttribute("productLines", productLinesService.obtenerLineasDeProductos());
		return "product-edit";
	}

	/**
	 * 
	 * @param file
	 * @param product
	 * @param result
	 * @param model
	 * @return Producto guardado con éxito
	 * @throws IOException
	 */
	@PostMapping(value = "/product/save", consumes = { "multipart/form-data",
			MediaType.APPLICATION_FORM_URLENCODED_VALUE }, produces = { MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public String getSaveProductRedirect(@RequestParam("file") MultipartFile file,
			@Valid @ModelAttribute("product") Product product, BindingResult result, Model model) throws IOException {
		if (!file.isEmpty()) {
			byte[] content = file.getBytes();
			String base64 = Base64.getEncoder().encodeToString(content);
			product.setImage(base64);
		}
		if (result.hasErrors()) {
			product.setId(null);
			model.addAttribute("product", product);
			model.addAttribute("productLines", productLinesService.obtenerLineasDeProductos());
			return "product-edit";
		} else {
			productService.guardar(product);
			return "redirect:/products";
		}

	}

	/**
	 * 
	 * @param code
	 * @return Producto borrado
	 */
	@GetMapping(value = "/product/delete/{id}")
	public String delete(@PathVariable(value = "id") String code) {
		productService.borrar(code);
		return "redirect:/products";
	}

	/**
	 * 
	 * @param response
	 * @throws DocumentException
	 * @throws IOException
	 */
	@GetMapping("/products/export/to/pdf")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=products_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<Product> productos = productService.obtenerProductos();

		ProductPDFExporter exporter = new ProductPDFExporter(productos);
		exporter.export(response);

	}

	// Producto - Detalle del Producto
	/**
	 * 
	 * @param id
	 * @param model
	 * @return devuelve la página con detalles del producto
	 * @throws Exception
	 */
	@GetMapping("/products/details/{id}")
	public String getProductDetail(@PathVariable(value = "id") String id, Model model) throws Exception {
		// int productDetail = new productDetail();
		model.addAttribute("producto", productService.buscarProducto(id));
		return "product-details";
	}
	
	/**
	 * 
	 * @param model
	 * @return Devuelve una página con una lista de productos
	 * @throws Exception
	 */
	@GetMapping("/products/list")
	public String getProductsListPage(Model model) throws Exception {
		model.addAttribute("productos", productService.obtenerProductos());
		return "product-list";
	}
}
