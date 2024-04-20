package com.App.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.App.dto.Employee;
import com.App.dto.Query;
@Repository
public interface QueryRepository extends JpaRepository<Query, Integer> {

	public List<Query> findByMyUserId(int my_user_id);
	public List<Query> findByEmpId(int emp_id);

	
}
