/**
 * @author Yuqi Chou
 * @version  Sep 27, 2012
 */
package com.threeti.ics.web.domain;

/**
 * @author Yuqi Chou 
 * @version Sep 27, 2012  2:34:39 PM
 */
public enum MessagePushTypeEnum {
	
	SDKVERIFICATION("sdkverification"),
    BUILDCONVERSATION("buildconversation"),
    MESSAGETRANSFER("messagetransfer"),
    CUSTOMERSERVICELOGIN("customerservicelogin"),
    SESSIONOPERATION("sessionoperation"),
    ONLINECUSTOMERSERVICELIST("onlinecustomerservicelist"),
    MESSAGELIST("messagelist"),
    QUEUECONVERSATIONLIST("queueconversationlist"),
    UPLOADIMAGE("uploadimage"),
    CUSTOMERSERVICESTATUSCHANGE("customerservicestatuschange"),
    VISITORCONVERSATION("visitorconversation"),
    MESSAGESTATUSCHANGE("messagestatuschange");
	
	private final String name;
	
	MessagePushTypeEnum(String name) {
        this.name = name;
    }
	
	public String getName() {
		return name;
	}
}
