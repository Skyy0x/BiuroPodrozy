package pl.sky0x.travelAgency.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.sky0x.travelAgency.model.user.Token;
import pl.sky0x.travelAgency.model.user.User;
import pl.sky0x.travelAgency.repository.TokenRepository;
import pl.sky0x.travelAgency.repository.UserRepository;
import pl.sky0x.travelAgency.service.JwtService;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilterHandler extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if (servletPath.contains("/api/authenticate/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        try {
            Long id = Long.parseLong(jwtService.extractId(jwt));

            String username = userRepository.findById(id)
                    .orElseThrow(() -> new BadCredentialsException("username doesn't found")).getUsername();

            User user = (User) this.userDetailsService.loadUserByUsername(username);

            Optional<Token> optionalToken = tokenRepository.findByToken(jwt);

            boolean isTokenValid = optionalToken.isPresent() && !optionalToken.get().isExpired();

            if (jwtService.isTokenValid(jwt, user) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception ignored) {
        }
        filterChain.doFilter(request, response);
    }
}