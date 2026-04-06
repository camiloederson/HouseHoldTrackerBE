package com.mikadev.householdtracker.auth;

import com.mikadev.householdtracker.auth.dto.LoginRequestDTO;
import com.mikadev.householdtracker.auth.dto.LoginResponseDTO;
import com.mikadev.householdtracker.auth.dto.RegisterRequestDTO;
import com.mikadev.householdtracker.exception.BadRequestException;
import com.mikadev.householdtracker.exception.ResourceNotFoundException;
import com.mikadev.householdtracker.household.HouseholdEntity;
import com.mikadev.householdtracker.household.HouseholdRepository;
import com.mikadev.householdtracker.role.RoleEntity;
import com.mikadev.householdtracker.role.RoleRepository;
import com.mikadev.householdtracker.security.JwtService;
import com.mikadev.householdtracker.user.UserEntity;
import com.mikadev.householdtracker.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HouseholdRepository householdRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       HouseholdRepository householdRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.householdRepository = householdRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Email is already in use");
        }

        HouseholdEntity householdEntity = householdRepository.findById(request.householdId())
                .orElseThrow(() -> new ResourceNotFoundException("Household not found with id: " + request.householdId()));

        RoleEntity roleEntity = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role ROLE_USER not found"));

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleEntity);

        UserEntity userEntity = UserEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .enabled(true)
                .household(householdEntity)
                .roles(roles)
                .build();

        UserEntity savedUser = userRepository.save(userEntity);

        String token = jwtService.generateToken(savedUser);

        return new LoginResponseDTO(
                token,
                "Bearer",
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail()
        );
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserEntity userEntity = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        String token = jwtService.generateToken(userEntity);

        return new LoginResponseDTO(
                token,
                "Bearer",
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail()
        );
    }
}