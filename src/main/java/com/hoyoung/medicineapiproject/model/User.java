package com.hoyoung.medicineapiproject.model;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userPassword;

    @OneToMany(mappedBy="user")
    private List<Bookmark> bookmarkList;

}
