package com.example.be_film.filters;

import com.example.be_film.components.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.*;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter{
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {


        try{

            if(isBypassToken(request)){
                filterChain.doFilter(request, response);//enable bypass
                return;
            }

            ///code cos kiem tra
            final String authHeader = request.getHeader("Authorization");
            if(authHeader!= null && authHeader.startsWith("Bearer ")){
                final String token = authHeader.substring(7);
                final String userName = jwtTokenUtil.extracUserName(token);
                if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null){
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                    if(jwtTokenUtil.validateToken(token, userDetails)){
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }


            }
            filterChain.doFilter(request, response);//enable pypass

        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
        }





    }

    private boolean isBypassToken(@NonNull HttpServletRequest request){
        final List<Pair<String,String>> bypassTokens = Arrays.asList(
                Pair.of("/api/v1/films", "GET"),
                Pair.of("/api/v1/users/login","POST"),
                Pair.of("/api/v1/users/register","POST"),
                Pair.of("/api/v1/genre", "GET")

        );
        for(Pair<String,String> bypassToken:bypassTokens){
            if(request.getServletPath().contains(bypassToken.getFirst())&&
            request.getMethod().equals(bypassToken.getSecond())
            ){
                return true;
            }

        }
        return false;
    }
}
