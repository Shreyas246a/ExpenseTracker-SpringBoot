package com.shreyas.ExpenseTracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isDefault; //

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public boolean getIsDefault() {
        return isDefault;
    }

}
