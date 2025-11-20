package com.shreyas.ExpenseTracker.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CategoryDTO {
    private long id;
    private String name;
}
