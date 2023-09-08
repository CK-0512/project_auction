<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
	var noticeSocket = null;
	
	$(document).ready(function () {
		connectWS();
	});
	
	function connectWS () {
		console.log("tttttttt");
		var ws = new WebSocket("ws://localhost:8081/webSocket/notice");
		noticeSocket = ws;
		
		ws.onopen = function () {
			console.log('Info : connection opened.');
		};
		
		ws.onmessage = function (event) {
		    var data = event.data;
		    // toast
		    let toastId = "toast-" + Date.now(); // Generate a unique ID
		    let toast = "<div id='" + toastId + "' class='toast z-10' role='alert' aria-live='assertive' aria-atomic='true'>";
		    toast += "<div class='toast-header'><i class='fas fa-bell mr-2'></i><strong class='mr-auto'>알림</strong>";
		    toast += "<small class='text-muted'>just now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>";
		    toast += "<span aria-hidden='true'>&times;</span></button>";
		    toast += "</div> <div class='toast-body'>" + data + "</div></div>";
		    $("#msgStack").append(toast);
		    
		    // Initialize and show the specific toast
		    $("#" + toastId).toast({"animation": true, "autohide": false});
		    $("#" + toastId).toast('show');
		    
		    // Set a timeout to hide the toast after 3 seconds
		    setTimeout(() => {
		        $("#" + toastId).toast('hide');
		    }, 3000);
		};

		
		ws.onclose = function (event) {
			console.log('Info : connection closed.');
		};
		
		ws.onerror = function (err) {
			console.log('Error : ', err);
		};
	}
</script>
	<div id="msgStack"></div>
</body>
</html>