package grails.twilio

import groovyx.net.http.ContentType
import groovyx.net.http.Method

import org.apache.http.auth.params.AuthPNames
import org.apache.http.client.params.AuthPolicy
import org.codehaus.groovy.grails.commons.ConfigurationHolder as C

class SmsService {

	def twilioHttpEndpointBean

	static transactional = true

	def send(String destinationPhoneNumber, String message) {
		if(C.config.twilio?.proxy) {
			log.debug "proxy use detected, will proxy through ${C.config.twilio?.proxy.method}://${C.config.twilio?.proxy.host}:${C.config.twilio?.proxy.port}"
			
			twilioHttpEndpointBean.setProxy(
				C.config.twilio?.proxy.host, 
				C.config.twilio?.proxy.port, 
				C.config.twilio?.proxy.method)
			
			if(C.config.twilio.proxy.auth) {
				log.debug "authenticating for proxy: ${C.config.twilio?.proxy.auth.username}"
				
				twilioHttpEndpointBean.auth.basic(
					C.config.twilio?.proxy.host, 
					C.config.twilio?.proxy.port, 
					C.config.twilio?.proxy.auth.username, 
					C.config.twilio?.proxy.auth.password)
				
				List<String> authpref = new ArrayList<String>()
				authpref.add(AuthPolicy.BASIC)
				
				twilioHttpEndpointBean.getClient().getParams().setParameter(AuthPNames.PROXY_AUTH_PREF, authpref)
			}
		}
		
		twilioHttpEndpointBean.auth.basic(C.config.twilio?.account.sid, C.config.twilio?.account.auth_token)
		
		log.debug "using twilio account sid ${C.config.twilio?.account.sid}"
		log.debug "twilio auth token is ${C.config.twilio?.account.auth_token}"
		log.debug "message = ${message}, destination = ${destinationPhoneNumber}, from = ${C.config.twilio?.phones.main}"

		def result = twilioHttpEndpointBean.request(Method.POST) { req ->
			requestContentType = ContentType.URLENC
			uri.path = "Accounts/${C.config.twilio?.account.sid}/SMS/Messages.json"

			if(C.config.twilio?.sms?.enable_status_callback) {
				log.debug("telling twilio the callback url is: ${C.config.grails.serverURL}/sms/callback")
				body = [ To: destinationPhoneNumber, From: C.config.twilio?.phones.main, Body: message, StatusCallback: "${C.config.grails.serverURL}/sms/callback" ]
			} else {
				body = [ To: destinationPhoneNumber, From: C.config.twilio?.phones.main, Body: message ]
			}

			response.success = { resp, data ->
				log.info("message sent, status = ${data.status}, sid = ${data.sid}")
				return [status: data.status, sid: data.sid]
			}

			response.failure = { resp, data ->
				log.error("could not send message: ${data.status} (${data.message})")
				throw new RuntimeException("${data.message}")
			}
		}
	}
}
