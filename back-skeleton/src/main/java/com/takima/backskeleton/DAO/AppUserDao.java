package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserDao extends JpaRepository<AppUser, Long> {
}