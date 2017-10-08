package com.friends.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friends.exceptions.InvalidEmailAddressException;
import com.friends.exceptions.UserNotFoundException;
import com.friends.exceptions.WrongRequestFormatException;
import com.friends.repository.FriendRepository;

@Service
public class FriendService {
	@Autowired
	private FriendRepository repo;
	
	public List<String> findAllUsers(){
		return repo.findAllUsers();
	}
	
	public List<String> findFriends(String email) {
		if (email == null || email.trim().isEmpty())
			throw new WrongRequestFormatException("Must have field 'email' which is not empty");
		checkEmail(email);
		
		return repo.findFriends(email);
	}
	
	public List<String> findCommonFriends(List<String> emailList){
		if (emailList.size() != 2) 
			throw new WrongRequestFormatException("Must have field 'friends' with length of exactly 2");
		
		return findCommonFriends(emailList.get(0), emailList.get(1));
 	}
	
	private List<String> findCommonFriends(String email1, String email2){
		checkEmail(email1);
		checkEmail(email2);
		
		List<String> friendList1 = repo.findFriends(email1);
		List<String> friendList2 = repo.findFriends(email2);
		List<String> commonFriends = friendList1.stream()
										.filter(friend -> friendList2.contains(friend))
										.collect(Collectors.toList());
		return commonFriends;
 	}
	
	private void checkEmail(String email){
		if (!email.contains("@") || email.contains(" "))
			throw new InvalidEmailAddressException(email);
		
		String[] splitEmail = email.split("@", 1);
		if (splitEmail[0] == null || splitEmail[1] == null || splitEmail[0].isEmpty() || splitEmail[1].isEmpty()){
			throw new InvalidEmailAddressException(email);
		}
		
		if (repo.findUser(email) == null)
			throw new UserNotFoundException(email);
	}
}
