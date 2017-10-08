package com.friends;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class FriendManagementAPI implements CommandLineRunner {
	
	public static void main(String[] args){
		SpringApplication.run(FriendManagementAPI.class, args);
	}

	@Autowired
	private JdbcTemplate template;
	@Override
	public void run(String... args) throws Exception {
		//initializing database
		//create tables
		template.execute("CREATE TABLE users( email VARCHAR(255))");
		template.execute("CREATE TABLE friends("
				+ "user1 VARCHAR(255), "
				+ "user2 VARCHAR(255),"
				+ "FOREIGN KEY (user1) REFERENCES users(email),"
				+ "FOREIGN KEY (user2) REFERENCES users(email))");
		template.execute("CREATE TABLE subscription("
				+ "subscribee VARCHAR(255), "
				+ "subscriber VARCHAR(255),"
				+ "FOREIGN KEY (subscriber) REFERENCES users(email),"
				+ "FOREIGN KEY (subscribee) REFERENCES users(email))");
		template.execute("CREATE TABLE block("
				+ "blocker VARCHAR(255), "
				+ "blockee VARCHAR(255),"
				+ "FOREIGN KEY (blocker) REFERENCES users(email),"
				+ "FOREIGN KEY (blockee) REFERENCES users(email))");
		
		//insert data into tables
		List<Object[]> users = Arrays.asList(
				"alice@a.com", "bob@b.com", "carol@c.com", "dennis@d.com", "eve@e.com",
				"frank@f.com", "grace@g.com", "harold@h.com", "ingrid@i.com", "jennifer@j.com"
				).stream().map(email -> new Object[]{email}).collect(Collectors.toList());
		template.batchUpdate("INSERT INTO users(email) VALUES(?)", users);
		
		List<Object[]> friends = Arrays.asList(
				new String[]{"alice@a.com", "bob@b.com"},
				new String[]{"bob@a.com", "carol@c.com"},
				new String[]{"dennis@d.com", "alice@a.com"},
				new String[]{"dennis@d.com", "bob@b.com"},
				new String[]{"dennis@d.com", "carol@c.com"},
				new String[]{"eve@e.com", "dennis@d.com"}
		);
		template.batchUpdate("INSERT INTO friends(user1,user2) VALUES(?,?)", friends);
		
		List<Object[]> subscriptions = Arrays.asList(
				new String[]{"eve@e.com", "alice@a.com"},
				new String[]{"eve@e.com", "bob@b.com"},
				new String[]{"eve@e.com", "carol@c.com"}
		);
		template.batchUpdate("INSERT INTO subscription(subscriber,subscribee) VALUES(?,?)", subscriptions);
		
		List<Object[]> blocks = Arrays.asList(
				new String[]{"alice@a.com", "carol@c.com"},
				new String[]{"bob@b.com", "eve@e.com"}
		);
		template.batchUpdate("INSERT INTO block(blocker,blockee) VALUES(?,?)", blocks);
		
	}
}
