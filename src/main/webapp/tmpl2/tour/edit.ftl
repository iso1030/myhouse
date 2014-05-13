<#include "../frame.ftl">
<#escape x as x?html>
<@pangu>
	<link href="${cssBase}/static/styles/ui-lightness/jquery-ui-1.9.2.custom.min.css" type="text/css" rel="stylesheet" />
	<@navbar module="tour"/>
	<div class="container">
      <div class="row">
      	  <ol class="breadcrumb">
  			<li><a href="/house">Tours</a></li>
  			<li class="active">Edit</li>
		  </ol><!-- /.breadcrumb -->

      	  <div class="panel panel-default">
  			<div class="panel-body">
    			<h2>TourID: ${house.id}<small> ${house.address!""}</small></h2>
  			</div>
		  </div>
		  
		  <div class="panel panel-default">
		  	<div class="panel-body">
		  		<p><span class="glyphicon glyphicon-eye-open"></span> Tour Link: <a href="/vtour/${house.id}" target="_blank">http://<script>document.write(location.host)</script>/vtour/${house.id}</a></p>
		  		<p><span class="glyphicon glyphicon-download"></span> Original Photos Download: <#if house.packageUrl?has_content><a href="${house.packageUrl!""}" target="_blank">http://<script>document.write(location.host)</script>${house.packageUrl!""}</a><#else><button type="button" class="btn btn-primary btn-xs" data-id="${house.id}" id="packBtn">Pack Tour</button></#if></p>
		  	</div>
		  </div>
		  
		  <#if !submodule?has_content><#assign submodule="edit"/></#if>
		  <div class="" id="main">
		  	<a href="/house/edit/?houseId=${house.id}#main" class="btn btn-<#if submodule=="edit">primary<#else>default</#if> btn-sm">Property Information</a>
		  	<a href="/house/edit/?houseId=${house.id}&m=photos#main" class="btn btn-<#if submodule=="photos">primary<#else>default</#if> btn-sm">Photos</a>
		  	<a href="/house/edit/?houseId=${house.id}&m=panoramas#main" class="btn btn-<#if submodule=="panoramas">primary<#else>default</#if> btn-sm">Panoramas</a>
		  	<a href="/house/edit/?houseId=${house.id}&m=video#main" class="btn btn-<#if submodule=="video">primary<#else>default</#if> btn-sm">Video Tour</a>
		  	<a href="/house/edit/?houseId=${house.id}&m=bgmusic#main" class="btn btn-<#if submodule=="bgmusic">primary<#else>default</#if> btn-sm">Background Music</a>
		  </div>
		  
		  <!-- Tab panes -->
		  <br>
		  <div class="well">
		  	<#if submodule=="photos"><#include "./include/photos.ftl"/>
		  	<#elseif submodule=="panoramas"><#include "./include/panoramas.ftl"/>
		  	<#elseif submodule=="video"><#include "./include/video.ftl"/>
		  	<#elseif submodule=="bgmusic"><#include "./include/bgmusic.ftl"/>
		  	<#else><#include "./include/info.ftl"/></#if>
	      </div>
      </div>
    </div><!-- /.container -->
    
    <script>var GHouseId = ${house.id!"0"}</script>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${jsBase}/static/jquery/1.9.1/jquery.min.js"></script>
	<script src="${jsBase}/static/jquery/jquery-ui-1.9.2.custom.min.js"></script>
    <script src="${jsBase}/static/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <script src="${jsBase}/static/jquery-fileupload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${jsBase}/static/jquery-fileupload/js/jquery.iframe-transport.js"></script>
    <script src="${jsBase}/static/jquery-fileupload/js/jquery.fileupload.js"></script>
    <script src="${jsBase}/static/mustache/mustache.js"></script>
    <script src="${jsBase}/static/project/js/tour${submodule}.js"></script>
</@pangu>
</#escape>