package com.store.store.JWT;

import com.store.store.Model.Entities.Customer;
import com.store.store.Model.Entities.Role;
//import com.store.store.Model.Entities.User;
import com.store.store.Model.Repositories.CustomerRepository;
//import com.store.store.Model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  //  private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public JwtUserDetailsService(
           // UserRepository userRepository,
            CustomerRepository customerRepository) {
       // this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByUsername(username).orElse(null);
//        User user = userRepository.findByUsername(username);

//        if (user != null) {
//
//            Collection<GrantedAuthority> authorities = new ArrayList<>();
//            for (Role role : user.getRoles()) {
//                authorities.add(new SimpleGrantedAuthority(role.getName()));
//            }
//
//            return new org.springframework.security.core.userdetails.User(
//                    user.getUsername(),
//                    user.getPassword(),
//                    authorities // Modify this based on your user entity structure
//            );
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }

        if (customer != null) {

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for (Role role : customer.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }

            return new org.springframework.security.core.userdetails.User(
                    customer.getUsername(),
                    customer.getPassword(),
                    authorities // Modify this based on your user entity structure
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}