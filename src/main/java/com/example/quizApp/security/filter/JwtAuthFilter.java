package com.example.quizApp.security.filter;

import java.io.IOException;

import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.quizApp.security.JwtUtil;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component @RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final Set<String> entryEndPoint = Set.of("/login", "/signup");
	private final UserDetailsService userDetailsService;
	private final JwtUtil jwtUtil;

	private String username;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (requestIsPublic(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		var authHeaderValue = request.getHeader("Authorization");

		if (authHeaderNotInFormat(authHeaderValue)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			filterChain.doFilter(request, response);
			return;
		}

		var token = authHeaderValue.substring(7);

		try {
			username = jwtUtil.extractUsername(token);
		} catch (JwtException e) {
			System.out.println("\n" + e.getMessage() + "\n");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			filterChain.doFilter(request, response);
			return;
		}


		if (username != null) {

			UserDetails userDetails;

			try {
				userDetails = userDetailsService.loadUserByUsername(username);
			} catch (UsernameNotFoundException e) {
				filterChain.doFilter(request, response);
				return;
			}

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		filterChain.doFilter(request, response);
	}


	private boolean requestIsPublic(String reqURI) {
		return entryEndPoint.stream().anyMatch(reqURI::matches);
	}

	private boolean authHeaderNotInFormat(String header) {
		return !(header != null && header.startsWith("Bearer "));
	}
}
