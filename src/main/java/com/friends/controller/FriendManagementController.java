package com.friends.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.friends.repository.FriendRepository;
import com.friends.requests.EmailRequest;
import com.friends.requests.FriendsRequest;
import com.friends.requests.TargetRequest;
import com.friends.requests.UpdateRequest;
import com.friends.responses.ErrorResponse;
import com.friends.responses.FriendListResponse;
import com.friends.responses.RecipientListResponse;
import com.friends.responses.Response;

@RestController
@RequestMapping("/friend/*")
public class FriendManagementController {
	
	@Autowired
	private FriendRepository repo;
	@RequestMapping("/users")
	public Response getUsers(){
		return new FriendListResponse(repo.findAllUsers());
	}
	
	@RequestMapping("/makeFriends")
	public Response makeFriends(@RequestBody FriendsRequest request){
		return new Response(true);
	}
	
	@RequestMapping("/getFriends")
	public Response getFriends(@RequestBody EmailRequest request){
		return new FriendListResponse(Arrays.asList("aaa@a.com", "bbb@b.com"));
	}
	
	@RequestMapping("/getCommonFriends")
	public Response getCommonFriends(@RequestBody FriendsRequest request){
		return new FriendListResponse(Arrays.asList("ccc@c.com"));
	}
	
	@RequestMapping("/subscribe")
	public Response subscribe(@RequestBody TargetRequest request){
		return new Response(true);
	}
	
	@RequestMapping("/block")
	public Response block(@RequestBody TargetRequest request){
		return new ErrorResponse("this feature is not yet supported");
	}
	
	@RequestMapping("/postUpdate")
	public Response postUpdate(@RequestBody UpdateRequest request){
		if (request.getText() == null || request.getText().isEmpty())
			return new ErrorResponse("please enter your text");
		else
			return new RecipientListResponse(Arrays.asList("aaa@a.com", "ddd@d.com"));
	}
}
