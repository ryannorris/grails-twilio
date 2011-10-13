<html xmlns:g="grails">
<head>
<title>Send SMS Message</title>

</head>
<body>

<form>

<div><label for="destination">To</label>
<input type="text" name="destination" size="30" /></div>


<div><label for="message">Message (Max 140 Characters)</label><br />
<textarea rows="4" cols="10" name="message"></textarea>
</div>

<button name="send" type="button">Send</button>

</form>

<g:javascript library="jquery-1.6.4.min" />
<g:javascript library="underscore.min" />
<g:javascript library="backbone.min" />

<script type="text/javascript">

window.SmsMessage = Backbone.Model.extend({ 
	url: 'sms'
});

window.SmsConsoleView = Backbone.View.extend({
	el: "form",

	events: {
		"click button[name='send']":			"send_onClick",
		"keypress input[name='destination']":	"destination_onKeypress"
	},

	initialize: function(options) {
		_.bindAll(this, "send_onClick", "destination_onKeypress");
	},

	send_onClick: function(e) {
		var sms = new SmsMessage();
		sms.save({ 
			destination: $("input[name='destination']").val(), message: $("textarea[name='message']").val() 
		});
	},

	destination_onKeypress: function(e) {
	}
	
});

$(function() {
	new SmsConsoleView();
});

</script>

</body>
</html>