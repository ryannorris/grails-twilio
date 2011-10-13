class TwilioUrlMappings {

	static mappings = {
		"/sms/callback"(parseRequest: true) {
			controller: "smsCallback"
			action = [ POST: 'create', GET: 'read', PUT: 'update', DELETE: 'delete' ]
		}
		"/$controller/$id?"(parseRequest: true) {
			action = [ POST: 'create', GET: 'read', PUT: 'update', DELETE: 'delete' ]
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
