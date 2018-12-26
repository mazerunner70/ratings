package uk.co.ameth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;


@RestController
@EnableWebMvc
public class PingController {

//    @Value("${IOS_APP_ID}")
//    private String iosAppId;


    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public Map<String, String> ping() {
        Map<String, String> pong = new HashMap<>();
        String iosAppId = "--";
        System.out.println("7685");
//        iosAppId = System.getenv("IOS_APP_ID");
        pong.put("pong", "Hello, World!"+ iosAppId);
        return pong;
    }
}
