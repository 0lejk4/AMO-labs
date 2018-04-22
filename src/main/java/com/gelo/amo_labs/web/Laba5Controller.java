package com.gelo.amo_labs.web;

import com.gelo.amo_labs.model.Lab;
import com.gelo.amo_labs.repository.LabRepository;
import com.gelo.amo_labs.service.MethodGaussZeidel;
import com.google.gson.Gson;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.repository.init.ResourceReader.Type.JSON;

@Controller
@RequestMapping("/laba5")
public class Laba5Controller {
    private final LabRepository mLabRepository;

    @Autowired
    public Laba5Controller(LabRepository mLabRepository) {
        this.mLabRepository = mLabRepository;
    }

    @GetMapping
    public String lab5(Model model) {
        Lab lab = this.mLabRepository.findOne(4L);
        //Add to model
        model.addAttribute("laba", lab);
        return "lab5";
    }


    @PostMapping
    @ResponseBody
    public String solveFunction(@RequestBody double[][] A,
                                  @RequestParam(name = "B") String B,
                                  @RequestParam(name = "X") String X,
                                  @RequestParam(name = "epsilon") double epsilon) {
        Gson parser = new Gson();
        return MethodGaussZeidel.solver(epsilon, A, parser.fromJson(B, double[].class),
                parser.fromJson(X, double[].class));
    }

}