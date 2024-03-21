package com.example.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    @Column(length = 5000000)
    private byte[] picByte;

    public ImageModel(String originalFilename, String contentType, byte[] imageBytes) {
        this.name = originalFilename;
        this.type = contentType;
        this.picByte = imageBytes;
    }
}
