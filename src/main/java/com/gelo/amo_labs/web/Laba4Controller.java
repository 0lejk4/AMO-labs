package com.gelo.amo_labs.web;

import com.gelo.amo_labs.model.Lab;
import com.gelo.amo_labs.repository.LabRepository;
import com.gelo.amo_labs.service.NonLinearSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/laba4")
public class Laba4Controller {
    private final String ERROR_MESSAGE = "Something went wrong with expression, change your input";
    private final String CANNOT_BE_SOLVED = "The expression cannot be solved, change input";
    private final LabRepository mLabRepository;
    private final NonLinearSolver nonLinearSolver = new NonLinearSolver("2*Math.log10(x)-x/2+1");

    @Autowired
    public Laba4Controller(LabRepository mLabRepository) {
        this.mLabRepository = mLabRepository;
    }

    @GetMapping
    public String lab4(Model model) {
        Lab lab = this.mLabRepository.findOne(3L);

        //Add to model
        model.addAttribute("laba", lab);

        return "lab4";
    }


    @PostMapping
    @ResponseBody
    public String solveFunction(@RequestParam(name = "func") String func,
                                @RequestParam(name = "a") Double a,
                                @RequestParam(name = "b") Double b,
                                @RequestParam(name = "epsilon") Double epsilon) {
        if (nonLinearSolver.setFunction(func) == 1) return ERROR_MESSAGE;
        Double result = nonLinearSolver.solve(a, b, epsilon);
        if (result == null) return CANNOT_BE_SOLVED;
        return "The result is " + result;
    }

}
