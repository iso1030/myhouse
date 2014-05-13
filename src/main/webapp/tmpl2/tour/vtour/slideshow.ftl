<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>House Tour</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<style type="text/css">
html,body{padding:0;margin:0;}
body{background-color:#000;}
</style>
</head>
<body>
	<script src="/static/js/swfobject.js"></script>
    <div id="flashcontent">
		<p>To use WS-Slideshow update your 
	    <a href="http://www.adobe.com/go/getflashplayer" class="error">Flash Player Plugin</a>, 
	    please. You have to install the version 10 or later.</p>
	</div>
	<script type="text/javascript">		
		//  <![CDATA[	
		var flashvars = {
			XMLPath: "/house/xml/slides/${house.id}"
		};
	
		var params = {
			quality: "best",
			allowFullScreen: "true",
			bgcolor: "#000000"
		};
		var attributes = {
			id: "ws-slideshow",
			name: "ws-slideshow"
		};

		swfobject.embedSWF(	"/static/slideshow/deploy/ws-slideshow.swf", 
							"flashcontent", 
							"960", 
							"500", 
							"10.0",
							null, 
							flashvars, 
							params, 
							attributes);
		// ]]>			
	</script>
	<script>top.loaded();</script>
</body>
</html>
</#escape>