// configuration for plugin testing - will not be included in the plugin zip

grails.mime.types = [ xml: ['text/xml', 'application/xml'],
	text: 'text-plain',
	js: 'text/javascript',
	rss: 'application/rss+xml',
	atom: 'application/atom+xml',
	css: 'text/css',
	csv: 'text/csv',
	all: '*/*',
	json: [ 'text/json', 'application/json' ] ,
	html: ['text/html','application/xhtml+xml']
  ]

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%c{2} %m%n')
    }

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
	
	debug  'grails.app.controller'
	all	   'groovyx.net.http'
}
/*
twilio {
	account {
		sid = 'XXXXXXX'
		auth_token = 'XXXXX'
	}
	
	sms {
		enable_status_callback = true
	}
	
	phones {
		main = '+16175551212'
	}
}
*/
