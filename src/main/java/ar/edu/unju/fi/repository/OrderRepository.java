/**
 * 
 */
package ar.edu.unju.fi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unju.fi.entity.Order;

/**
 * @author Enzo Sandoval
 *
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

  // ✨ Consulta para la Barra de Busqueda 🔍
  @Query(value = "SELECT * FROM orders o INNER JOIN customers c ON o.customerNumber=c.customerNumber WHERE " + 
  "CONCAT(o.orderNumber, o.status, o.customerNumber, o.orderDate, o.shippedDate, c.contactFirstName, c.contactLastName)" + 
  " LIKE %:keyword%", nativeQuery = true)
  List<Order> findByKeyword(@Param("keyword") String keyword);

}
