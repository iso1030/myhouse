<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>House Tour</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<link rel="stylesheet" href="/static/styles/style.css"/>
<script src="/static/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<div class="g-doc">
    <div class="g-bd">
        <div class="m-box1">
        	<h2 class="ttl">${house.address}</h2>
        	<div class="info f-cb">
        		<div class="cover">
        			<img src="${house.coverImg}">
        		</div>
        		<div class="detail">
        			<p class="price">$${house.price}</p>
        			<p>TourID: ${house.id}</p>
        			<hr/>
        			<p><label class="lb">Area:</label><span>${house.area}M<sup>2</sup></span></p>
        			<p><label class="lb">Bedrooms:</label><span>${house.bedrooms}</span></p>
        			<p><label class="lb">Photographer:</label><span>${house.photographer}</span></p>
	        		<div class="open f-cb">
	        			<img class="f-fl" src="/static/images/icon_open_house.gif">
	        			<p class="f-fl">Please call for a showing.</p>
	        		</div>
        		</div>
        	</div>
        </div>
    </div>
</div>
<script>top.loaded();</script>
</body>
</html>
</#escape>