<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Slack Sample</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<h1>Slack Sample</h1>
	<div>
		<select id="channel">
			<option value="">(선택)</option>
			<option value="java-alarm-service">1채널</option>
			<option value="java-alarm-service2">2채널</option>
		</select>
	</div>
	<div>
		<textarea id="msg" style="width: 500px; height: 200px; margin-top: 10px"></textarea>
	</div>
	<div>
		<button id="sendButton">Send Message Send</button>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#sendButton').click(function() {

				if ($('#channel').val() == "") {
					alert("타입 선택 필요")
					return;
				}

				var type = "type"; // 필요에 따라 변경 가능
				var param = {
					"channel" : $('#channel').val(),
					"msg" : $('#msg').val()
				};

				$.ajax({
					type : 'POST',
					url : '/slack/sample',
					contentType : 'application/json',
					data : JSON.stringify(param),
					success : function(response) {
						alert('result: ' + response.result);
						refresh();
					},

					error : function(error) {
						alert('Error: ' + response.result);
					}
				});
			});
		});

		function refresh() {
			$('#msg').val("");
			$('#channel').val("")
		}
	</script>
</body>
</html>
