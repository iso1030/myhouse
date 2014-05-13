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
<script src="/static/viewer/embedpano.js"></script>
<div id="pano" style="height:500px;">
    <script>
		embedpano({swf:"/static/viewer/krpano.swf", xml:"/house/xml/krpano/${house.id}", target:"pano", html5:"auto", passQueryParameters:true});
	</script>
</div>
<script>top.loaded();</script>
</body>
</html>
</#escape>