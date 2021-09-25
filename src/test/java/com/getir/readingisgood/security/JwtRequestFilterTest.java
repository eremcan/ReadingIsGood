package com.getir.readingisgood.security;

import com.getir.readingisgood.util.JwtUtils;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomUserDetailsService.class, JwtUtils.class, JwtRequestFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class JwtRequestFilterTest {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Test
    public void testDoFilterInternal() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter(any(), any());
        this.jwtRequestFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(any(), any());
    }
}

