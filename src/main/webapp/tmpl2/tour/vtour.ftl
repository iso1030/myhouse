<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>House Tour</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<link rel="stylesheet" href="/static/styles/style.css"/>
<script src="/static/jquery/jquery-1.9.1.min.js"></script>
<script src="/static/js/swfobject.js"></script>
</head>
<body>
<div class="g-doc">
    <div class="g-hd">
    	<ul class="m-nav">
    		<li><a href="/vtour/${house.id}"><img src="/static/images/slideshow1.png">Slide Show</a></li>
    		<li><a href="/vtour/3d/${house.id}"><img src="/static/images/panorama1.png">Panorama</a></li>
    		<li><a href="/vtour/info/${house.id}"><img src="/static/images/home1.png">More&nbsp;Info</a></li>
    	</ul>
    </div>
    <div class="g-bd">
        <div class="m-box">
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
        	<div class="btn"><a href="javascript:;" id="toggleAgentInfo">SHOW AGENT INFO</a></div>
        	<#if user?exists>
        	<div class="user f-cb" id="agentInfoPanel" style="display:none;">
        		<div class="avatar">
        			<img src="${user.avatar!"/static/images/default_user_man.png"}"/>
        		</div>
        		<div class="profile">
        			<h3>${user.realname!""}</h3>
        			<p>${user.telephone!""}</p>
        			<p>${user.company!""}</p>
        			<p><a href="mailto:${user.email!""}">Click here to email me</a></p>
        		</div>
        	</div>
        	</#if>
        	<script>
        	$("#toggleAgentInfo").click(function(){
        		$("#agentInfoPanel").slideToggle();
        	});
        	</script>
        </div>
    </div>
    <div class="g-ft">
          Presented by Www.flyingmaple.ca
    </div>
</div>
</body>
</html>
</#escape>