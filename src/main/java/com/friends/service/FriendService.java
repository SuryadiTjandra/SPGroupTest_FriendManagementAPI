package com.friends.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friends.exceptions.AlreadyBlockedException;
import com.friends.exceptions.AlreadyFriendsException;
import com.friends.exceptions.AlreadySubscribedException;
import com.friends.exceptions.InvalidEmailAddressException;
import com.friends.exceptions.SameUserException;
import com.friends.exceptions.UserBlockedException;
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
		if (email == null)
			throw new WrongRequestFormatException("Must have field 'email'");
		checkEmail(email);
		
		return repo.findFriends(email);
	}
	
	public List<String> findCommonFriends(List<String> friends){
		if (friends == null || friends.size() != 2) 
			throw new WrongRequestFormatException("Must have field 'friends' with length of exactly 2");
		
		return findCommonFriends(friends.get(0), friends.get(1));
 	}
	
	private List<String> findCommonFriends(String email1, String email2){
		checkEmails(email1, email2);
		
		List<String> friendList1 = repo.findFriends(email1);
		List<String> friendList2 = repo.findFriends(email2);
		List<String> commonFriends = friendList1.stream()
										.filter(friend -> friendList2.contains(friend))
										.collect(Collectors.toList());
		return commonFriends;
 	}
	
	public void makeFriends(List<String> friends){
		if (friends == null || friends.size() != 2) 
			throw new WrongRequestFormatException("Must have field 'friends' with length of exactly 2");
		
		makeFriends(friends.get(0), friends.get(1));
	}
	
	private void makeFriends(String email1, String email2) {
		checkEmails(email1, email2);
		
		List<String> email1Friends = repo.findFriends(email1);
		if (email1Friends.contains(email2))
			throw new AlreadyFriendsException(email1, email2);
		
		List<String> email1Blockers = repo.findBlockers(email1);
		if (email1Blockers.contains(email2))
			throw new UserBlockedException(email2, email1);
		
		List<String> email2Blockers = repo.findBlockers(email2);
		if (email2Blockers.contains(email1))
			throw new UserBlockedException(email1, email2);
		
		repo.makeFriends(email1, email2);
	}

	public void subscribe(String requestor, String target){
		if (requestor == null || target == null)
			throw new WrongRequestFormatException("Must have fields 'requestor' and 'target'");
		
		checkEmails(requestor, target);
		
		List<String> targetSubscribers = repo.findSubscribers(target);
		if (targetSubscribers.contains(requestor))
			throw new AlreadySubscribedException(requestor, target);
		
		repo.subscribe(requestor, target);
	}
	
	public void block(String requestor, String target){
		if (requestor == null || target == null)
			throw new WrongRequestFormatException("Must have fields 'requestor' and 'target'");
		
		checkEmails(requestor, target);
		
		List<String> targetBlockers = repo.findBlockers(target);
		if (targetBlockers.contains(requestor))
			throw new AlreadyBlockedException(requestor, target);
		
		repo.block(requestor, target);
	}
	
	private void checkEmails(String email1, String email2){
		checkEmail(email1);
		checkEmail(email2);
		if (email1.contentEquals(email2))
			throw new SameUserException("The two users must not be the same.");
	}
	
	private void checkEmail(String email){
		if (!email.contains("@") || email.contains(" "))
			throw new InvalidEmailAddressException(email);
		
		String[] splitEmail = email.split("@", 2);
		if (splitEmail[0] == null || splitEmail[1] == null || splitEmail[0].isEmpty() || splitEmail[1].isEmpty()){
			throw new InvalidEmailAddressException(email);
		}
		
		if (repo.findUser(email) == null)
			throw new UserNotFoundException(email);
	}
}
