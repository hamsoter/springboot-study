package otaku.shelterforcowards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {


    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "한국어되냐");
        // templates 폴더에서 리턴된 hello.html 파일을 찾아온다
        return "hello";
    }
}
