package com.App.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.App.Repository.QueryRepository;
import com.App.dto.Query;

@Service
public class QueryService {
	
	@Autowired
	private QueryRepository queryRepository;
	
	public Query saveQuery(Query query) {
		return queryRepository.save(query);
	}

	public List<Query> getQueryList(){
		return queryRepository.findAll();
	}
	
	public Query getQueryById(int id) {
		return queryRepository.findById(id).get();
	}
	
	public List<Query> getQuerlistByUser(int id){
		return queryRepository.findByMyUserId(id);
	}
	public List<Query> getQueryListByEmp(int id){
		return queryRepository.findByEmpId(id);
	}
	
}
