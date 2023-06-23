package com.store.store.Controllers;

import com.store.store.JWT.JwtRequest;
import com.store.store.JWT.JwtResponse;
import com.store.store.JWT.JwtTokenUtil;
import com.store.store.JWT.JwtUserDetailsService;
import com.store.store.Model.Entities.Customer;
import com.store.store.Model.Entities.Order;
import com.store.store.Model.Entities.Role;
import com.store.store.Model.Entities.dto.CustomerDto;
import com.store.store.Services.CustomerService;
import com.store.store.Services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@CrossOrigin
public class AutheController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Customer> signup(@RequestBody CustomerDto customerInfo) {
        Customer customer = modelMapper.map(customerInfo,Customer.class);

        Set<Role> Roles = new HashSet<Role>();
        List<Order> orders = new ArrayList<Order>();
        Roles.add(roleService.getRoleByName("ROLE_USER").orElse(null));

        customer.setRoles(Roles);
        customer.setOrders(orders);
        customerService.saveCustomer(customer);

        return ResponseEntity.ok(customer);
    }


}
