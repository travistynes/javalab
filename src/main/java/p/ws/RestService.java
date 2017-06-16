package p.ws;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class RestService {
    @RequestMapping("/test")
    public String test() {
        return "This page was served from the restful web service defined in p.ws.RestService.java";
    }
}
