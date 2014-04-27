<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>House Tour</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<link rel="stylesheet" href="/static/styles/style.css"/>
<script src="/static/jquery/jquery-1.9.1.min.js"></script>
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
        	<h2 class="ttl">${house.address}</h2>
        	<div class="info f-cb">
        		<div class="cover">
        			<img src="${house.coverImg}">
        		</div>
        		<div class="detail">
        			<p class="price">$${house.price}</p>
        			<p>TourID: HT${house.id}</p>
        			<hr/>
        			<p><label class="lb">Area:</label><span>${house.area}M<sup>2</sup></span></p>
        			<p><label class="lb">Bedrooms:</label><span>${house.bedrooms}</span></p>
	        		<div class="open f-cb">
	        			<img class="f-fl" src="/static/images/icon_open_house.gif">
	        			<p class="f-fl">Please call for a showing.</p>
	        		</div>
        		</div>
        	</div>
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