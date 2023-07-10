package com.hoyoung.medicineapiproject.model;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookmarkId;

    @Column
    private BookmarkType bookmarkType1;

    @Column
    private BookmarkType bookmarkType2;

    @Column
    private BookmarkType bookmarkType3;

    @Column
    private BookmarkType bookmarkType4;

    @Column
    private BookmarkType bookmarkType5;

    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    private User user;

}
