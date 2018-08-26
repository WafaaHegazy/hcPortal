package com.sirtts.service.mapper;

import com.sirtts.domain.Authority;
import com.sirtts.domain.MyDoctor;
import com.sirtts.domain.User;
import com.sirtts.service.dto.UserDTO;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            user.setBirthdate(userDTO.getBirthdate());
            user.setEthnicity(userDTO.getEthnicity());
            user.setMaritalStatus(userDTO.getMaritalStatus());
            user.setIsDoctor(userDTO.getDoctor());
            Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            if (authorities != null) {
                user.setAuthorities(authorities);
            }

            Set<String> patients = userDTO.getPatients();
            if (patients != null) {
                user.setPatients(patients);
            }

            Set<String> doctors = userDTO.getDoctors();
            if (doctors != null) {
                user.setDoctors(doctors);
            }
            return user;
        }
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public User userFromId(String id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    public Set<Authority> authoritiesFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            Authority auth = new Authority();
            auth.setName(string);
            return auth;
        }).collect(Collectors.toSet());
    }

    public Set<User> patientsFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            User user = new User();
            user.setLogin(string);
            return user;
        }).collect(Collectors.toSet());
    }

    public Set<User> doctorsFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            User doctor = new User();
            doctor.setLogin(string);
            return doctor;
        }).collect(Collectors.toSet());
    }
}
