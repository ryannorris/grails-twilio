package grails.plugin.twilio

class SmsController {
	
	def smsService
	
	def twilioHttpEndpointBean

	def read = { withFormat { html { } } }

	def create = { SendSmsCommand cmd ->
		def validMessage = cmd.validate();

		log.debug "Incoming message is ${validMessage ? 'valid' : 'invalid'}"
		log.debug "Format is ${request.format}"

		withFormat {
			json {
				if(validMessage) {
					def smsResponse
					
					try {
						smsResponse = smsService.send(cmd.destination, cmd.message)
						render(contentType: "application/json") {
							result(success: true)
						}
					} catch(Exception e) {
						render(contentType: "application/json", status: 500) {
							result(success: false, message: e.message)
						}
					}
				} else {
					render(contentType: "application/json", status: 500) { result(success: false) }
				}
			}
		}
	}
}

class SendSmsCommand {
	String destination
	String message

	static constraints = {
		message(size: 1..140)
	}
}
