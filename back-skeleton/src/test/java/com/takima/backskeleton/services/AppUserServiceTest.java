package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.AppUserDao;
import com.takima.backskeleton.DTO.AppUserDto;
import com.takima.backskeleton.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserDao appUserDao;

    private AppUserService appUserService;

    @BeforeEach
    void setUp() {

        appUserService =
                new AppUserService(
                        appUserDao
                );
    }

    @Test
    void shouldCreateUser() {

        AppUserDto input =
                new AppUserDto.AppUserDtoBuilder()
                        .name("Alice")
                        .email(
                                "alice@example.com"
                        )
                        .build();

        AppUser savedUser =
                createUser(
                        1L,
                        "Alice",
                        "alice@example.com",
                        true
                );

        when(
                appUserDao.existsByEmail(
                        "alice@example.com"
                )
        ).thenReturn(false);

        when(
                appUserDao.save(
                        any(AppUser.class)
                )
        ).thenReturn(savedUser);

        AppUserDto result =
                appUserService.create(
                        input
                );

        assertNotNull(result);

        assertEquals(
                1L,
                result.getId()
        );

        assertEquals(
                "Alice",
                result.getName()
        );

        assertEquals(
                "alice@example.com",
                result.getEmail()
        );

        assertTrue(
                result.getActive()
        );
    }

    @Test
    void shouldRejectDuplicateEmail() {

        AppUserDto input =
                new AppUserDto.AppUserDtoBuilder()
                        .name("Alice")
                        .email(
                                "alice@example.com"
                        )
                        .build();

        when(
                appUserDao.existsByEmail(
                        "alice@example.com"
                )
        ).thenReturn(true);

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                appUserService.create(
                                        input
                                )
                );

        assertEquals(
                409,
                exception
                        .getStatusCode()
                        .value()
        );

        verify(
                appUserDao,
                never()
        ).save(
                any(AppUser.class)
        );
    }

    @Test
    void shouldReturnUserById() {

        AppUser user =
                createUser(
                        1L,
                        "Alice",
                        "alice@example.com",
                        true
                );

        when(
                appUserDao.findById(1L)
        ).thenReturn(
                Optional.of(user)
        );

        AppUserDto result =
                appUserService
                        .findById(1L);

        assertEquals(
                "Alice",
                result.getName()
        );
    }

    @Test
    void shouldReturn404ForUnknownUser() {

        when(
                appUserDao.findById(
                        999L
                )
        ).thenReturn(
                Optional.empty()
        );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                appUserService
                                        .findById(
                                                999L
                                        )
                );

        assertEquals(
                404,
                exception
                        .getStatusCode()
                        .value()
        );
    }

    @Test
    void shouldDisableUser() {

        AppUser user =
                createUser(
                        1L,
                        "Alice",
                        "alice@example.com",
                        true
                );

        when(
                appUserDao.findById(1L)
        ).thenReturn(
                Optional.of(user)
        );

        when(
                appUserDao.save(
                        any(AppUser.class)
                )
        ).thenAnswer(
                invocation ->
                        invocation.getArgument(0)
        );

        AppUserDto result =
                appUserService
                        .updateStatus(
                                1L,
                                false
                        );

        assertFalse(
                result.getActive()
        );
    }

    @Test
    void shouldDeleteUser() {

        AppUser user =
                createUser(
                        1L,
                        "Alice",
                        "alice@example.com",
                        true
                );

        when(
                appUserDao.findById(1L)
        ).thenReturn(
                Optional.of(user)
        );

        appUserService.delete(1L);

        verify(appUserDao)
                .delete(user);
    }

    private AppUser createUser(
            Long id,
            String name,
            String email,
            boolean active
    ) {

        AppUser user =
                new AppUser();

        user.setId(id);

        user.setName(name);

        user.setEmail(email);

        user.setActive(active);

        user.setCreatedAt(
                LocalDateTime.now()
        );

        return user;
    }
}