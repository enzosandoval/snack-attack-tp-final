/**
 * 
 */
package ar.edu.unju.fi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unju.fi.entity.Employee;

/**
 * @author Enzo Sandoval
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  // ✨ Consulta para la Barra de Busqueda 🔍
  @Query(value = "SELECT * FROM employees e WHERE " + 
  "CONCAT(e.employeeNumber, e.lastName, e.firstName, e.jobTitle)" + 
  " LIKE %:keyword%", nativeQuery = true)
  List<Employee> findByKeyword(@Param("keyword") String keyword);
}
