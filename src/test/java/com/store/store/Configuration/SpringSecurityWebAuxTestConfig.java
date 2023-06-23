//package com.store.store.Configuration;
//
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//
//// this class is not used but in order to mock Authentication i will nead it
//@TestConfiguration
//public class SpringSecurityWebAuxTestConfig {
//    @Bean
//    @Primary
//    public UserDetailsService userDetailsService() {
//
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        User basicUser = new User("USER","password",authorities);
//
//        return new InMemoryUserDetailsManager(basicUser);
//    }
//}
