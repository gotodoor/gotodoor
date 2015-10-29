package com.gotodoor.svc.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 
 * @author nabbeher
 *
 */
@SuppressWarnings("rawtypes")
public class WebHookAuthenticationUserDetailsService implements
		AuthenticationUserDetailsService {

	private String secretKey;
	

	public String getSecretKey() {
		return secretKey;
	}


	public UserDetails loadUserDetails(Authentication token)
			throws UsernameNotFoundException {
		
		//check for valid key
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (token.getPrincipal() != null && getSecretKey().equals(token.getCredentials())){
						
			
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			return new User((String) token.getPrincipal(), (String) token.getCredentials(), true, true, true, true, authorities);
			
		}
		
		authorities.add(new SimpleGrantedAuthority("ROLE_NO_ACCESS"));
		return new User((String) token.getPrincipal(), (String) token.getCredentials(), true, true, true, true, authorities);
	}


	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}
