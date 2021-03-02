package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    private String heool(Model modal) {
        modal.addAttribute("data", "hello!!!");
        return "hello";
    }
}
