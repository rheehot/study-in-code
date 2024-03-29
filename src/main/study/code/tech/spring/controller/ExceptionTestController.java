package code.tech.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("exception")
public class ExceptionTestController {
    @GetMapping(path = "/hello")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Hello");
    }
}
