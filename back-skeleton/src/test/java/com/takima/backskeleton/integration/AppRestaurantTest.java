
package com.takima.backskeleton.integration;

import com.takima.backskeleton.services.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppRestaurantTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void shouldReturnAllRestaurants() throws Exception {
        when(restaurantService.getAllRestaurants())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(restaurantService).getAllRestaurants();
    }

    @Test
    void shouldReturnRestaurantRanking() throws Exception {
        when(restaurantService.getRestaurantRanking())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/restaurants/ranking"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(restaurantService).getRestaurantRanking();
    }
}
