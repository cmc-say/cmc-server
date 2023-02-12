package cmc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main1() throws Exception{
        return "/main";
    }

    @GetMapping("/hi2")
    public String main2() throws Exception{
        return "main";
    }

}