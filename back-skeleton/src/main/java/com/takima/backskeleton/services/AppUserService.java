package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.AppUserDao;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserDao appUserDao;

    public AppUserService(AppUserDao appUserDao) {
        this.appUserDao = appUserDao;
    }
}