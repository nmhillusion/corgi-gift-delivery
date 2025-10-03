package tech.nmhillusion.corgi_gift_delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-09-27
 */
@Controller
public class ViewController {
    @RequestMapping(value = "/corgi-deli/{path:[^\\.]*}")
    public String redirect() {
        // Forward all non-file/non-api requests to index.html
        return "forward:/index.html";
    }
}
