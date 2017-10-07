package com.friends.responses;

import java.util.List;

public class FriendListResponse extends Response{
	private List<String> friends;
	private int count;
	
	public FriendListResponse(List<String> friends) {
		super(true);
		this.friends = friends;
		this.count = friends.size();
	}
	
	public List<String> getFriends() {
		return friends;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
