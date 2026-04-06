package com.mikadev.householdtracker.household;

import com.mikadev.householdtracker.household.dto.HouseholdGetDTO;
import com.mikadev.householdtracker.household.dto.HouseholdPostDTO;
import com.mikadev.householdtracker.household.dto.HouseholdPutDTO;
import com.mikadev.householdtracker.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseholdService {

    private final HouseholdRepository householdRepository;
    private final HouseholdMapper householdMapper;

    public HouseholdService(HouseholdRepository householdRepository, HouseholdMapper householdMapper) {
        this.householdRepository = householdRepository;
        this.householdMapper = householdMapper;
    }

    public List<HouseholdGetDTO> findAll() {
        List<HouseholdEntity> householdEntities = householdRepository.findAll();
        List<HouseholdGetDTO> householdGetDTOS = new ArrayList<>();

        for (HouseholdEntity householdEntity : householdEntities) {
            householdGetDTOS.add(householdMapper.toGetDTO(householdEntity));
        }

        return householdGetDTOS;
    }

    public HouseholdGetDTO findById(Long id) {
        HouseholdEntity householdEntity = householdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Household not found with id: " + id));

        return householdMapper.toGetDTO(householdEntity);
    }

    public HouseholdGetDTO save(HouseholdPostDTO householdPostDTO) {
        HouseholdEntity householdEntity = householdMapper.toEntity(householdPostDTO);
        HouseholdEntity savedHousehold = householdRepository.save(householdEntity);

        return householdMapper.toGetDTO(savedHousehold);
    }

    public HouseholdGetDTO update(Long id, HouseholdPutDTO householdPutDTO) {
        HouseholdEntity householdEntity = householdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Household not found with id: " + id));

        householdMapper.updateEntityFromPutDTO(householdEntity, householdPutDTO);

        HouseholdEntity updatedHousehold = householdRepository.save(householdEntity);

        return householdMapper.toGetDTO(updatedHousehold);
    }

    public void delete(Long id) {
        HouseholdEntity householdEntity = householdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Household not found with id: " + id));

        householdRepository.delete(householdEntity);
    }
}