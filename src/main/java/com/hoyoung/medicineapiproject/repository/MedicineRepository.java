package com.hoyoung.medicineapiproject.repository;

import com.hoyoung.medicineapiproject.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
}