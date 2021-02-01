package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/resource")
public class ResourceController {
    @RequestMapping("/404.html")
    public String get404(){
        return "404.html";
    }
    @RequestMapping("/500.html")
    public String get500(){
        return "500.html";
    }

}
