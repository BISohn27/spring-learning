package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.*;

@RestController //스프링은 @Controller 또는 @RestController 가 있어야 스프링 컨트롤러로 인식 @RequestMapping 은 3.0부터 안됨
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();
}
