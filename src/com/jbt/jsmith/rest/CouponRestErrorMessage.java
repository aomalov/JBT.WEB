/**
 * 
 */
package com.jbt.jsmith.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author andrewm
 *
 *Error class utility - translation to JSON to web client
 */
@XmlRootElement
public class CouponRestErrorMessage {
	
	String messageText;

	public CouponRestErrorMessage(String messageText) {
		super();
		this.messageText = messageText;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	/**
	 * 
	 */
	public CouponRestErrorMessage() {
		// TODO Auto-generated constructor stub
	}

}
