package com.takima.backskeleton.DTO;

public class AppUserDto {

    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public static final class AppUserDtoBuilder {
        private String name;
        private String email;

        public AppUserDtoBuilder() {
        }

        public static AppUserDtoBuilder anAppUserDto() {
            return new AppUserDtoBuilder();
        }

        public AppUserDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AppUserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AppUserDto build() {
            AppUserDto appUserDto = new AppUserDto();
            appUserDto.name = this.name;
            appUserDto.email = this.email;
            return appUserDto;
        }
    }
}