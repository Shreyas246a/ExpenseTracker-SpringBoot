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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "user_id"})
        }
)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isDefault; //


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = true)
    private User user;

    public boolean getIsDefault() {
        return isDefault;
    }

}
