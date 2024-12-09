package tech.nmhillusion.slight_transportation.domains.customer.customerType;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.nmhillusion.slight_transportation.entity.business.CustomerTypeEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */

@RestController
@RequestMapping("/api/customer-type")
public class CustomerTypeController {
    private final CustomerTypeService service;

    public CustomerTypeController(CustomerTypeService service) {
        this.service = service;
    }

    @GetMapping(value = "/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerTypeEntity> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{customerTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerTypeEntity findById(@PathVariable String customerTypeId) {
        return service.findById(customerTypeId);
    }
}
