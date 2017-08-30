package co.bugu.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;

/**
 * Created by QDHL on 2017/8/15.
 */
@Controller
public class TestController {

    @RequestMapping("/conf")
    @ResponseBody
    public String test(){
        return "hello " + Thread.currentThread();
    }



}
