package com.stackroute.keepnote.jwtfilter;


import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;



/* This class implements the custom filter by extending org.springframework.web.filter.GenericFilterBean.  
 * Override the doFilter method with ServletRequest, ServletResponse and FilterChain.
 * This is used to authorize the API access for the application.
 */


public class JwtFilter extends GenericFilterBean {

	
	
	

	/*
	 * Override the doFilter method of GenericFilterBean.
     * Retrieve the "authorization" header from the HttpServletRequest object.
     * Retrieve the "Bearer" token from "authorization" header.
     * If authorization header is invalid, throw Exception with message. 
     * Parse the JWT token and get claims from the token using the secret key
     * Set the request attribute with the retrieved claims
     * Call FilterChain object's doFilter() method */
	
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	final HttpServletRequest httpReq=(HttpServletRequest) request;
    	final HttpServletResponse httpRes=(HttpServletResponse) response;
    	String authHeader=httpReq.getHeader("Authorization");
    	
    	// If the HTTP method is OPTIONS we don't need to check token. OPTIONS http method shows the various methods
    	// that the end point supports (kind of like a api documentation for the endpoint)
    	if("OPTIONS".equals(httpReq.getMethod())) {
    		httpRes.setStatus(HttpServletResponse.SC_OK);
    		chain.doFilter(request, response);
    		
    	}else {
    		if(authHeader==null || !authHeader.startsWith("Bearer")) {
    			throw new ServletException("Invalid request. Please provide correct bearer token.");
    		}
    		
    		String token=authHeader.substring(7); // The first 6 chars of auth header is 'Bearer', after that only we get the token.
    		Claims claim=Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody(); // Extract the claims of JWT token. 
    																							//We set subject, issued at and exp in the user auth service jwt token generation.
    		httpReq.setAttribute("claims", claim);
    		chain.doFilter(request, response);
    	}
    }
}
