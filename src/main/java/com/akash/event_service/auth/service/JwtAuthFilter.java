package com.akash.event_service.auth.service;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.akash.event_service.auth.repo.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserRepository userRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		final String jwt = authHeader.substring(7);
		String email;
		try {
			email = jwtService.extractUsername(jwt);
		} catch (Exception e) {
			filterChain.doFilter(request, response);
			return;
		}

		// 🔹 If valid email and no authentication yet
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			var user = userRepo.findByEmail(email).orElse(null);

			if (user != null && jwtService.isTokenValid(jwt, org.springframework.security.core.userdetails.User
					.withUsername(user.getEmail()).password(user.getPassword()).roles(user.getRole().name()).build())) {

				// Extract the exact role from token ("ROLE_USER", "ROLE_ORGANIZER", etc.)
				String tokenRole = jwtService.extractRole(jwt);

				// Create UserDetails with correct authority
				UserDetails userDetails = org.springframework.security.core.userdetails.User
				        .withUsername(user.getEmail())
				        .password(user.getPassword())
				        .authorities("ROLE_" + user.getRole().name())
				        .build();

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				
				System.out.println("== JWT FILTER DEBUG ==");
				System.out.println("Token email: " + email);
				System.out.println("User role from DB: " + user.getRole());
				System.out.println("Authorities to set: ROLE_" + user.getRole());
				System.out.println("======================");

				// Store in SecurityContext
				SecurityContextHolder.getContext().setAuthentication(authToken);

				// Debug log
				System.out.println("Authenticated user: " + email + " with role " + tokenRole);
				
				System.out.println("Authorities in context: " +
					    SecurityContextHolder.getContext().getAuthentication().getAuthorities());

			}
		}

		filterChain.doFilter(request, response);
	}

}
