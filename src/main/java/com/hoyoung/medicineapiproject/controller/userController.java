package com.hoyoung.medicineapiproject.controller;

import com.hoyoung.medicineapiproject.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class userController {

    private MedicineService medicineService;

    public userController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping("/getDrugList")
    public @ResponseBody String getDrugList() throws IOException {
        return medicineService.getMedicineResponse();
    }

}
