/**
 * 
 */
package com.jbt.jsmith.rest;

/**
 * @author andrewm
 *
 *Error class utility - translation to JSON to web client
 */
//@XmlRootElement(name="CouponRestErrorMessage")
public class CouponRestErrorMessage {
	
	String messageText;
	String messageType;

	public CouponRestErrorMessage(String messageText,String messageType) {
		super();
		this.messageText = messageText;
		this.messageType = messageType;
	}

//	@XmlElement
	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	/**
	 * @return the messageType
	 */
//	@XmlElement
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * 
	 */
	public CouponRestErrorMessage() {
		// TODO Auto-generated constructor stub
	}

}
