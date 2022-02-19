package otaku.shelterforcowards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {


    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "한국어되냐");
        // templates 폴더에서 리턴된 hello.html 파일을 찾아온다
        return "hello";
    }

    // url?name (파라미터 value에 들어온 값이 model에 전달된다.)
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model) {
        // 첫번째 인자는 key이름이고 두 번째 인자는 넘겨줄 value값이다
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    // http body에 리턴된 데이터를 그대로 넣어준다.
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name;
    }

    // API (데이터(객체)를 가져오는 방식)
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();

        hello.setName(name);
        return hello;
    }

    public static class Hello {
        private String name;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
