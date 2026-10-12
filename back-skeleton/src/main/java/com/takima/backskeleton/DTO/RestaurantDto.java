package com.takima.backskeleton.DTO;

public class RestaurantDto {

    private Long id;
    private String title;
    private String address;
    private String openingHours;
    private String imageUrl;

    private String cuisine;

    // These values are derived from reviews and are not stored
    // directly in the restaurants table.
    private Double averageRating;
    private Long reviewCount;

    public RestaurantDto() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCuisine() {
        return cuisine;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Long getReviewCount() {
        return reviewCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public static final class RestaurantDtoBuilder {

        private Long id;
        private String title;
        private String address;
        private String openingHours;
        private String imageUrl;
        private String cuisine;
        private Double averageRating;
        private Long reviewCount;

        public RestaurantDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RestaurantDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public RestaurantDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        public RestaurantDtoBuilder openingHours(String openingHours) {
            this.openingHours = openingHours;
            return this;
        }

        public RestaurantDtoBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public RestaurantDtoBuilder cuisine(String cuisine) {
            this.cuisine = cuisine;
            return this;
        }

        public RestaurantDtoBuilder averageRating(Double averageRating) {
            this.averageRating = averageRating;
            return this;
        }

        public RestaurantDtoBuilder reviewCount(Long reviewCount) {
            this.reviewCount = reviewCount;
            return this;
        }

        public RestaurantDto build() {
            RestaurantDto dto = new RestaurantDto();

            dto.id = this.id;
            dto.title = this.title;
            dto.address = this.address;
            dto.openingHours = this.openingHours;
            dto.imageUrl = this.imageUrl;
            dto.cuisine = this.cuisine;
            dto.averageRating = this.averageRating;
            dto.reviewCount = this.reviewCount;

            return dto;
        }
    }
}