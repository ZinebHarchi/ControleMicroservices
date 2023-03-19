package org.sid.orderservice.web;

import org.sid.orderservice.entities.Order;
import org.sid.orderservice.model.Customer;
import org.sid.orderservice.model.Product;
import org.sid.orderservice.repositories.OrderRepository;
import org.sid.orderservice.repositories.ProductItemRepository;
import org.sid.orderservice.services.CustomerRestCient;
import org.sid.orderservice.services.ProductRestClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    private OrderRepository orderRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestCient customerRestCient;
    private ProductRestClient productRestClient;

    public OrderRestController(OrderRepository orderRepository, ProductItemRepository productItemRepository, CustomerRestCient customerRestCient, ProductRestClient productRestClient) {
        this.orderRepository = orderRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestCient = customerRestCient;
        this.productRestClient = productRestClient;
    }

    @GetMapping(path = "/fullOrder/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Order getOrder(@PathVariable Long id){
        Order order=orderRepository.findById(id).get();
        Customer customer=customerRestCient.customerById(order.getCustomerId());
        order.setCustomer(customer);
        order.getProductItems().forEach(pi->{
            Product product= productRestClient.productById(pi.getProductId());
            pi.setProduct(product);
        });
        return order;
    };
}
