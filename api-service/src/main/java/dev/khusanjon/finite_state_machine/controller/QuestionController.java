package dev.khusanjon.finite_state_machine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionController {

    @GetMapping("/ask")
    @ResponseBody
    public String getAnswer(@RequestParam String question){
        return  "You asked: " +"`"+ question+"`" + ", but our programmers still working on it...";
    }
}
