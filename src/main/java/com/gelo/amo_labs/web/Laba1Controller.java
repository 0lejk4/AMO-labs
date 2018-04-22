package com.gelo.amo_labs.web;

import com.gelo.amo_labs.model.Lab;
import com.gelo.amo_labs.repository.LabRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

import javax.mail.internet.MimeMessage;

import lombok.val;


@Controller
@RequestMapping("/laba1")
public class Laba1Controller {

    @Autowired
    private JavaMailSender sender;


    private final LabRepository mLabRepository;


    @Autowired
    public Laba1Controller(LabRepository labRepository) {
        mLabRepository = labRepository;
    }


    @GetMapping
    public String laba1(Model model) {
        Lab lab = this.mLabRepository.findOne(0L);
        //Add to model
        model.addAttribute("laba", lab);
        return "lab1";
    }

    @PostMapping("/linear")
    @ResponseBody
    public String laba1Linear(@RequestParam(value = "readFile") Boolean readFile,
                              @RequestParam(value = "readDb") Boolean readDb,
                              @RequestParam(value = "a", required = false) Long a,
                              @RequestParam(value = "b", required = false) Long b,
                              @RequestParam(value = "c", required = false) Long c,
                              @RequestParam(value = "d", required = false) Long d,
                              @RequestParam(value = "email", required = false) String email) {

        //Start timer
        if (readFile) {
            val dataMap = readFile("file/data.txt");
            a = dataMap.get("a");
            b = dataMap.get("b");
            c = dataMap.get("c");
            d = dataMap.get("d");
        }
        if (readDb) {
            val dataMap = this.mLabRepository.findOne(0L).getInputData();
            a = dataMap.get("a");
            b = dataMap.get("b");
            c = dataMap.get("c");
            d = dataMap.get("d");
        }
        String resultString = linear(a, b, c, d);
        //Add to model
        try {
            sendEmail(resultString, email);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return resultString;
    }

    @PostMapping("/branching")
    @ResponseBody
    public String laba1Branching(
            @RequestParam(value = "readFile") Boolean readFile,
            @RequestParam(value = "readDb") Boolean readDb,
            @RequestParam(value = "d", required = false) Long d,
            @RequestParam(value = "l", required = false) Long l,
            @RequestParam(value = "k", required = false) Long k,
            @RequestParam(value = "w", required = false) Long w,
            @RequestParam(value = "f", required = false) Long f,
            @RequestParam(value = "email", required = false) String email) {
        //Start timer
        if (readFile) {
            val dataMap = readFile("file/data.txt");
            d = dataMap.get("d");
            l = dataMap.get("l");
            k = dataMap.get("k");
            w = dataMap.get("w");
            f = dataMap.get("f");
        }

        if (readDb) {
            val dataMap = this.mLabRepository.findOne(0L).getInputData();
            d = dataMap.get("d");
            l = dataMap.get("l");
            k = dataMap.get("k");
            w = dataMap.get("w");
            f = dataMap.get("f");
        }
        String resultString = branching(f, l, k, w, d);
        //Add to model
        try {
            sendEmail(resultString, email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultString;
    }

    @PostMapping("/cycle")
    @ResponseBody
    public String laba1Cycle(
            @RequestParam(value = "readFile") Boolean readFile,
            @RequestParam(value = "readDb") Boolean readDb,
            @RequestParam(value = "b", required = false) Long b,
            @RequestParam(value = "email", required = false) String email) {

        //Start timer
        if (readFile) {
            val dataMap = readFile("file/data.txt");
            b = dataMap.get("b");
        }
        if (readDb) {
            val dataMap = this.mLabRepository.findOne(0L).getInputData();
            b = dataMap.get("b");
        }
        String resultString = cycle(b);
        //Add to model

        try {
            sendEmail(resultString, email);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return resultString;
    }

    private static String cycle(Long b) {
        Double f = 0d;
        String resultString = "";
        //Alg body
        Double resultNumber = 0d;

        LocalDateTime begin = LocalDateTime.now();

        for (int a = 4; a <= 18; a++) {
            f = Math.sqrt(a * a + b * b) - (a + b) * (a + b);
            resultString += " f = Math.sqrt(a * a + b * b) -  (a + b)*(a + b) = " + f + "<br/><br/><br/>";
        }

        LocalDateTime end = LocalDateTime.now();
        //End timer
        Duration duration = java.time.Duration.between(begin, end);
        resultString += "Time took(<small>in seconds</small>) = " + duration.toString() + "<br/><br/>";
        return resultString;
    }

    private HashMap<String, Long> readFile(String fileName) {

        val dataMap = new HashMap<String, Long>();


        //Get file from resources folder
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream file = classLoader.getResourceAsStream(fileName);
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("=");
                dataMap.put(parts[0], Long.parseLong(parts[1]));
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataMap;
    }

    private static String branching(Long f, Long l, Long k, Long w, Long d) {

        //Result String
        String resultString = "";
        //Alg body
        Double resultNumber = 0d;

        LocalDateTime begin = LocalDateTime.now();

        Long lk = l * k;
        resultString += " l*k = " + lk + "<br/>";
        Long wk = w * k;
        resultString += " w*k = " + wk + "<br/>";
        Double lg_lk = Math.log10(lk);
        if (f == 0) {
            Double sin_wk = Math.sin(wk);
            resultString += " sin(w*k) = " + sin_wk + "<br/>";
            Double d_sin_wk = sin_wk * d;
            resultString += " d*sin(w*k) = " + d_sin_wk + "<br/>";
            resultNumber += lg_lk + d_sin_wk;
            resultString += "y = lg(l*k) + d*sin(w*k) = " + resultNumber + "<br/>";
        } else {
            Double cos_wk = Math.cos(wk);
            resultString += " cos(w*k) = " + cos_wk + "<br/>";
            resultNumber += lg_lk + cos_wk;
            resultString += "y = lg(l*k) + cos(w*k) = " + resultNumber + "<br/>";
        }


        LocalDateTime end = LocalDateTime.now();
        //End timer
        Duration duration = java.time.Duration.between(begin, end);
        resultString += "Time took(<small>in seconds</small>) = " + duration.toString() + "<br/><br/>";
        return resultString;
    }

    private static String linear(Long a, Long b, Long c, Long d) {
        LocalDateTime begin = LocalDateTime.now();
        //Result String
        String resultString = "";
        //Alg body
        Long a2 = a * a;
        Long d2 = d * d;
        Float resultNumber = (float) a2 / d2;

        resultString += " a^2 = " + a2 + " <br/> d^2 = " + d2 + " <br/> a^2 / d^2 = " + resultNumber + "<br/>";

        Long b3 = b * b * b;
        Long d3 = d2 * d;

        resultNumber += (float) b3 / d3;
        resultString += " b^3 = " + b3 + " <br/> d^3 = " + d3 + " <br/> (a^2 / d^2 ) + (b^3 / d^3 ) = " + resultNumber + "<br/>";

        Long c4 = pow(c, 4);
        resultNumber += (float) c4 / 16;

        resultString += " c^4 = " + a2 + " <br/> 2^4 = " + 16 + "<br/>";
        resultString += "Результат = " + resultNumber;
        LocalDateTime end = LocalDateTime.now();
        //End timer
        Duration duration = java.time.Duration.between(begin, end);
        resultString += "<br/>Time took(<small>in seconds</small>) = " + duration.toString() + "<br/><br/>";
        return resultString;
    }

    private void sendEmail(String text, String to) throws Exception {
        if (to == null || "".equals(to)) return;

        MimeMessage message = sender.createMimeMessage();
        // Enable the multipart flag!
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setFrom("pro100pechka@gmail.com");
        helper.setText("<html><body>" + text + "</html></body>", true);
        helper.setSubject("Laba result");

        sender.send(message);
    }

    private static long pow(long a, long b) {
        long re = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                re *= a;
            }
            b >>= 1;
            a *= a;
        }
        return re;
    }

}
