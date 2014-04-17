<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<slideshow>
	<!--
	
		WS-Slideshow 2 
		@see: http://www.ws-slideshow.com
	
		Standard configuration / Standard Konfiguration
	
	-->	
	
	<preferences 
		thumbSize="50"
		/>

	<albums>
		<#if house?exists>
	    <album slidePath="" thumbPath="">
			<slides>
				<#list d2images as item>
				<slide name="${item.url}" thumbName="${item.thumbnail}"/>
				</#list>
			</slides>
		</album>
		</#if>
	</albums>
</slideshow>
