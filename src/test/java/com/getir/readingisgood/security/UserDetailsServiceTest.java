package com.getir.readingisgood.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = {CustomUserDetailsService.class})
@ExtendWith(SpringExtension.class)
public class UserDetailsServiceTest {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test
    public void testLoadUserByUsername() throws UsernameNotFoundException {
        UserDetails actualLoadUserByUsernameResult = this.userDetailsService.loadUserByUsername("${getir-security.username}");
        assertTrue(actualLoadUserByUsernameResult.getAuthorities().isEmpty());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertEquals("${getir-security.username}", actualLoadUserByUsernameResult.getUsername());
        assertEquals("${getir-security.password}", actualLoadUserByUsernameResult.getPassword());
    }
}

