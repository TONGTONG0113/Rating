
package com.takima.backskeleton.DTO;

public class RestaurantDto {

    private Long id;
    private String title;
    private String address;
    private String openingHours;
    private String imageUrl;

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

    public static final class RestaurantDtoBuilder {
        private Long id;
        private String title;
        private String address;
        private String openingHours;
        private String imageUrl;

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

        public RestaurantDto build() {
            RestaurantDto dto = new RestaurantDto();
            dto.id = this.id;
            dto.title = this.title;
            dto.address = this.address;
            dto.openingHours = this.openingHours;
            dto.imageUrl = this.imageUrl;
            return dto;
        }
    }
}
