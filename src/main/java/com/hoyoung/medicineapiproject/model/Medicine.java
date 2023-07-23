package com.hoyoung.medicineapiproject.model;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MedicineNum;

    @Column
    private String entpName;

    @Column
    private String itemName;

    @Column
    private String itemSeq;

    @Column(length = 1500)
    private String efcyQesitm;

    @Column(length = 1500)
    private String useMethodQesitm;

    @Column(length = 1500)
    private String atpnWarnQesitm;

    @Column(length = 1500)
    private String atpnQesitm;

    @Column(length = 1500)
    private String intrcQesitm;

    @Column(length = 1500)
    private String seQesitm;

    @Column(length = 1500)
    private String depositMethodQesitm;




}
