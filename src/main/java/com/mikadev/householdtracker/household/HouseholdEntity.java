package com.mikadev.householdtracker.household;

import com.mikadev.householdtracker.monthlybudget.MonthlyBudgetEntity;
import com.mikadev.householdtracker.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "households")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseholdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "household_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @OneToMany(mappedBy = "household")
    @Builder.Default
    private Set<UserEntity> users = new HashSet<>();

    @OneToMany(mappedBy = "household")
    @Builder.Default
    private Set<MonthlyBudgetEntity> monthlyBudgets = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseholdEntity householdEntity)) return false;
        return Objects.equals(id, householdEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}