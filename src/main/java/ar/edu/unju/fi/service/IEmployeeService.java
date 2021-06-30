/**
 * 
 */
package ar.edu.unju.fi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unju.fi.entity.Employee;

/**
 * @author Enzo Sandoval
 *
 */
public interface IEmployeeService {

	public void guardar(Employee empleado);

	public List<Employee> obtenerEmpleados();

	public Employee buscarEmpleado(Long id) throws Exception;

	public Page<Employee> findAll(Pageable pagable);

	public void borrar(Long id);



	public List<Employee> findByKeyword(String keyword);

}
