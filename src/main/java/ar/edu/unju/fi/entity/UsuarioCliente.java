/**
 * 
 */
package ar.edu.unju.fi.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * @author Enzo Sandoval
 *
 */
@Entity
@Component
@DiscriminatorValue(value = "Cliente")
@JsonTypeName("cliente")
public class UsuarioCliente extends Usuario {

}
