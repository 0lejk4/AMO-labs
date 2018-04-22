package com.gelo.amo_labs.web;

import com.gelo.amo_labs.model.Lab;
import com.gelo.amo_labs.repository.LabRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainController {


    private final LabRepository labRepository;

    @Autowired
    public MainController(LabRepository labRepository)
    {
        this.labRepository = labRepository;
    }

    @ModelAttribute("allLabs")
    public List<Lab> allLabs(){
        return labRepository.findAll();
    }

    @GetMapping({"/home", "/"})
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }


}
