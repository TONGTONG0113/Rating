package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.AppUserDao;
import com.takima.backskeleton.DTO.AppUserDto;
import com.takima.backskeleton.DTO.AppUserMapper;
import com.takima.backskeleton.models.AppUser;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserDao appUserDao;

    public AppUserService(AppUserDao appUserDao) {
        this.appUserDao = appUserDao;
    }

    public List<AppUserDto> findAll() {

        return appUserDao
                .findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(AppUserMapper::toDto)
                .toList();
    }

    public AppUserDto findById(Long id) {

        return AppUserMapper.toDto(
                getUserOrThrow(id)
        );
    }

    public AppUserDto create(AppUserDto dto) {

        validateUser(dto);

        if (appUserDao.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        AppUser appUser =
                AppUserMapper.fromDto(dto, null);

        appUser.setActive(true);

        AppUser savedUser =
                appUserDao.save(appUser);

        return AppUserMapper.toDto(savedUser);
    }

    public AppUserDto update(
            Long id,
            AppUserDto dto
    ) {

        AppUser existingUser =
                getUserOrThrow(id);

        validateUser(dto);

        if (
                appUserDao.existsByEmailAndIdNot(
                        dto.getEmail(),
                        id
                )
        ) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        existingUser.setName(dto.getName());
        existingUser.setEmail(dto.getEmail());

        AppUser savedUser =
                appUserDao.save(existingUser);

        return AppUserMapper.toDto(savedUser);
    }

    public AppUserDto updateStatus(
            Long id,
            boolean active
    ) {

        AppUser user =
                getUserOrThrow(id);

        user.setActive(active);

        AppUser savedUser =
                appUserDao.save(user);

        return AppUserMapper.toDto(savedUser);
    }

    public void delete(Long id) {

        AppUser appUser =
                getUserOrThrow(id);

        appUserDao.delete(appUser);
    }

    private AppUser getUserOrThrow(Long id) {

        return appUserDao
                .findById(id)
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "User not found"
                                )
                );
    }

    private void validateUser(AppUserDto dto) {

        if (
                dto.getName() == null ||
                        dto.getName().isBlank()
        ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Name is required"
            );
        }

        if (
                dto.getEmail() == null ||
                        dto.getEmail().isBlank()
        ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email is required"
            );
        }
    }
}