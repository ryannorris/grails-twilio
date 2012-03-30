class TwilioGrailsPlugin {
    def version = "0.1e"
    def grailsVersion = "1.3.3 > *"

    def author = "Ryan Norris"
    def authorEmail = ""
    def title = "Twilio Plugin"
    def description = "Add support for both sending and receiving phone calls and text messages via [Twilio|http://www.twilio.com]."

    // URL to the plugin's documentation
    def license = "APACHE"
    def documentation = "https://github.com/ryannorris/grails-twilio"
    def scm = [url: "https://github.com/ryannorris/grails-twilio"]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
        twilioHttpEndpointBean(groovyx.net.http.HTTPBuilder, 'https://api.twilio.com/2010-04-01/')
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
