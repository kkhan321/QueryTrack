package com.App.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.App.dto.MyUser;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
public boolean existsByEmail(String email);
public MyUser findByEmail(String email);
}
