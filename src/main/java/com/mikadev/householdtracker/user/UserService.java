package com.mikadev.householdtracker.user;

import com.mikadev.householdtracker.exception.BadRequestException;
import com.mikadev.householdtracker.exception.ResourceNotFoundException;
import com.mikadev.householdtracker.household.HouseholdEntity;
import com.mikadev.householdtracker.household.HouseholdRepository;
import com.mikadev.householdtracker.role.RoleEntity;
import com.mikadev.householdtracker.role.RoleRepository;
import com.mikadev.householdtracker.user.dto.UserGetDTO;
import com.mikadev.householdtracker.user.dto.UserPostDTO;
import com.mikadev.householdtracker.user.dto.UserPutDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final HouseholdRepository householdRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       HouseholdRepository householdRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.householdRepository = householdRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public List<UserGetDTO> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserGetDTO> userGetDTOS = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            userGetDTOS.add(userMapper.toGetDTO(userEntity));
        }

        return userGetDTOS;
    }

    public UserGetDTO findById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return userMapper.toGetDTO(userEntity);
    }

    public UserGetDTO save(UserPostDTO userPostDTO) {
        if (userRepository.existsByEmail(userPostDTO.email())) {
            throw new BadRequestException("Email is already in use");
        }

        HouseholdEntity householdEntity = householdRepository.findById(userPostDTO.householdId())
                .orElseThrow(() -> new ResourceNotFoundException("Household not found with id: " + userPostDTO.householdId()));

        RoleEntity roleEntity = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role ROLE_USER not found"));

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleEntity);

        UserEntity userEntity = userMapper.toEntity(userPostDTO);
        userEntity.setPassword(passwordEncoder.encode(userPostDTO.password()));
        userEntity.setHousehold(householdEntity);
        userEntity.setRoles(roles);

        UserEntity savedUser = userRepository.save(userEntity);

        return userMapper.toGetDTO(savedUser);
    }

    public UserGetDTO update(Long id, UserPutDTO userPutDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!userEntity.getEmail().equals(userPutDTO.email()) && userRepository.existsByEmail(userPutDTO.email())) {
            throw new BadRequestException("Email is already in use");
        }

        HouseholdEntity householdEntity = householdRepository.findById(userPutDTO.householdId())
                .orElseThrow(() -> new ResourceNotFoundException("Household not found with id: " + userPutDTO.householdId()));

        userMapper.updateEntityFromPutDTO(userEntity, userPutDTO);
        userEntity.setHousehold(householdEntity);

        UserEntity updatedUser = userRepository.save(userEntity);

        return userMapper.toGetDTO(updatedUser);
    }

    public void delete(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(userEntity);
    }
}