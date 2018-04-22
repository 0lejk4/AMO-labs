package com.gelo.amo_labs.web;


import com.gelo.amo_labs.model.Lab;
import com.gelo.amo_labs.repository.LabRepository;
import com.gelo.amo_labs.service.Interpolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/laba3")
public class Laba3Controller {

    private final LabRepository mLabRepository;
    private Interpolation interpolation;

    @Autowired
    public Laba3Controller(LabRepository mLabRepository) {
        this.mLabRepository = mLabRepository;
    }

    @GetMapping
    public String lab3(Model model) {
        Lab lab = this.mLabRepository.findOne(2L);
        interpolation = new Interpolation(1,5,10);
        interpolation.setY(x -> Math.sin(x));
        //Add to model
        model.addAttribute("laba", lab);
        return "lab3";
    }

    @GetMapping("/getY")
    @ResponseBody
    public double[] getY() {
        return interpolation.realArray();
    }


    @GetMapping("/lagrange")
    @ResponseBody
    public double[] lagrange() {
        return interpolation.interpolateArray(Interpolation.Lagrange);
    }

    @GetMapping("/eitken")
    @ResponseBody
    public double[] eitken() {
        return interpolation.interpolateArray(Interpolation.Eitken);
    }

    @GetMapping("/newton")
    @ResponseBody
    public double[] newton() {
        return interpolation.interpolateArray(Interpolation.Newton);
    }

}
