package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.AppUserDto;
import com.takima.backskeleton.services.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(
            AppUserService appUserService
    ) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUserDto> getAllUsers() {
        return appUserService.findAll();
    }

    @GetMapping("/{id}")
    public AppUserDto getUserById(
            @PathVariable Long id
    ) {
        return appUserService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDto createUser(
            @RequestBody AppUserDto dto
    ) {
        return appUserService.create(dto);
    }

    @PutMapping("/{id}")
    public AppUserDto updateUser(
            @PathVariable Long id,
            @RequestBody AppUserDto dto
    ) {
        return appUserService.update(id, dto);
    }

    @PatchMapping("/{id}/status")
    public AppUserDto updateUserStatus(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {
        return appUserService.updateStatus(
                id,
                active
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @PathVariable Long id
    ) {
        appUserService.delete(id);
    }
}