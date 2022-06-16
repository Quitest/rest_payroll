package ru.pel.payroll;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {
    private final OrderRepository repository;
    private final OrderModelAssembler orderAssembler;
    private final static String ORDER_NOT_FOUND = "Could not found order ";

    public OrderController(OrderRepository orderRepository, OrderModelAssembler orderAssembler) {
        this.repository = orderRepository;
        this.orderAssembler = orderAssembler;
    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<Order>> all() {
        List<EntityModel<Order>> entityModels = repository.findAll().stream()
                .map(orderAssembler::toModel)
                .toList();
        return CollectionModel.of(entityModels,
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    @DeleteMapping("/orders/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable long id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND + id));
        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(orderAssembler.toModel(repository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
    }

    @PutMapping("/orders/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable long id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND + id));

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(orderAssembler.toModel(order));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
    }

    @PostMapping("/orders")
    public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order newOrder) {
        newOrder.setStatus(Status.IN_PROGRESS);
        Order order = repository.save(newOrder);
        EntityModel<Order> orderEntityModel = orderAssembler.toModel(order);
        return ResponseEntity
//                .created(orderEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //TODO сравнить с нижним вариантом
                .created(linkTo(methodOn(OrderController.class).one(order.getId())).toUri())
                .body(orderAssembler.toModel(order));
    }

    @GetMapping("/orders/{id}")
    public EntityModel<Order> one(@PathVariable long id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND + id));
        return orderAssembler.toModel(order);
    }


}
