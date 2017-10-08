package com.friends.controller;

import java.util.Arrays;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.friends.exceptions.ApplicationException;
import com.friends.requests.EmailRequest;
import com.friends.requests.FriendsRequest;
import com.friends.requests.TargetRequest;
import com.friends.requests.UpdateRequest;
import com.friends.responses.ErrorResponse;
import com.friends.responses.FriendListResponse;
import com.friends.responses.RecipientListResponse;
import com.friends.responses.Response;
import com.friends.service.FriendService;

@RestController
@RequestMapping("/friend/*")
public class FriendManagementController {
	
	@Autowired
	private FriendService service;
	
	@RequestMapping("/users")
	public Response getUsers(){
		try{
			return new FriendListResponse(service.findAllUsers());
		}catch (Exception e){
			return new ErrorResponse("Unknown error occured");
		}
	}
	
	@RequestMapping("/makeFriends")
	public Response makeFriends(@RequestBody FriendsRequest request){
		return doWithException( () -> {
			service.makeFriends(request.getFriends());
			return new Response(true);
		});
	}
	
	@RequestMapping("/getFriends")
	public Response getFriends(@RequestBody EmailRequest request){
		return doWithException( () -> new FriendListResponse(service.findFriends(request.getEmail())));
	}
	
	@RequestMapping("/getCommonFriends")
	public Response getCommonFriends(@RequestBody FriendsRequest request){
		return doWithException( () -> new FriendListResponse(service.findCommonFriends(request.getFriends())));
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
	
	private Response doWithException(Supplier<Response> service){
		try{
			return service.get();
		} catch (ApplicationException e){
			return new ErrorResponse(e.getMessage());
		} catch (Exception e){
			return new ErrorResponse("Unknown error occured");
		}
	}
}
