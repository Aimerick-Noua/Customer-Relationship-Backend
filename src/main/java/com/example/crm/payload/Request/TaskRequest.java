package com.example.crm.payload.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TaskRequest {
    private String description;
    private LocalDate date_limit;
    private String responsible;
}
