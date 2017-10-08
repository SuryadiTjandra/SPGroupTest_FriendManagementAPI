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
	
	public List<String> findFriends(String email){
		return template.query(
				"SELECT user1 AS friend FROM friends WHERE user2 = ? "
				+ " UNION "
				+ " SELECT user2 AS friend FROM friends WHERE user1 = ? ",
				ps -> {
					ps.setString(1, email);
					ps.setString(2, email);	
				},
				(rs, index) -> rs.getString("friend")
		);
	}
}
