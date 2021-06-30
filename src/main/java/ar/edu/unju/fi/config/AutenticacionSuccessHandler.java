/**
 * 
 */
package ar.edu.unju.fi.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import ar.edu.unju.fi.util.EnumRoles;

/**
 * @author Enzo Sandoval
 *
 */
@Component
public class AutenticacionSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		boolean vendedor = false;
		boolean administrador = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals(EnumRoles.ROL_VENDEDOR.getDescripcion())) {
				vendedor = true;
				break;
			}
			if (grantedAuthority.getAuthority().equals(EnumRoles.ROL_ADMINISTRADOR.getDescripcion())) {
				administrador = true;
				break;
			}
		}
		if (vendedor) {
			redirectStrategy.sendRedirect(request, response, "/products");
		} else if (administrador) {
			redirectStrategy.sendRedirect(request, response, "/admin");
		} else {
			throw new IllegalStateException();
		}

	}

}
