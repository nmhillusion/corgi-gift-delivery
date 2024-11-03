package tech.nmhillusion.slight_transportation.domains.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-03
 */
@RestController
@RequestMapping("/api/sample")
public class SampleController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
