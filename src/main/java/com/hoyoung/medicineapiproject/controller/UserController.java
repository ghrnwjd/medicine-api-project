package com.hoyoung.medicineapiproject.controller;

import com.hoyoung.medicineapiproject.dto.MedicineDto;
import com.hoyoung.medicineapiproject.dto.ResponseDto;
import com.hoyoung.medicineapiproject.service.InputService;
import com.hoyoung.medicineapiproject.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class UserController {

    private MedicineService medicineService;
    private InputService inputService;

    public UserController(MedicineService medicineService, InputService inputService) {
        this.medicineService = medicineService;
        this.inputService = inputService;
    }

    @PostMapping("/inputSymptom")
    public @ResponseBody MedicineDto getDrugList(@RequestBody MedicineDto medicineDto) throws IOException {
        return medicineService.getMedicineResponse(medicineDto);
    }

//    @GetMapping("/getDrugListString")
//    public @ResponseBody String getDrugListString() throws IOException {
//        return medicineService.getMedicineResponseString();
//    }

    @GetMapping({"" , "/"})
    public String checkBox () {
        return "index.html";
    }

    @GetMapping("/{symptom}")
    public String recommendMedicine(@PathVariable String symptom) {
        StringBuilder sb = new StringBuilder(symptom).append("\n").append("알맞는 약을 추천해줘");
        return inputService.generateText(sb.toString());
    }

    @PostMapping("/input")
    public @ResponseBody ResponseDto inputByUser(@RequestBody MedicineDto medicineDto) throws IOException {
        return medicineService.getMedicineResponseString(medicineDto);
    }

}
