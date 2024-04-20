package com.App.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.App.dto.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	public boolean existsByEmail(String email);
    public Admin findByEmail(String email);
}
