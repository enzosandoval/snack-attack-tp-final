/**
 * 
 */
package ar.edu.unju.fi.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

/**
 * @author Enzo Sandoval
 *
 */
@Entity
@Component
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Length(max = 11)
	@Column(name = "orderNumber", unique = true, nullable = false, updatable = false)
	private Long number;

	@NotNull(message = "The Order Date must not be null")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "The Order Date must be a date  in the past or in the present. ")
	@Column(nullable = false)
	private LocalDate orderDate;

	@NotNull(message = "The Required Date must not be null")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future(message = "The Required Date must be a date or time in the future. ")
	@Column(nullable = false)
	private LocalDate requiredDate;

	@Future(message = "If you enter shipped date, it must be a future date .")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column
	private LocalDate shippedDate;

	@NotBlank
	@Length(max = 15)
	@Column(nullable = false, length = 15)
	private String status;

	@Column
	private String comments;

	@Autowired
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerNumber")
	private Customer customer;

	@OneToMany(mappedBy = "id.order", fetch = FetchType.EAGER)
	private List<OrderDetails> orderDetailsList;

	public Order() {
	}

	/**
	 * @return the number
	 */
	public Long getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(Long number) {
		this.number = number;

	}

	/**
	 * @return the orderDate
	 */
	public LocalDate getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the requiredDate
	 */
	public LocalDate getRequiredDate() {
		return requiredDate;
	}

	/**
	 * @param requiredDate the requiredDate to set
	 */
	public void setRequiredDate(LocalDate requiredDate) {
		this.requiredDate = requiredDate;
	}

	/**
	 * @return the shippedDate
	 */
	public LocalDate getShippedDate() {
		return shippedDate;
	}

	/**
	 * @param shippedDate the shippedDate to set
	 */
	public void setShippedDate(LocalDate shippedDate) {
		this.shippedDate = shippedDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the orderDetailsList
	 */
	public List<OrderDetails> getOrderDetailsList() {
		return orderDetailsList;
	}

	/**
	 * @param orderDetailsList the orderDetailsList to set
	 */
	public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
		this.orderDetailsList = orderDetailsList;
	}

	@Override
	public String toString() {
		return "Order [number=" + number + ", orderDate=" + orderDate + ", requiredDate=" + requiredDate
				+ ", shippedDate=" + shippedDate + ", status=" + status + ", comments=" + comments
				+ ", orderDetailsList=" + orderDetailsList + "]";
	}

}
