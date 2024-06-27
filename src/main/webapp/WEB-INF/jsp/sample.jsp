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
	    </select>
	</div>
	<div>
		<textarea id="msg" style="width: 500px; height: 200px; margin-top: 10px"></textarea>
	</div>
	<div>
		<button id="sendButton">Send Message Send</button>
	</div>

	<script type="text/javascript">
		$('#sendButton').click(function() {

			if ($('#channel').val() == "") {
				alert("타입 선택 필요")
				return;
			}
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
		
		function getChannelList() {
            $.ajax({
                type: 'GET',
                url: '/slack/sample/channels',
                contentType: 'application/json',
                success: function(response) {
                    const channels = response.channels;
                    const selectElement = $('#channel');
                    selectElement.empty(); // 기존 옵션들을 모두 지움

                    // 기본 옵션 추가
                    selectElement.append('<option value="">(선택)</option>');

                    // 각 채널을 옵션으로 추가
                    channels.forEach(channel => {
                        selectElement.append('<option value="' + channel.name + '">' + channel.name + '</option>');
                    });
                },
                error: function(error) {
                    alert('Error: ' + error.responseText);
                }
            });
        }
            

		function refresh() {
			$('#msg').val("");
			$('#channel').val("")
		}
		
		 $(document).ready(function() {
            // 페이지 로드 시 채널 목록을 가져옴
            getChannelList();
        });
	</script>
</body>
</html>
