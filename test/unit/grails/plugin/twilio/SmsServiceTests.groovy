package grails.plugin.twilio

import grails.test.*
import groovyx.net.http.AuthConfig;
import groovyx.net.http.HTTPBuilder;
import groovyx.net.http.Method;

import org.codehaus.groovy.grails.commons.ConfigurationHolder as C;
import org.gmock.WithGMock;

@WithGMock
class SmsServiceTests extends GrailsUnitTestCase {
	
	String sid = 'sid'
	String authToken = 'authtoken'
	String phone = '5555551212'
	
	def httpMock
	
	protected void setUp() {
		super.setUp()

		httpMock = mockFor(HTTPBuilder)
		
		mockConfig('''
			twilio {
				account {
					sid = "${sid}"
					auth_token = "${authToken}"
				}
				
				phones {
					main = "${phone}"
				}
			}
			
		''')
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testSendingSuccessfulSms() {

		httpMock.demand.auth(1..1) { ->
			def authMock = mockFor(AuthConfig)
			authMock.demand.basic(1..1) { username, password ->
				assert username == sid
				assert password == authToken
			}
			
			return authMock.createMock()
		}
		
		httpMock.demand.request(1..1) { method, closure ->
			assert method == Method.POST
		}
		
		def twilioHttpEndpointBean = mock(HTTPBuilder)
		def auth = mock(AuthConfig)
		auth.basic(sid, authToken)
		
		twilioHttpEndpointBean.auth.returns(auth)
		
		play {
			println C.config.twilio.account.sid
			twilioHttpEndpointBean.auth.basic(C.config.twilio.account.sid, C.config.twilio.account.auth_token)
		}
	}
}