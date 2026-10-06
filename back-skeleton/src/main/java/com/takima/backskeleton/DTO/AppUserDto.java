package com.takima.backskeleton.DTO;

import java.time.LocalDateTime;

public class AppUserDto {

    private Long id;
    private String name;
    private String email;
    private Boolean active;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static final class AppUserDtoBuilder {

        private Long id;
        private String name;
        private String email;
        private Boolean active;
        private LocalDateTime createdAt;

        public AppUserDtoBuilder() {
        }

        public AppUserDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AppUserDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AppUserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AppUserDtoBuilder active(Boolean active) {
            this.active = active;
            return this;
        }

        public AppUserDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AppUserDto build() {
            AppUserDto dto = new AppUserDto();

            dto.id = this.id;
            dto.name = this.name;
            dto.email = this.email;
            dto.active = this.active;
            dto.createdAt = this.createdAt;

            return dto;
        }
    }
}