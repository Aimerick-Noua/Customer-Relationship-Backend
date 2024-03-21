package com.example.crm.repository;

import com.example.crm.model.ImageModel;
import com.example.crm.service.ImageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel,Long> {
}
