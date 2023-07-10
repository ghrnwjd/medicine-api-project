package com.hoyoung.medicineapiproject.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDto {

    private String resultCode; // 결과코드
    private String resultMsg; // 결과메시지
    private String numOfRows; // 한 페이지 결과 수
    private String pageNo; // 페이지번호 --
    private String totalCount; // 전체 결과 수
    private String entpName; // 업체명 --
    private String itemName; // 제품명 --
    private String itemSeq; // 품목기준코드 --
    private String efcyQesitm; // 이 약의 효능은 무엇입니까? --
    private String useMethodQesitm; // 이 약은 어떻게 사용합니까?
    private String atpnWarnQesitm; // 이 약을 사용하기 전에 반드시 알아야 할 내용은 무엇입니까?
    private String atpnQesitm; // 이 약의 사용상 주의사항은 무엇입니까?
    private String intrcQesitm; // 이 약을 사용하는 동안 주의해야 할 약 또는 음식은 무엇입니까?
    private String seQesitm; // 이 약은 어떤 이상반응이 나타날 수 있습니까?
    private String depositMethodQesitm; // 이 약은 어떻게 보관해야 합니까?
    private String openDe; // 공개일자
    private String updateDe; // 수정일자
    private String itemImage;

    public MedicineDto(String efcyQesitm) {
        this.efcyQesitm = efcyQesitm;
    }
}
