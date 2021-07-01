package ar.edu.unju.fi.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.core.Authentication;

import org.springframework.data.repository.query.Param;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.edu.unju.fi.entity.Employee;
import ar.edu.unju.fi.entity.Office;
import ar.edu.unju.fi.entity.UsuarioEmpleado;
import ar.edu.unju.fi.service.IEmployeeService;
import ar.edu.unju.fi.service.IOfficeService;

@Controller
public class EmployeeController {

	@Autowired
	private Employee employee;

	@Autowired
	private Employee reportTo;

	@Autowired
	private Office oficina;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IOfficeService officeService;

	/**
	 * 
	 * @param params
	 * @param model
	 * @param keyword
	 * @return devuelve la página de empleados en formato de tabla, con opciones de CRUD y función de busqueda para empleado
	 */
	@GetMapping("/employees")
	public String getEmployeePage(@RequestParam Map<String, Object> params, Model model, @Param("keyword") String keyword) {
		int page = params.get("page") != null ? Integer.valueOf(params.get("page").toString()) - 1 : 0;
		PageRequest pageRequest = PageRequest.of(page, 10);
		Page<Employee> pageEmployees = employeeService.findAll(pageRequest);
		int totalPage = pageEmployees.getTotalPages();
		if (totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		model.addAttribute("employees", pageEmployees.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);

		// ✨ Barra de Busqyeda 🔍
		if (keyword != null) {
			List<Employee> listEmployees = employeeService.findByKeyword(keyword);
			model.addAttribute("employees", listEmployees);
			model.addAttribute("keyword", keyword); // Para que "keyword" siga apareciendo en la Barra de Busqueda
		}
		return "employee-crud";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return devuelve la página con un formulario para poder editar datos de un empleado
	 * @throws Exception
	 */
	@GetMapping("/employee/edit/{id}")
	public String getEditEmployeePage(@PathVariable(value = "id") long id, Model model) throws Exception {
		employee = employeeService.buscarEmpleado(id);
		System.out.println("Here: " + employee.getReportsTo().getNumber());
		employee.setReportsTo(employeeService.buscarEmpleado(employee.getReportsTo().getNumber()));
		model.addAttribute("employee", employee);
		model.addAttribute("oficinas", officeService.obtenterOficinas());
		model.addAttribute("empleados", employeeService.obtenerEmpleados());
		return "employee-edit";
	}

	/**
	 * 
	 * @param model
	 * @return devuelve una página con un formulario para dar de alta un nuevo empleado.
	 * @throws Exception
	 */
	@GetMapping("/employee/new")
	public String getNewEmployeePage(Model model) throws Exception {
		employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("oficinas", officeService.obtenterOficinas());
		model.addAttribute("empleados", employeeService.obtenerEmpleados());
		return "employee-edit";
	}

	/**
	 * 
	 * @param employee
	 * @param result
	 * @param model
	 * @return Si los datos cargados de un empleado en el formulario de alta son válidos, devuelve la pagina de lista de empleado. Caso contrario devuelve la página con el formulario de alta, señalando los campos con datos invalidos.
	 * @throws Exception
	 */
	@PostMapping(value = "/employee/save")
	public String getSaveemployeeAndRedirect(@Valid @ModelAttribute("employee") Employee employee, BindingResult result,
			Model model) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("employee", employee);
			model.addAttribute("oficinas", officeService.obtenterOficinas());
			model.addAttribute("empleados", employeeService.obtenerEmpleados());
			return "employee-edit";
		} else {
			UsuarioEmpleado usuario = new UsuarioEmpleado();
			usuario.setUsername(employee.getUsuarioEmpleado().getUsername());
			usuario.setPassword(employee.getUsuarioEmpleado().getPassword());
			usuario.setRol(employee.getJobTitle());
			usuario.setImage(
					"iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAMAAACahl6sAAAAe1BMVEUAAAD////8/PwLCwsZGRkKCgrv7+/s7OwsLCz4+Pg3Nzfg4OAFBQV5eXmamprCwsIwMDDJyclNTU2srKxbW1tiYmJAQEC2traUlJRZWVmqqqooKCjb29uenp6kpKTOzs5DQ0OBgYGLi4u8vLwXFxd2dnZtbW0hISFKSkqNUkUbAAAHKUlEQVR4nOWd6XqqMBBAAyggKAgqotWqaLXv/4QX9KosAZIwSUx6fvcTTrNNtgEZvHDCYLZJ3JE1RmhsjdxkMwtCh9vjEI8fnYRZPEJYRnEWTng8E17ET+MxXuKJFafwJQMsYqdJt8STJLBhnwwqEi57yqLMeBlBPhtOxNx65BYPvIUJ9ngoEXP7RatR4IKpAImc5ywaBfMQ5g1ARPyYVaNg40O8A4CImVE0cRxWClC/hovsqNt4E28nX2RvDffIC+VbsshkCaFRsBoYuAwT8Zk7qybzYW1+kEh0gfPI48mDLJH1wN6qjrWWI7KdwnogNF3IEPmG1ihg77yYRbY8PBBiLhNWkTV4vXowZW0njCIRcDt/YzH2XWwiPmi/W2XENp4wiUwAx8EmHtMYzyQCFpfgWYkS2fP1YOuEGUR2IPFuFxZDM6EXMQHmH3149DMtepGMvwdCKX8Rn9sIUoa+clGLDFpnIGfDW+QsxgMh2lUiShGT61BYZs5XhFPMi4MyDqYTMZnWRdlw6bpgOhGBBUJbJHQiAsbCNwk/kVCkB0JU+ydUIpyj3jpUUTCNiC1kUH8zptmdoxFJxXogFHASIdznhOPIR8QX7YEQxS42hYjwmkVVtyhEBMW9ZWIeIhPBfVaBRb6gQi4ieDR8QD4mkosImeLWyTiISGgiNI2EXKTl3BJfbvAijgwPhIijFGIRKW2dYupOLBLIEdmDi8zkiMzARTZyRJbgIsJD3wfEATCxiCtHhHh5i1hEyjCC0AhchPumCB4LXERC7FswBheR44HI3+/PiWhTtbRp7Np0v9oMiNqEKNoEjdqE8dpMrLSZ6mqz+KDNcpA+C3TaLJlqs4g9kRA2WuSnH/7gRo8+W2/abIZqsz0tPtwiDrQoRUQf4bB4HeHQ5lCNEYkV4XfMSZuDZ8ZCpAjd3R7Kw5kC11LmPA9nanNcVuABZo/uxaiPlAsL5jkfKRe2vsX9kL/hC5mWCLh2ISaaF3ARRsjVpETE1SRtLotxujddRtD1PcNY8fW4srzT377iavgc109FXjo2jAO3Bi/2GjjHi/lnxhdiTpXAZ2rCnr7is5JXTCUkr8jLRJN0Ink7AW7xFmv7GCpiHEB7YXkpd/LxBDCA9CQmQcrHeLBo5So1LVXOtx6JwgyY6pUMz6cHkUwvHVgoVvARyfRy/EErEh+T3rAgZA7svU9KOFmwYFpNnQ8Yy6sAJmVdUG/NJevPS8p6J1rRpMldfWia3Dt2cCTTOO7lJS42Z0Tb3k4Q93THVhwQ7TunJ4qaRy5SDHyEZ1wmURbf8BK3OIsIX+9EFX8RizxCkR/iHzbscD9bHufPdOvz43K2Dynq0/VeeFvSPycUeeUs3XDJlY554HOMJc1xSiZSylnq8cthX8J5B3CE1YtIJCznzrsNmv+QEZVb2IiomyYRCaqT8zFNKgYm0uoDpyRHOfpFzGuj6xmam7cbu3nAgqCP6RUxcZHtDXRQrhLiOu5lb5fdJzL5xfxsXtozuCip+rwf7PNQ3FcJekSc1umfCxR+Vwlb00UlPUNQt4jTFZuvgKOl/HFdSxnz7n6/U6TTA6ELRL73N2bWnSF13vmP6xKxe6d9X0OSP9dY9CYh87pMOkRskuURD0hlQTJXTjpafLvIhHDCB/DtDXNLOOU/tpu0i5CvjNyyQc3ezlpCfgztN5VaRaiuIlkr5s44XFGtip1oRahT4X6dGD70sDuRF8Z/2tZWW0TOLJs47ol08ldgRjOWZInTlrLHizCf0rhs9kQFsws2rGm1L/j5CVZk2IGAy+9psWstGnO3OP0OWizGHyjAijQDd2rGbnzNvtfRzrEnpjmxnV20/s6uvy7AxiP2iAdORGh+TBZw66wYEf7HmIZiYdphU0TcQVJ2MDmzmyIn2W9JQnOlsCFy4HTIBJZpo3LVRVSoWAWNylUXUaJiFdQrV01kp0TFKqhXrpoIfs3kI4m7RNay346Gc7uIyNzdw6lm/66ISLkgzU7aJuJ8fGxSpXK9rywiKdEGOye8iGoFUi2SkkjL8vEnM8OJqFcgeZE4GBEFC6RcJC8RW8ECKadofolIyB8AQdAQUWpQf+PWRZSKssqcayIKhb1V4qrITvb7sONXRJSZGDbJKiKKNvUCtywiOAcCLIeSCMBirzx+3iImxy9n8mdkvkSEffeMD+FLRMl48c3PS0ThPqvAfYpISNMEi/9fRNHA903wX0TZOOtJ/BAxlZxSlSlS1eUiB9nvMZzDXUT5JnJvJEh41iwerO4i1MdBPo+vQkRS2lhYnFxE2dl6mXMuotheAp40F+GcG0QM11xEUh51WJJcROlJ1ZOLgbTotBCykdLrDm8iJDRjIT8WSINIqyBAyu2A4jkhLYaRPGxEUtLBwxMjoXlW+eEhSV96gcZFGsxGCm5I0keEoBkhLUKtPNhCkj60BY2FlDnE2M30HyXOcALHhmxnAAAAAElFTkSuQmCC");
			employee.setUsuarioEmpleado(usuario);
			oficina = officeService.buscarOficina(employee.getOffice().getCode());
			reportTo = employeeService.buscarEmpleado(employee.getReportsTo().getNumber());
			employee.setOffice(oficina);
			employee.setReportsTo(reportTo);
			employeeService.guardar(employee);
			return "redirect:/employees";
		}
	}

	/**
	 * 
	 * @param id
	 * @return Si se produce la eliminación de un empleado, redirige a la página de empleados.
	 */
	@GetMapping(value = "/employee/delete/{id}")
	public String delete(@PathVariable(value = "id") long id) {
		employeeService.borrar(id);
		return "redirect:/employees";
	}


}
