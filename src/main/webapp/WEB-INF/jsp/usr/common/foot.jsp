<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
	var socket = null;
	
	$(document).ready(function () {
		connectWS();
	});
	
	function connectWS () {
		console.log("tttttttt");
		var ws = new WebSocket("ws://localhost:8081/auctionSocket?");
		socket = ws;
		
		ws.onopen = function () {
			console.log('Info : connection opened.');
		};
		
		ws.onmessage = function (event) {
	        console.log("ReceiveMessage:", event.data+'\n');
			let $socketAlert = $('div#socketAlert');
			$socketAlert.html(event.data);
			$socketAlert.css('display', 'block');
			
			setTimeout( function () {
				$socketAlert.css('display', 'none');
			}, 2000);
		};
		
		ws.onclose = function (event) {
			console.log('Info : connection closed.');
		};
		
		ws.onerror = function (err) {
			console.log('Error : ', err);
		};
	}
</script>
</body>
</html>