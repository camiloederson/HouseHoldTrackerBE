package com.mikadev.householdtracker.user;

import com.mikadev.householdtracker.user.dto.UserGetDTO;
import com.mikadev.householdtracker.user.dto.UserPostDTO;
import com.mikadev.householdtracker.user.dto.UserPutDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserGetDTO toGetDTO(UserEntity userEntity) {
        return new UserGetDTO(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getEnabled(),
                userEntity.getHousehold().getId(),
                userEntity.getHousehold().getName(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }

    public UserEntity toEntity(UserPostDTO userPostDTO) {
        return UserEntity.builder()
                .firstName(userPostDTO.firstName())
                .lastName(userPostDTO.lastName())
                .email(userPostDTO.email())
                .password(userPostDTO.password())
                .enabled(userPostDTO.enabled())
                .build();
    }

    public void updateEntityFromPutDTO(UserEntity userEntity, UserPutDTO userPutDTO) {
        userEntity.setFirstName(userPutDTO.firstName());
        userEntity.setLastName(userPutDTO.lastName());
        userEntity.setEmail(userPutDTO.email());
        userEntity.setEnabled(userPutDTO.enabled());
    }
}