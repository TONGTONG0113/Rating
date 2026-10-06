package com.takima.backskeleton.integration;

import com.takima.backskeleton.DAO.AppUserDao;
import com.takima.backskeleton.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AppUserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserDao appUserDao;

    @BeforeEach
    void cleanDatabase() {

        appUserDao.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveUser()
            throws Exception {

        String requestBody = """
                {
                  "name": "Integration User",
                  "email": "integration@example.com"
                }
                """;

        mockMvc.perform(
                        post("/api/users")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        requestBody
                                )
                )
                .andExpect(
                        status().isCreated()
                )
                .andExpect(
                        jsonPath(
                                "$.id",
                                notNullValue()
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.name"
                        ).value(
                                "Integration User"
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.email"
                        ).value(
                                "integration@example.com"
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.active"
                        ).value(true)
                );

        AppUser user =
                appUserDao
                        .findAll()
                        .get(0);

        mockMvc.perform(
                        get(
                                "/api/users/{id}",
                                user.getId()
                        )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath(
                                "$.name"
                        ).value(
                                "Integration User"
                        )
                );
    }

    @Test
    void shouldDisableUser()
            throws Exception {

        AppUser user =
                new AppUser();

        user.setName(
                "Active User"
        );

        user.setEmail(
                "active@example.com"
        );

        user.setActive(true);

        user =
                appUserDao.save(user);

        mockMvc.perform(
                        patch(
                                "/api/users/{id}/status",
                                user.getId()
                        )
                                .param(
                                        "active",
                                        "false"
                                )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath(
                                "$.active"
                        ).value(false)
                );
    }

    @Test
    void shouldDeleteUser()
            throws Exception {

        AppUser user =
                new AppUser();

        user.setName(
                "Delete User"
        );

        user.setEmail(
                "delete@example.com"
        );

        user.setActive(true);

        user =
                appUserDao.save(user);

        Long id =
                user.getId();

        mockMvc.perform(
                        delete(
                                "/api/users/{id}",
                                id
                        )
                )
                .andExpect(
                        status().isNoContent()
                );

        assertFalse(
                appUserDao.existsById(id)
        );
    }
}