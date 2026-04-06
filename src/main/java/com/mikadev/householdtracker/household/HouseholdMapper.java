package com.mikadev.householdtracker.household;

import com.mikadev.householdtracker.household.dto.HouseholdGetDTO;
import com.mikadev.householdtracker.household.dto.HouseholdPostDTO;
import com.mikadev.householdtracker.household.dto.HouseholdPutDTO;
import org.springframework.stereotype.Component;

@Component
public class HouseholdMapper {

    public HouseholdGetDTO toGetDTO(HouseholdEntity householdEntity) {
        return new HouseholdGetDTO(
                householdEntity.getId(),
                householdEntity.getName(),
                householdEntity.getCreatedAt(),
                householdEntity.getUpdatedAt()
        );
    }

    public HouseholdEntity toEntity(HouseholdPostDTO householdPostDTO) {
        return HouseholdEntity.builder()
                .name(householdPostDTO.name())
                .build();
    }

    public void updateEntityFromPutDTO(HouseholdEntity householdEntity, HouseholdPutDTO householdPutDTO) {
        householdEntity.setName(householdPutDTO.name());
    }
}