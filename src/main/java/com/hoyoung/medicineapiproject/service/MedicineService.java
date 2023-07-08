package com.hoyoung.medicineapiproject.service;

import com.hoyoung.medicineapiproject.component.ApiKey;
import com.hoyoung.medicineapiproject.dto.MedicineDto;
import com.hoyoung.medicineapiproject.dto.ResponseDto;
import com.hoyoung.medicineapiproject.dto.ResponseStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@Service
public class MedicineService {

    private ApiKey apiKey;

    public MedicineService(ApiKey apiKey) {
        this.apiKey = apiKey;
    }


    public ResponseDto getMedicineResponseString(MedicineDto medicineDto) throws IOException{
        String serviceKey = apiKey.getMedicineEncodingKey();
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("3", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("efcyQesitm","UTF-8") + "=" + URLEncoder.encode(medicineDto.getEfcyQesitm(), "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        else {
            return ResponseDto
                    .builder()
                    .responseStatus(ResponseStatus.NO)
                    .build();
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        System.out.println(sb.toString());

        rd.close();
        conn.disconnect();
        return ResponseDto
                .builder()
                .responseStatus(ResponseStatus.OK)
                .build();

    }
    public MedicineDto getMedicineResponse(MedicineDto medicineDto) throws IOException {
        String serviceKey = apiKey.getMedicineEncodingKey();

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("efcyQesitm","UTF-8") + "=" + URLEncoder.encode(medicineDto.getEfcyQesitm(), "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        String line;

        String resultCode = null, resultMsg = null, numOfRows = null, pageNo = null, totalCount = null, entpName = null, itemName = null,
                itemSeq = null, efcyQesitm = null, useMethodQesitm = null, atpnWarnQesitm = null, atpnQesitm = null, intrcQesitm = null,
                seQesitm = null, depositMethodQesitm = null, openDe = null, updateDe = null, itemImage = null;

        MedicineDto newMedicineDto = new MedicineDto();

        // 한개밖에 못받아온다는 단점이 존재.
        while ((line = rd.readLine()) != null) {

            if(line.startsWith("<resultCode>")) {
               resultCode = regexLine(line);
            }
            else if(line.startsWith("<resultMsg>")) {
                resultMsg = regexLine(line);
            }
            else if(line.startsWith("<numOfRows>")) {
                numOfRows = regexLine(line);
            }
            else if(line.startsWith("<pageNo>")) {
                pageNo = regexLine(line);
            }
            else if(line.startsWith("<totalCount>")) {
                totalCount = regexLine(line);
            }
            else if(line.startsWith("<entpName>")) {
                entpName = regexLine(line);
            }
            else if(line.startsWith("<itemName>")) {
                itemName = regexLine(line);
            }
            else if(line.startsWith("<itemSeq>")) {
                itemSeq = regexLine(line);
            }
            else if(line.startsWith("<efcyQesitm>")) {
                efcyQesitm = regexLine(line);
            }
            if(line.startsWith("<useMethodQesitm>")) {
                useMethodQesitm = regexLine(line);
            }
            else if(line.startsWith("<atpnWarnQesitm>")) {
                atpnWarnQesitm = regexLine(line);
            }
            else if(line.startsWith("<atpnQesitm>")) {
                atpnQesitm = regexLine(line);
            }
            else if(line.startsWith("<intrcQesitm>")) {
                intrcQesitm = regexLine(line);
            }
            else if(line.startsWith("<seQesitm>")) {
                seQesitm = regexLine(line);
            }
            else if(line.startsWith("<depositMethodQesitm>")) {
                depositMethodQesitm = regexLine(line);
            }
            else if(line.startsWith("<openDe>")) {
                openDe = regexLine(line);
            }
            else if(line.startsWith("<updateDe>")) {
                updateDe = regexLine(line);
            }
            else if(line.startsWith("<itemImage>")) {
                itemImage = regexLine(line);
            }
        }

        rd.close();
        conn.disconnect();

        return newMedicineDto.builder()
                .resultCode(resultCode)
                .resultMsg(resultMsg)
                .numOfRows(numOfRows)
                .pageNo(pageNo)
                .totalCount(totalCount)
                .entpName(entpName)
                .itemName(itemName)
                .itemSeq(itemSeq)
                .efcyQesitm(efcyQesitm)
                .useMethodQesitm(useMethodQesitm)
                .atpnWarnQesitm(atpnWarnQesitm)
                .atpnQesitm(atpnQesitm)
                .intrcQesitm(intrcQesitm)
                .seQesitm(seQesitm)
                .depositMethodQesitm(depositMethodQesitm)
                .openDe(openDe)
                .updateDe(updateDe)
                .itemImage(itemImage).build();
    }

    public String regexLine(String line) {
        List<String> htmlTags = new ArrayList<>(Arrays.asList(
                 "resultCode\", \"resultMsg\", \"numOfRows\", \"pageNo\",  \"totalCount\", \"entpName", "itemName",  "itemSeq"
                , "efcyQesitm", "useMethodQesitm", "atpnQesitm" , "intrcQesitm", "seQesitm", "depositMethodQesitm"
                , "openDe", "updateDe", "itemImage", "<", ">", "/", "&", ";", "gt", "sup", "lt", "sub", "p"));

        for(int i = 0; i < htmlTags.size(); i++) {
            line = line.replace(htmlTags.get(i), "");
        }

        return line;
    }

    public void getMedicineRequest() {

    }
}
