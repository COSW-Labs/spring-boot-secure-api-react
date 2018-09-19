package com.eci.cosw.springbootsecureapi.config;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Santiago Carrillo
 * 8/21/17.
 */
public class JwtFilter
    extends GenericFilterBean
{
	/*
	* El metodo adiciona un filtro en el caso en el que la solicityud es la esperada pero arroja error
	* si la estructura es incorrecta o el tocken dentro de la etsructura lo es.
	*/
    public void doFilter( final ServletRequest servletRequest, final ServletResponse servletResponse,
                          final FilterChain filterChain )
        throws IOException, ServletException
    {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader( "authorization" );

        if ( "OPTIONS".equals( request.getMethod() ) )
        {
            response.setStatus( HttpServletResponse.SC_OK );

            filterChain.doFilter( servletRequest, response );
        }
        else
        {

            if ( authHeader == null || !authHeader.startsWith( "Bearer " ) )
            {
                throw new ServletException( "Missing or invalid Authorization header" );
            }

            final String token = authHeader.substring( 7 );

//            try
//            {
//                final Claims claims = Jwts.parser().setSigningKey( "secretkey" ).parseClaimsJws( token ).getBody();
//                request.setAttribute( "claims", claims );
//            }
//            catch ( final SignatureException e )
//            {
//                throw new ServletException( "Invalid token" );
//            }

            filterChain.doFilter( servletRequest, response );
        }
    }
}
