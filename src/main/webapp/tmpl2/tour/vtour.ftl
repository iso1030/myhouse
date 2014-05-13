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
	<script src="/static/js/jquery.jplayer.min.js"></script>
	<script src="/static/js/Hash.js"></script>
</head>
<body>
<div class="g-doc">
    <div class="g-hd">
    	<ul class="m-nav">
    		<li><a href="#slideshow"><i class="icn icn1"></i>Slide&nbsp;Show</a>
    		<li><a href="#panorama"><i class="icn icn2"></i>Panorama</a>
    		<li><a href="#moreinfo"><i class="icn icn3"></i>More&nbsp;Info</a>
    		<li><a href="#videotour"><i class="icn icn4"></i>Video&nbsp;Tour</a>
    	</ul>
    </div>
    <div class="g-bd">
        <div class="m-box">
        	<div class="cnt" id="innerBox">
	        	<iframe class="frm" id="contentFrame" name="contentFrame" src="about:blank" scrolling="no" frameborder="0"></iframe>
        	</div>
        	<div class="ant">
        		<a id="toggleAgentInfo" href="javascript:;" class="btn">Show Agent Info</a>
        		<a id="toggleVolumn" href="javascript:;" class="vol">&nbsp;</a>
        	</div>
        	<div class="btn"><a href="javascript:;" id="toggleAgentInfo">SHOW AGENT INFO</a></div>
        	<#if !user?exists><#assign user = {}></#if>
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
        	<div id="jquery_jplayer_1"></div>
        </div>
    </div>
    <div class="g-ft">Presented by www.flyingmaple.ca</div>
    <script>
    	$(document).ready(function() {
        	$("#toggleAgentInfo").click(function(){
        		$("#agentInfoPanel").slideToggle();
        	});
        	$("#toggleVolumn").click(function(){
        		if ($(this).hasClass("vol1")) {
        		    $(this).removeClass("vol1");
        		    $("#jquery_jplayer_1").jPlayer("volume", 0.75);
        		} else {
        			$(this).addClass("vol1");
        		    $("#jquery_jplayer_1").jPlayer("volume", 0);
        		}
        	});
        	var currentHash = "#";
        	window.loaded = function(){
			    $("#contentFrame").css("height", /^\moreinfo/.test(currentHash)?"330px":"500px");
        	};
        	function handler(newHash, initial) {
        		newHash = newHash||"slideshow";
        		currentHash = newHash;
        		$("#contentFrame").css("height","0px");
			    $("#contentFrame").attr("src","/vtour/"+newHash+"/${house.id}");
			    //$("#contentFrame").css("height", /^moreinfo/.test(newHash)?"330px":"500px");
			    if (initial) {
			    	// nothing todo
			    } else {
			        //$("#contentFrame").attr("src","/vtour"+newHash);
			    }
			}
			Hash.init(handler);
			
			<#if house.bgMusic?has_content>
			$("#jquery_jplayer_1").jPlayer({
		        ready: function() {
		          $(this).jPlayer("setMedia", {
		            mp3: "${house.bgMusic}"
		          }).jPlayer("play");
		          var click = document.ontouchstart === undefined ? 'click' : 'touchstart';
		          var kickoff = function () {
		            $("#jquery_jplayer_1").jPlayer("play");
		            document.documentElement.removeEventListener(click, kickoff, true);
		          };
		          document.documentElement.addEventListener(click, kickoff, true);
		        },
		        loop: true,
		        swfPath: "/static/js"
      		});
      		</#if>
		});
    </script>
</div>
</body>
</html>
</#escape>