package tech.nmhillusion.slight_transportation.domains.customer.customer;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.nmhillusion.slight_transportation.entity.business.CustomerEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping(value = "/sync", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerEntity sync(@RequestBody CustomerEntity customerEntity) {
        return service.sync(customerEntity);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CustomerEntity> search(@RequestBody Map<String, ?> dto, @RequestParam int pageIndex, @RequestParam int pageSize) {
        return service.search(dto, pageIndex, pageSize);
    }
}
