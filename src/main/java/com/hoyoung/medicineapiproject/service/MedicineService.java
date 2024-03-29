package com.hoyoung.medicineapiproject.service;

import com.hoyoung.medicineapiproject.component.ApiKey;
import com.hoyoung.medicineapiproject.dto.MedicineDto;
import com.hoyoung.medicineapiproject.dto.ResponseDto;
import com.hoyoung.medicineapiproject.dto.ResponseStatus;
import com.hoyoung.medicineapiproject.model.Medicine;
import com.hoyoung.medicineapiproject.repository.MedicineRepository;
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

    private static final String PATH = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList";
    private ApiKey apiKey;

    private final MedicineRepository medicineRepository;

    public MedicineService(ApiKey apiKey, MedicineRepository medicineRepository) {
        this.apiKey = apiKey;
        this.medicineRepository = medicineRepository;
    }


    public ResponseDto getMedicineResponseString(MedicineDto medicineDto) throws IOException{
        URL url = urlBuilder(medicineDto);
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
        URL url = urlBuilder(medicineDto);
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


    public URL urlBuilder(MedicineDto medicineDto) throws IOException {

        StringBuilder urlBuilder = new StringBuilder(PATH); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + apiKey.getMedicineEncodingKey()); /*Service Key*/
        if (medicineDto.getPageNo() != null) {
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(medicineDto.getPageNo(), "UTF-8")); /*페이지번호*/
        }
        else  {
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        }
        if (medicineDto.getNumOfRows() != null) {
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(medicineDto.getNumOfRows(), "UTF-8")); /*한 페이지 결과 수*/
        }
        else {
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
        }
        if (medicineDto.getEntpName() != null) {
            urlBuilder.append("&" + URLEncoder.encode("entpName","UTF-8") + "=" + URLEncoder.encode(medicineDto.getEntpName(), "UTF-8")); /*업체명*/
        }
        if (medicineDto.getItemName() != null) {
            urlBuilder.append("&" + URLEncoder.encode("itemName","UTF-8") + "=" + URLEncoder.encode(medicineDto.getItemName(), "UTF-8")); /*제품명*/
        }
        if (medicineDto.getItemSeq() != null) {
            urlBuilder.append("&" + URLEncoder.encode("itemSeq","UTF-8") + "=" + URLEncoder.encode(medicineDto.getItemSeq(), "UTF-8")); /*품목기준코드*/
        }
        if (medicineDto.getEfcyQesitm() != null) {
            urlBuilder.append("&" + URLEncoder.encode("efcyQesitm","UTF-8") + "=" + URLEncoder.encode(medicineDto.getEfcyQesitm(), "UTF-8")); /*이 약의 효능은 무엇입니까?*/
        }
        if (medicineDto.getUseMethodQesitm() != null) {
            urlBuilder.append("&" + URLEncoder.encode("useMethodQesitm","UTF-8") + "=" + URLEncoder.encode(medicineDto.getUseMethodQesitm(), "UTF-8")); /*이 약은 어떻게 사용합니까?*/
        }
        if (medicineDto.getAtpnWarnQesitm() != null) {
            urlBuilder.append("&" + URLEncoder.encode("atpnWarnQesitm","UTF-8") + "=" + URLEncoder.encode(medicineDto.getAtpnWarnQesitm(), "UTF-8")); /*이 약을 사용하기 전에 반드시 알아야 할 내용은 무엇입니까?*/
        }
        if (medicineDto.getAtpnQesitm() != null) {
            urlBuilder.append("&" + URLEncoder.encode("atpnQesitm","UTF-8") + "=" + URLEncoder.encode(medicineDto.getAtpnQesitm(), "UTF-8")); /*이 약의 사용상 주의사항은 무엇입니까?*/
        }
        if (medicineDto.getIntrcQesitm() != null) {
            urlBuilder.append("&" + URLEncoder.encode("intrcQesitm","UTF-8") + "=" + URLEncoder.encode(medicineDto.getIntrcQesitm(), "UTF-8")); /*이 약을 사용하는 동안 주의해야 할 약 또는 음식은 무엇입니까?*/
        }
        if (medicineDto.getSeQesitm() != null) {
            urlBuilder.append("&" + URLEncoder.encode("seQesitm","UTF-8") + "=" + URLEncoder.encode(medicineDto.getSeQesitm(), "UTF-8")); /*이 약은 어떤 이상반응이 나타날 수 있습니까?*/
        }
        if (medicineDto.getDepositMethodQesitm() != null) {
            urlBuilder.append("&" + URLEncoder.encode("depositMethodQesitm","UTF-8") + "=" + URLEncoder.encode(medicineDto.getDepositMethodQesitm(), "UTF-8")); /*이 약은 어떻게 보관해야 합니까?*/
        }
        if (medicineDto.getOpenDe() != null) {
            urlBuilder.append("&" + URLEncoder.encode("openDe","UTF-8") + "=" + URLEncoder.encode(medicineDto.getOpenDe(), "UTF-8")); /*공개일자*/
        }
        if (medicineDto.getUpdateDe() != null) {
            urlBuilder.append("&" + URLEncoder.encode("updateDe","UTF-8") + "=" + URLEncoder.encode(medicineDto.getUpdateDe(), "UTF-8")); /*수정일자*/
        }

        return new URL(urlBuilder.toString());
    }
    public MedicineDto recommendMedicineByInput(String medicineName) throws IOException {

        return getMedicineResponse(MedicineDto.builder().itemName(medicineName).build());
    }

    public void saveMedicine() throws IOException {
        BufferedReader rd = null;
        HttpURLConnection conn = null;
        for(int pageNo = 1; pageNo <= 500; pageNo++) {
            StringBuilder urlBuilder = new StringBuilder(PATH); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + apiKey.getMedicineEncodingKey()); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNo), "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/

            URL url = new URL(urlBuilder.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());

            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String line;

            String resultCode = null, resultMsg = null, numOfRows = null,tempPageNo = null, totalCount = null, entpName = null, itemName = null,
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
                    tempPageNo = regexLine(line);
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

            medicineRepository.save(Medicine.builder()
                            .itemName(itemName)
                            .itemSeq(itemSeq)
                            .entpName(entpName)
                            .atpnWarnQesitm(atpnWarnQesitm)
                            .atpnQesitm(atpnQesitm)
                            .seQesitm(seQesitm)
                            .intrcQesitm(intrcQesitm)
                            .depositMethodQesitm(depositMethodQesitm)
                            .build());

            System.out.println("저장 : " + pageNo);

        }

        rd.close();
        conn.disconnect();
        rd.close();
        conn.disconnect();



    }

}
