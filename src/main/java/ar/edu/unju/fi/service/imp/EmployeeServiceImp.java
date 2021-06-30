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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.entity.Employee;
import ar.edu.unju.fi.repository.EmployeeRepository;
import ar.edu.unju.fi.service.IEmployeeService;

/**
 * @author Enzo Sandoval
 *
 */
@Service
public class EmployeeServiceImp implements IEmployeeService {

	private static final Log LOGGER = LogFactory.getLog(EmployeeServiceImp.class);

	@Autowired
	private EmployeeRepository empleadoRepository;

	@Override
	public void guardar(Employee empleado) {
		LOGGER.info("SERVICE: EmployeeService");
		LOGGER.info("METHOD: guardar()");
		String pw = empleado.getUsuarioEmpleado().getPassword();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		empleado.getUsuarioEmpleado().setPassword(bCryptPasswordEncoder.encode(pw));
		empleadoRepository.save(empleado);
		LOGGER.info("RESULT: Se guardó el empleado " + empleado.getFirstName());
	}

	@Override
	public List<Employee> obtenerEmpleados() {
		LOGGER.info("SERVICE: EmployeeService");
		LOGGER.info("METHOD: obtenerEmpleados()");
		List<Employee> empleados = new ArrayList<>();
		empleadoRepository.findAll().forEach(empleados::add);
		LOGGER.info("METHOD: Lista tamaño: " + empleados.size());
		return empleados;
	}

	@Override
	public Employee buscarEmpleado(Long id) throws Exception {
		LOGGER.info("SERVICE: EmployeeService");
		LOGGER.info("METHOD: buscarEmpleado()");
		Optional<Employee> optional = empleadoRepository.findById(id);
		Employee empleado = null;
		if (optional.isPresent()) {
			empleado = optional.get();
			LOGGER.info("RESULT: Empleado: " + empleado.getFirstName());
		} else {
			throw new Exception("Empleado no encontrado");
		}
		return empleado;
	}

	@Override
	public Page<Employee> findAll(Pageable pagable) {
		LOGGER.info("SERVICE: EmployeeService");
		LOGGER.info("METHOD: findAll()");
		LOGGER.info("RESULT: Página de empleados");
		return empleadoRepository.findAll(pagable);
	}

	@Override
	public void borrar(Long id) {
		LOGGER.info("SERVICE: EmployeeService");
		LOGGER.info("METHOD: borrar()");
		LOGGER.info("RESULT: ");
		empleadoRepository.deleteById(id);
	}

	@Override
	public List<Employee> findByKeyword(String keyword) {
		LOGGER.info("METHOD: findByKeyword()");
		return empleadoRepository.findByKeyword(keyword);
	}

}
