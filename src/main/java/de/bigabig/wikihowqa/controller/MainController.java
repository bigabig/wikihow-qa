package de.bigabig.wikihowqa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/")
    public String main(Model model) {
        logger.debug("Showing Main.html! :)");
        model.addAttribute("welcome", "Hello World! :D");
        return "main";
    }

}