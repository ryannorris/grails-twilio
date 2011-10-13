package grails.plugin.twilio

import org.springframework.context.ApplicationEvent;

class SmsCallbackController {

    def create = {
		log.debug("Status: ${request.SmsStatus} for SID: ${request.SmsSid}")
		publishEvent(new SmsStatusEvent(this, request.SmsSid, request.SmsStatus == 'sent' ? true : false))
	}
}

class SmsStatusEvent extends ApplicationEvent {
	
	String sid
	boolean success
	
	public SmsStatusEvent(def source, String sid, boolean success) {
		super(source)
		this.sid = sid
		this.success = success
	}
	
}
