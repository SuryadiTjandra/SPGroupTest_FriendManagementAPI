package com.friends.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FriendRepository {
	@Autowired
	JdbcTemplate template;
	
	public List<String> findAllUsers(){
		return template.query(
				"SELECT email FROM users", 
				(rs, index) -> rs.getString("email")
		);
	}
	
}
