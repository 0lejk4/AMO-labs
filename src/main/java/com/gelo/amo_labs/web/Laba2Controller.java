package com.gelo.amo_labs.web;


import com.gelo.amo_labs.model.Lab;
import com.gelo.amo_labs.repository.LabRepository;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import lombok.val;

@Controller
@RequestMapping("/laba2")
public class Laba2Controller {
    private final LabRepository mLabRepository;

    @Autowired
    public Laba2Controller(LabRepository labRepository) {
        mLabRepository = labRepository;
    }


    @GetMapping
    public String lab2(Model model) {
        Lab lab = this.mLabRepository.findOne(1L);
        //Add to model
        model.addAttribute("laba", lab);
        return "lab2";
    }

    @GetMapping("/table")
    @ResponseBody
    public double[][] sortTable() {
        double[][] data = new double[8][2];
        LocalDateTime begin;
        LocalDateTime end;


        //End timer

        for (int i = 0; i < data.length; i++) {
            long[] array = new long[i * 1250 + 100];
            randomize(array);

            begin = LocalDateTime.now();
            Bose(array);
            end = LocalDateTime.now();
            String duration = Duration.between(begin, end).toString();
            data[i][0] = (double) i * 1250 + 100;
            data[i][1] = Double.parseDouble(duration.substring(2,duration.length()-1));
        }
        return data;
    }


    @PostMapping
    @ResponseBody
    public String sortRest(@RequestParam(name = "n", required = false) int n,
                           @RequestParam(value = "readFile") Boolean readFile,
                           @RequestParam(value = "email", required = false) String email) {
        StringBuilder result = new StringBuilder();
        Duration duration;
        LocalDateTime begin;
        LocalDateTime end;
        long[] array = new long[n];
        Gson gson = new Gson();
        if (readFile) {
            array = readFile("file/array_data.txt");
        } else {
            randomize(array);
        }
        result.append(gson.toJson(array));
        result.append("@");

        begin = LocalDateTime.now();
        Bose(array);
        end = LocalDateTime.now();
        //End timer

        duration = Duration.between(begin, end);
        result.append(gson.toJson(array));
        result.append("@");
        result.append(duration.toString());

        return result.toString();
    }

    private long[] readFile(String fileName) {

        long[] array = new long[500];


        //Get file from resources folder
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream file = classLoader.getResourceAsStream(fileName);
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                array = Arrays.stream(parts).mapToLong(Long::parseLong).toArray();
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return array;
    }


    private static void randomize(long[] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(47);
        }
    }


    private static void Bose(long[] array) {
        int m = 1;
        int j;
        do {
            j = 0;
            while (j + m < array.length) {
                Sli(array, j, m, m);
                j += 2 * m;
            }
            m *= 2;
        } while (m < array.length);
    }

    private static void Sli(long[] array, int j, int r, int m) {
        if (j + r < array.length) {
            if (m == 1) {
                if (array[j] > array[j + r]) {
                    long x = array[j];
                    array[j] = array[j + r];
                    array[j + r] = x;
                }
            } else {
                m /= 2;
                Sli(array, j, r, m);
                if (j + r + m < array.length) {
                    Sli(array, j + m, r, m);
                }
                Sli(array, j + m, r - m, m);
            }
        }
    }


}
