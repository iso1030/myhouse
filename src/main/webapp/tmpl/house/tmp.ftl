<#escape x as x?html>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${house.address}</title>
	<script type="text/javascript" src="/static/js/swfobject.js"></script>
</head>
<body>
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

</body>
</html>
</#escape>

<!DOCTYPE html>
<html>
<head>
	<title>krpano.com - Corfu Virtual Tour</title>
	<meta name="viewport" content="target-densitydpi=device-dpi, width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="x-ua-compatible" content="IE=edge" />
	<style>
		@-ms-viewport { width: device-width; }
		@media only screen and (min-device-width: 800px) { html { overflow:hidden; } }
		html { height:100%; }
		body { height:100%; overflow:hidden; margin:0; padding:0; font-family:Arial, Helvetica, sans-serif; font-size:16px; color:#FFFFFF; background-color:#000000; }
	</style>
</head>
<body>

<script src="/static/viewer/embedpano.js"></script>

<div id="pano" style="width:100%;height:100%;">
	<noscript><table style="width:100%;height:100%;"><tr style="valign:middle;"><td><div style="text-align:center;">ERROR:<br/><br/>Javascript not activated<br/><br/></div></td></tr></table></noscript>
	<script>
		embedpano({swf:"/static/viewer/krpano.swf", xml:"/house/xml/krpano/${house.id}", target:"pano", html5:"auto", passQueryParameters:true});
	</script>
</div>

</body>
</html>
