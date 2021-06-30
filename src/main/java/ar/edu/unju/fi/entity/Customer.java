/**
 * 
 */
package ar.edu.unju.fi.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

/**
 * @author Enzo Sandoval
 *
 */
@Entity
@Component
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customerNumber", nullable = false, updatable = false)
	private Long number;

	@NotEmpty(message = "The Customer Name must not be null nor empty.")
	@Length(min = 2, max = 50, message = "Customer Name must be at least two characters.")
	@Column(name = "customerName", nullable = false, length = 50)
	private String name;

	@NotBlank(message = "The Contact First Name must not be null nor must contain  one whitespace character.")
	@Length(min = 2, max = 50, message = "Contact First Name must be at least two characters.")
	@Column(nullable = false, length = 50)
	private String contactFirstName;

	@NotEmpty(message = "The Contact Last Name must not be null nor empty.")
	@Length(min = 1, max = 50, message = "Contact First Name must be at least one character.")
	@Column(nullable = false, length = 50)
	private String contactLastName;

	// la bd acepta espacio en blanco
	@NotEmpty(message = "The Phone Number must not be null nor empty.")
	@Length(min = 6, max = 50, message = "The Phone Number must be at least six characters.")
	@Column(nullable = false, length = 50)
	private String phone;

	@NotEmpty(message = "The Address Line 1 must not be null nor empty.")
	@Length(min = 3, max = 50, message = "The Address Line 1 must be at least three characters.")
	@Column(name = "addressLine1", nullable = false, length = 50)
	private String addressLine1;

	@Length(max = 50)
	@Column(name = "addressLine2", nullable = false, length = 50)
	private String addressLine2;

	@NotEmpty(message = "The field City must not be null nor empty.")
	@Length(min = 2, max = 50, message = "The field City must be at least two characters.")
	@Column(name = "city", nullable = false, length = 50)
	private String city;

	@Length(max = 50)
	@Column(name = "state", nullable = false, length = 50)
	private String state;

	@Length(max = 15)
	@Column(name = "postalCode", nullable = false, length = 15)
	private String postalCode;

	@NotEmpty(message = "The field Country must not be null nor empty.")
	@Length(min = 2, max = 50, message = "The field Country must be at least two characters. ")
	@Column(name = "country", nullable = false, length = 50)
	private String country;

	@Digits(integer = 8, fraction = 2, message = "The credit limit must be a number whose value must be less than or equal to 99999999.99.")
	@Column(nullable = false, length = 50)
	private BigDecimal creditLimit;

	@ManyToOne
	@JoinColumn(name = "salesRepEmployeeNumber")
	private Employee salesRepresentative;

	public Customer() {
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the contactFirstName
	 */
	public String getContactFirstName() {
		return contactFirstName;
	}

	/**
	 * @param contactFirstName the contactFirstName to set
	 */
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	/**
	 * @return the contactLastName
	 */
	public String getContactLastName() {
		return contactLastName;
	}

	/**
	 * @param contactLastName the contactLastName to set
	 */
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the creditLimit
	 */
	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	/**
	 * @param creditLimit the creditLimit to set
	 */
	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	/**
	 * @return the salesRepresentative
	 */
	public Employee getSalesRepresentative() {
		return salesRepresentative;
	}

	/**
	 * @param salesRepresentative the salesRepresentative to set
	 */
	public void setSalesRepresentative(Employee salesRepresentative) {
		this.salesRepresentative = salesRepresentative;
	}



	@Override
	public String toString() {
		return "Customer [number=" + number + ", name=" + name + ", contactFirstName=" + contactFirstName
				+ ", contactLastName=" + contactLastName + ", phone=" + phone + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", city=" + city + ", state=" + state + ", postalCode="
				+ postalCode + ", country=" + country + ", creditLimit=" + creditLimit + ", salesRepresentative="
				+ salesRepresentative + "]";
	}

}
