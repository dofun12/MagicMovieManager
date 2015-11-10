package org.lemanoman.testeweb.restcontroller;

import java.util.concurrent.atomic.AtomicLong;

import org.lemanoman.testeweb.model.GreetingModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public GreetingModel greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new GreetingModel(counter.incrementAndGet(),
                            String.format(template, name));
    }
}