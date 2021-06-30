/**
 * 
 */
package ar.edu.unju.fi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unju.fi.entity.Customer;

/**
 * @author Enzo Sandoval
 *
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  // ✨ Consulta para la Barra de Busqueda 🔍
  @Query(value = "SELECT * FROM customers c WHERE " + 
  "CONCAT(c.customerNumber, c.customerName, c.contactLastName, c.contactFirstName, c.city, c.country)" + 
  " LIKE %:keyword%", nativeQuery = true)
  List<Customer> findByKeyword(@Param("keyword") String keyword);
}
