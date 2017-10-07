package com.friends.responses;

import java.util.List;

public class RecipientListResponse extends Response {
	private List<String> recipients;

	public RecipientListResponse(List<String> recipients) {
		super(true);
		this.recipients = recipients;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
	
	
}
