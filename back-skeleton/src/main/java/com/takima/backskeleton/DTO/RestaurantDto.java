package com.takima.backskeleton.DTO;

public class RestaurantDto {

    private String title;
    private String address;
    private String openingHours;

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public static final class RestaurantDtoBuilder {
        private String title;
        private String address;
        private String openingHours;

        public RestaurantDtoBuilder() {
        }

        public static RestaurantDtoBuilder aRestaurantDto() {
            return new RestaurantDtoBuilder();
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

        public RestaurantDto build() {
            RestaurantDto restaurantDto = new RestaurantDto();
            restaurantDto.title = this.title;
            restaurantDto.address = this.address;
            restaurantDto.openingHours = this.openingHours;
            return restaurantDto;
        }
    }
}