package typeconverter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import typeconverter.type.IpPort;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data");
        Integer intValue = Integer.valueOf(data);
        System.out.println("initValue = " + intValue);
        return "ok";
    }

    @GetMapping("/hello-v2")
    // 스프링이 중간에서 타입을 변환해줌
    public String helloV1(@RequestParam Integer data) {
        System.out.println("initValue = " + data);
        return "ok";
    }


    @GetMapping("ip-port")
    public String helloV1(@RequestParam IpPort ipPort) {
        System.out.println("ipPort IP: "+ ipPort.getIp());
        System.out.println("ipPort Port: " +ipPort.getPort());
        return "ok";
    }
}
