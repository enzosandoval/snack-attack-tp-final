/**
 * 
 */
package ar.edu.unju.fi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.entity.Employee;

/**
 * @author Enzo Sandoval
 *
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	public Employee findByUsuarioEmpleadoUsername(String username);
}
