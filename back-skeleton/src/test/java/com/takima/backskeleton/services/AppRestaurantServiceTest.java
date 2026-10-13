
package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.RestaurantDao;
import com.takima.backskeleton.DAO.ReviewDao;
import com.takima.backskeleton.DTO.RestaurantDto;
import com.takima.backskeleton.models.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppRestaurantServiceTest {

    @Mock
    private RestaurantDao restaurantDao;

    @Mock
    private ReviewDao reviewDao;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setTitle("Italian Restaurant");
        restaurant.setAddress("Paris");
        restaurant.setOpeningHours("09:00-22:00");
        restaurant.setImageUrl("https://example.com/image.jpg");
        restaurant.setCuisine("Italian");
    }

    @Test
    void shouldReturnAllRestaurants() {
        when(restaurantDao.findAll())
                .thenReturn(List.of(restaurant));

        when(reviewDao.findByRestaurantIdOrderByCreatedAtDesc(1L))
                .thenReturn(List.of());

        List<RestaurantDto> result =
                restaurantService.getAllRestaurants();

        assertEquals(1, result.size());
        assertEquals("Italian Restaurant", result.get(0).getTitle());
        assertEquals("Paris", result.get(0).getAddress());

        verify(restaurantDao).findAll();
    }

    @Test
    void shouldReturnRestaurantById() {
        when(restaurantDao.findById(1L))
                .thenReturn(Optional.of(restaurant));

        when(reviewDao.findByRestaurantIdOrderByCreatedAtDesc(1L))
                .thenReturn(List.of());

        RestaurantDto result =
                restaurantService.getRestaurantById(1L);

        assertNotNull(result);
        assertEquals("Italian Restaurant", result.getTitle());
        assertEquals("Paris", result.getAddress());
        assertEquals("Italian", result.getCuisine());
    }

    @Test
    void shouldThrowNotFoundWhenRestaurantDoesNotExist() {
        when(restaurantDao.findById(999L))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> restaurantService.getRestaurantById(999L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void shouldCreateRestaurant() {
        RestaurantDto dto = new RestaurantDto();
        dto.setTitle("New Restaurant");
        dto.setAddress("Paris");
        dto.setOpeningHours("10:00-22:00");
        dto.setImageUrl("https://example.com/new.jpg");
        dto.setCuisine("French");

        when(restaurantDao.save(any(Restaurant.class)))
                .thenAnswer(invocation -> {
                    Restaurant saved = invocation.getArgument(0);
                    saved.setId(2L);
                    return saved;
                });

        when(reviewDao.findByRestaurantIdOrderByCreatedAtDesc(2L))
                .thenReturn(List.of());

        RestaurantDto result = restaurantService.createRestaurant(dto);

        assertNotNull(result);
        assertEquals("New Restaurant", result.getTitle());
        assertEquals("French", result.getCuisine());

        verify(restaurantDao).save(any(Restaurant.class));
    }

    @Test
    void shouldRejectRestaurantWithoutTitle() {
        RestaurantDto dto = new RestaurantDto();
        dto.setTitle("   ");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> restaurantService.createRestaurant(dto)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(restaurantDao, never()).save(any(Restaurant.class));
    }

    @Test
    void shouldUpdateRestaurant() {
        RestaurantDto dto = new RestaurantDto();
        dto.setTitle("Updated Restaurant");
        dto.setAddress("Lyon");
        dto.setOpeningHours("11:00-23:00");
        dto.setImageUrl("https://example.com/updated.jpg");
        dto.setCuisine("French");

        when(restaurantDao.findById(1L))
                .thenReturn(Optional.of(restaurant));

        when(restaurantDao.save(any(Restaurant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(reviewDao.findByRestaurantIdOrderByCreatedAtDesc(1L))
                .thenReturn(List.of());

        RestaurantDto result =
                restaurantService.updateRestaurant(1L, dto);

        assertEquals("Updated Restaurant", result.getTitle());
        assertEquals("Lyon", result.getAddress());
        assertEquals("French", result.getCuisine());

        verify(restaurantDao).save(restaurant);
    }

    @Test
    void shouldDeleteRestaurant() {
        when(restaurantDao.findById(1L))
                .thenReturn(Optional.of(restaurant));

        restaurantService.deleteRestaurant(1L);

        verify(restaurantDao).delete(restaurant);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingRestaurant() {
        when(restaurantDao.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> restaurantService.deleteRestaurant(999L)
        );

        verify(restaurantDao, never()).delete(any(Restaurant.class));
    }
}
