package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.AppUser;

public class AppUserMapper {

    public static AppUser fromDto(AppUserDto dto, Long id) {

        AppUser appUser = new AppUser();

        appUser.setId(id);
        appUser.setName(dto.getName());
        appUser.setEmail(dto.getEmail());

        if (dto.getActive() != null) {
            appUser.setActive(dto.getActive());
        }

        return appUser;
    }

    public static AppUserDto toDto(AppUser appUser) {

        return new AppUserDto.AppUserDtoBuilder()
                .id(appUser.getId())
                .name(appUser.getName())
                .email(appUser.getEmail())
                .active(appUser.getActive())
                .createdAt(appUser.getCreatedAt())
                .build();
    }
}