package com.gotodoor.svc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;

/**
 * 
 * @author nabbeher
 *
 */
public class WebHookAutoLoginFilter extends
		org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter {

	private AuthenticationManager authenticationManager;
	/**
	 * 
	 * @return {@link #authenticationManager}
	 */
		public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}
	/**
	 * @param authenticationManager
	 */
		public void setAuthenticationManager(AuthenticationManager authenticationManager) {
			super.setAuthenticationManager(authenticationManager);
		}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		String apikey = request.getHeader("apiKey");
		System.out.println("getPreAuthenticatedCredentials:: " +apikey);
		if(apikey != null)
			return apikey;
		else
		return null;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		String apikey = request.getHeader("apiKey");
		System.out.println("getPreAuthenticatedPrincipal:: " +apikey);
		
		if(apikey != null)
			return apikey;
		else
		return null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		super.doFilter(request, response, chain);
	}
}
