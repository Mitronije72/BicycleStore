package com.store.store.Controllers;

import com.store.store.JWT.JwtTokenUtil;
import com.store.store.Model.Entities.*;
import com.store.store.Model.Entities.dto.OrderDto;
import com.store.store.Model.Entities.dto.OrderItemDto;
import com.store.store.Model.Repositories.BicycleRepository;
import com.store.store.Model.Repositories.CustomerRepository;
import com.store.store.Model.Repositories.OrderItemRepository;
import com.store.store.Model.Repositories.OrderRepository;
import com.store.store.handlers.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    JwtTokenUtil jwtTokenUtil;

    CustomerRepository customerRepository;

    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;

    BicycleRepository bicycleRepository;

    ModelMapper modelMapper;

    @Autowired
    public OrderController(JwtTokenUtil jwtTokenUtil,
                           ModelMapper modelMapper,
                           CustomerRepository customerRepository,
                           BicycleRepository bicycleRepository,
                           OrderItemRepository orderItemRepository,
                           OrderRepository orderRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.bicycleRepository = bicycleRepository;
        this.orderItemRepository = orderItemRepository;

    }


    @PostMapping("/create")
    public ResponseEntity<Order> createOrderForAuthenticatedCustomer(@RequestBody OrderDto orderInfo, HttpServletRequest request) {

        Order order = modelMapper.map(orderInfo, Order.class);
        String token = request.getHeader("Authorization").split(" ")[1];
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        Customer customer = customerRepository.findByUsername(userName).orElse(null);

        order.setCustomer(customer);
        order.setOrderDate(new Date());

        orderRepository.save(order);

        return ResponseEntity.ok(order);

    }

    @PostMapping("/orderItem/add")
    public ResponseEntity<?> addOrderItemForAuthenticatedCustomer(@RequestBody OrderItemDto orderItemInfo, HttpServletRequest request) {

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemInfo.getQuantity());
        String token = request.getHeader("Authorization").split(" ")[1];
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        Customer customer = customerRepository.findByUsername(userName).orElse(null);


//comfirm if the order is from the right customer

        Order corespondingOrder = orderRepository.findById(orderItemInfo.getOrderId()).orElse(null);
        Customer corespondingCustomer = null;

        if(!(corespondingOrder == null)){
            corespondingCustomer = corespondingOrder.getCustomer();
        }

        if(!(corespondingOrder == null) &&
                !(corespondingCustomer == null) &&
                corespondingCustomer.getId().equals(customer.getId())){

            orderItem.setOrder(corespondingOrder);
        }else{
            return new ResponseEntity<>(new ErrorResponse("customer that is making the order is not the customer that is logged in ")
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Bicycle corespondingBicycle = bicycleRepository.findById(orderItemInfo.getBicycleId()).orElse(null);
        if(corespondingBicycle !=null) {
            orderItem.setBicycle(corespondingBicycle);
            orderItem.setPrice(corespondingBicycle.getPrice()*orderItemInfo.getQuantity());
        }

        orderItemRepository.save(orderItem);

        return ResponseEntity.ok(orderItem);

    }

    @GetMapping("/list/{customer}")
    public ResponseEntity<List<Order>> getOrdersForCustomer(@PathVariable String customer) {

        return getOrderList(customer);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> getOrdersForAuthenticatedCustomer(HttpServletRequest request) {

        String token = request.getHeader("Authorization").split(" ")[1];
        String customer = jwtTokenUtil.getUsernameFromToken(token);
        return getOrderList(customer);
    }

    private ResponseEntity<List<Order>> getOrderList(String name){
        Customer customer = customerRepository.findByUsername(name).orElse(null);
        if (customer != null) {
            return new ResponseEntity<>(customer.getOrders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
