package com.examen.webapitest.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;


public class JwtTokenFilter extends GenericFilterBean {

	private static final String BEARER = "Bearer";

    private UserDetailSecurityService userDetailsService;

    public JwtTokenFilter(UserDetailSecurityService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 //Check for Authorization:Bearer JWT
        String headerValue = ((HttpServletRequest)request).getHeader("Authorization");
        getBearerToken(headerValue).ifPresent(token-> {
            //Pull the Username and Roles from the JWT to construct the user details
            userDetailsService.loadUserByJwtToken(token).ifPresent(userDetails -> {
                //Add the user details (Permissions) to the Context for just this API invocation
                SecurityContextHolder.getContext().setAuthentication(
                        new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
            });
        });
		chain.doFilter(request, response);
		
	}

	    private Optional<String> getBearerToken(String headerVal) {
	        if (headerVal != null && headerVal.startsWith(BEARER)) {
	            return Optional.of(headerVal.replace(BEARER, "").trim());
	        }
	        return Optional.empty();
	    }

}
