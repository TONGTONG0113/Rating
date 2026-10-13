package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.AppUserDao;
import com.takima.backskeleton.DAO.RestaurantDao;
import com.takima.backskeleton.DAO.ReviewDao;
import com.takima.backskeleton.DTO.ReviewDto;
import com.takima.backskeleton.models.AppUser;
import com.takima.backskeleton.models.Restaurant;
import com.takima.backskeleton.models.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewDao reviewDao;

    @Mock
    private AppUserDao appUserDao;

    @Mock
    private RestaurantDao restaurantDao;

    @InjectMocks
    private ReviewService reviewService;

    private AppUser user;
    private Restaurant restaurant;
    private Review review;

    @BeforeEach
    void setUp() {
        user = new AppUser();
        user.setId(1L);
        user.setName("Alice");

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setTitle("Test Restaurant");

        review = new Review();
        review.setId(1L);
        review.setRating(5);
        review.setSummary("Excellent");
        review.setDetails("Very good food and service");
        review.setCreatedAt(LocalDateTime.now());
        review.setUser(user);
        review.setRestaurant(restaurant);
    }

    private ReviewDto validDto() {
        return ReviewDto.ReviewDtoBuilder.aReviewDto()
                .rating(5)
                .summary("Excellent")
                .details("Very good food and service")
                .userId(1L)
                .restaurantId(1L)
                .build();
    }

    private ResponseStatusException assertStatus(
            HttpStatus expectedStatus,
            Runnable action
    ) {
        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, action::run);

        assertEquals(expectedStatus, exception.getStatusCode());

        return exception;
    }

    @Test
    void getAllReviewsShouldReturnAllReviews() {
        when(reviewDao.findAll()).thenReturn(List.of(review));

        List<ReviewDto> result = reviewService.getAllReviews();

        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getRating());
        assertEquals("Excellent", result.get(0).getSummary());
        assertEquals(1L, result.get(0).getUserId());
        assertEquals(1L, result.get(0).getRestaurantId());

        verify(reviewDao).findAll();
    }

    @Test
    void getReviewByIdShouldReturnReview() {
        when(reviewDao.findById(1L)).thenReturn(Optional.of(review));

        ReviewDto result = reviewService.getReviewById(1L);

        assertEquals(1L, result.getId());
        assertEquals(5, result.getRating());
        assertEquals("Excellent", result.getSummary());
    }

    @Test
    void getReviewByIdShouldReturnNotFoundWhenReviewDoesNotExist() {
        when(reviewDao.findById(999L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertStatus(
                HttpStatus.NOT_FOUND,
                () -> reviewService.getReviewById(999L)
        );

        assertEquals("Review not found", exception.getReason());
    }

    @Test
    void createReviewShouldSaveReviewSuccessfully() {
        ReviewDto dto = validDto();

        when(appUserDao.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantDao.findById(1L))
                .thenReturn(Optional.of(restaurant));
        when(reviewDao.save(any(Review.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ReviewDto result = reviewService.createReview(dto);

        assertEquals(5, result.getRating());
        assertEquals("Excellent", result.getSummary());
        assertEquals(1L, result.getUserId());
        assertEquals(1L, result.getRestaurantId());
        assertNotNull(result.getCreatedAt());

        verify(reviewDao).save(any(Review.class));
    }

    @Test
    void createReviewShouldRejectRatingBelowOne() {
        ReviewDto dto = ReviewDto.ReviewDtoBuilder.aReviewDto()
                .rating(0)
                .summary("Bad rating")
                .userId(1L)
                .restaurantId(1L)
                .build();

        assertStatus(
                HttpStatus.BAD_REQUEST,
                () -> reviewService.createReview(dto)
        );

        verify(reviewDao, never()).save(any(Review.class));
    }

    @Test
    void createReviewShouldRejectRatingAboveFive() {
        ReviewDto dto = ReviewDto.ReviewDtoBuilder.aReviewDto()
                .rating(6)
                .summary("Bad rating")
                .userId(1L)
                .restaurantId(1L)
                .build();

        assertStatus(
                HttpStatus.BAD_REQUEST,
                () -> reviewService.createReview(dto)
        );

        verify(reviewDao, never()).save(any(Review.class));
    }

    @Test
    void createReviewShouldRejectBlankSummary() {
        ReviewDto dto = ReviewDto.ReviewDtoBuilder.aReviewDto()
                .rating(4)
                .summary("   ")
                .userId(1L)
                .restaurantId(1L)
                .build();

        assertStatus(
                HttpStatus.BAD_REQUEST,
                () -> reviewService.createReview(dto)
        );

        verify(reviewDao, never()).save(any(Review.class));
    }

    @Test
    void createReviewShouldReturnNotFoundWhenUserDoesNotExist() {
        when(appUserDao.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertStatus(
                HttpStatus.NOT_FOUND,
                () -> reviewService.createReview(validDto())
        );

        assertEquals("User not found", exception.getReason());

        verify(reviewDao, never()).save(any(Review.class));
    }

    @Test
    void createReviewShouldReturnNotFoundWhenRestaurantDoesNotExist() {
        when(appUserDao.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantDao.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertStatus(
                HttpStatus.NOT_FOUND,
                () -> reviewService.createReview(validDto())
        );

        assertEquals("Restaurant not found", exception.getReason());

        verify(reviewDao, never()).save(any(Review.class));
    }

    @Test
    void updateReviewShouldModifyReviewContent() {
        ReviewDto dto = ReviewDto.ReviewDtoBuilder.aReviewDto()
                .rating(4)
                .summary("Good")
                .details("Nice atmosphere")
                .build();

        when(reviewDao.findById(1L)).thenReturn(Optional.of(review));
        when(reviewDao.save(any(Review.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ReviewDto result = reviewService.updateReview(1L, dto);

        assertEquals(4, result.getRating());
        assertEquals("Good", result.getSummary());
        assertEquals("Nice atmosphere", result.getDetails());

        verify(reviewDao).save(review);
    }

    @Test
    void updateReviewShouldRejectInvalidRating() {
        ReviewDto dto = ReviewDto.ReviewDtoBuilder.aReviewDto()
                .rating(0)
                .summary("Invalid")
                .build();

        when(reviewDao.findById(1L)).thenReturn(Optional.of(review));

        assertStatus(
                HttpStatus.BAD_REQUEST,
                () -> reviewService.updateReview(1L, dto)
        );

        verify(reviewDao, never()).save(any(Review.class));
    }

    @Test
    void deleteReviewShouldDeleteExistingReview() {
        when(reviewDao.findById(1L)).thenReturn(Optional.of(review));

        reviewService.deleteReview(1L);

        verify(reviewDao).delete(review);
    }

    @Test
    void deleteReviewShouldReturnNotFoundWhenReviewDoesNotExist() {
        when(reviewDao.findById(999L)).thenReturn(Optional.empty());

        assertStatus(
                HttpStatus.NOT_FOUND,
                () -> reviewService.deleteReview(999L)
        );

        verify(reviewDao, never()).delete(any(Review.class));
    }

    @Test
    void getReviewsByRestaurantShouldReturnReviews() {
        when(restaurantDao.existsById(1L)).thenReturn(true);
        when(reviewDao.findByRestaurantIdOrderByCreatedAtDesc(1L))
                .thenReturn(List.of(review));

        List<ReviewDto> result =
                reviewService.getReviewsByRestaurant(1L);

        assertEquals(1, result.size());
        assertEquals("Excellent", result.get(0).getSummary());
    }

    @Test
    void getReviewsByRestaurantShouldReturnNotFoundWhenRestaurantDoesNotExist() {
        when(restaurantDao.existsById(999L)).thenReturn(false);

        assertStatus(
                HttpStatus.NOT_FOUND,
                () -> reviewService.getReviewsByRestaurant(999L)
        );

        verify(reviewDao, never())
                .findByRestaurantIdOrderByCreatedAtDesc(anyLong());
    }

    @Test
    void getReviewsByUserShouldReturnReviews() {
        when(appUserDao.existsById(1L)).thenReturn(true);
        when(reviewDao.findByUserIdOrderByCreatedAtDesc(1L))
                .thenReturn(List.of(review));

        List<ReviewDto> result = reviewService.getReviewsByUser(1L);

        assertEquals(1, result.size());
        assertEquals("Excellent", result.get(0).getSummary());
    }

    @Test
    void getReviewsByUserShouldReturnNotFoundWhenUserDoesNotExist() {
        when(appUserDao.existsById(999L)).thenReturn(false);

        assertStatus(
                HttpStatus.NOT_FOUND,
                () -> reviewService.getReviewsByUser(999L)
        );

        verify(reviewDao, never())
                .findByUserIdOrderByCreatedAtDesc(anyLong());
    }
}