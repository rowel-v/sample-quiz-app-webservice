package com.example.quizApp.security.filter;

import java.io.IOException;

import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.quizApp.security.JwtUtil;
import com.example.quizApp.security.service.student.StudentAccountDetailsService;
import com.example.quizApp.security.service.student.StudentDetails;
import com.example.quizApp.security.service.teacher.TeacherAccountDetailsService;
import com.example.quizApp.security.service.teacher.TeacherDetails;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component @RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final Set<String> entryEndPoint = Set.of("/login", "/signup");

	private final StudentAccountDetailsService studentAccountDetailsService;
	private final TeacherAccountDetailsService teacherAccountDetailsService;

	private final JwtUtil jwtUtil;

	private String username;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		var requestURI = request.getRequestURI();

		if (requestIsPublic(requestURI)) {
			filterChain.doFilter(request, response);
			return;
		}

		var authorization = request.getHeader("Authorization");

		if (authorization != null && authorization.startsWith("Bearer ")) {

			var token = authorization.substring(7);

			try {
				username = jwtUtil.extractUsername(token);
			} catch (JwtException e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("text/plain");
				response.getWriter().append(e.getMessage());
				return;
			}

			if (requestURI.startsWith("/student")) {

				StudentDetails studentDetails;
				try {
					studentDetails = (StudentDetails) studentAccountDetailsService.loadUserByUsername(username);
				} catch (UsernameNotFoundException e) {
					filterChain.doFilter(request, response);
					return;
				}

				if (SecurityContextHolder.getContext().getAuthentication() == null) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
							studentDetails, null, studentDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
				
			} else if (requestURI.startsWith("/teacher")) {

				TeacherDetails teacherDetails;
				try {
					teacherDetails = (TeacherDetails) teacherAccountDetailsService.loadUserByUsername(username);
				} catch (UsernameNotFoundException e) {
					filterChain.doFilter(request, response);
					return;
				}
				
				if (SecurityContextHolder.getContext().getAuthentication() == null) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
							teacherDetails, null, teacherDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	private boolean requestIsPublic(String reqURI) {
		return entryEndPoint.stream().anyMatch(reqURI::matches);
	}
}