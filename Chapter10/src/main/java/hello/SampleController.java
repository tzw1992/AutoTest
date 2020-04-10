package hello;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class SampleController {
    @RequestMapping("/")
    @ResponseBody
    String name(){
        return "hello word";
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleController.class,args);
    }
}
