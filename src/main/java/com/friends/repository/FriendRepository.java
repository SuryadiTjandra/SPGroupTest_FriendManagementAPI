package com.friends.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
	
	public String findUser(String email){
		try{
			return template.queryForObject(
					"SELECT email FROM users WHERE email = ?", 
					String.class, email);
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	}
	
	public List<String> findFriends(String email){
		return template.query(
				"SELECT user2 AS friend FROM friends WHERE user1 = ? "
				+ " UNION "
				+ " SELECT user1 AS friend FROM friends WHERE user2 = ? ",
				ps -> {
					ps.setString(1, email);
					ps.setString(2, email);	
				},
				(rs, index) -> rs.getString("friend")
		);
	}
	
	public List<String> findBlocked(String blocker){
		return template.query(
				"SELECT blockee FROM block WHERE blocker = ? ",
				ps -> {
					ps.setString(1, blocker);
				},
				(rs, index) -> rs.getString("blockee")
		);
	}
	
	public void makeFriends(String user1, String user2){
		template.update("INSERT INTO friends(user1,user2) VALUES(?,?)", user1, user2);
	}
}
