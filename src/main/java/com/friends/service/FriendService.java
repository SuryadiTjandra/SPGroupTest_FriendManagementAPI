package com.friends.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friends.repository.FriendRepository;
import com.friends.responses.ErrorResponse;
import com.friends.responses.FriendListResponse;
import com.friends.responses.Response;

@Service
public class FriendService {
	@Autowired
	private FriendRepository repo;
	
	public Response findAllUsers(){
		return new FriendListResponse(repo.findAllUsers());
	}
	
	public Response findFriends(String email){
		return new FriendListResponse(repo.findFriends(email));
	}
	
	public Response findCommonFriends(List<String> emailList){
		if (emailList.size() != 2) 
			return new ErrorResponse("Wrong request format. 'friends' must be 2 in length.");
		
		return findCommonFriends(emailList.get(0), emailList.get(1));
 	}
	
	private Response findCommonFriends(String email1, String email2){
		List<String> friendList1 = repo.findFriends(email1);
		List<String> friendList2 = repo.findFriends(email2);
		List<String> commonFriends = friendList1.stream()
										.filter(friend -> friendList2.contains(friend))
										.collect(Collectors.toList());
		return new FriendListResponse(commonFriends);
 	}
}
