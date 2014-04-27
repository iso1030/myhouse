<!-- 
	krpano Virtual Tour Demo - Corfu
		http://krpano.com/tours/corfu/
	
	This tour was build fully automatic with the MAKE VTOUR (Normal) Droplet from the 1.16 krpano tools version.
	Note - this is an reduced example (smaller images, stronger compression, fewer panos) to keep the download package small!
-->
<krpano version="1.16" title="" onstart="wait(0);startup();">
	
	
	<!-- adjust the examples backbutton position (if available), the wait(0) in the onstart action allows executing this event -->
	<events name="backbutton_adjust" onxmlcomplete="if(layer[backbutton], set(layer[backbutton].y,190));" />
	

	<include url="/static/viewer/skin/vtourskin.xml" />

	<!-- set skin settings: bingmaps? gyro? -->
	<skin_settings bingmaps="false"
	               bingmaps_key="An4-9WoANqtASVorNezSjENest2VC0zoAzvMhEpdTjb3uY-k6u6bMkPxYpDsolsF"
	               bingmaps_zoombuttons="false"
	               gyro="true"
	               thumbs_width="120" thumbs_height="80" thumbs_padding="10" thumbs_crop=""
	               thumbs_opened="false"
	               thumbs_text="true"
	               thumbs_dragging="true"
	               thumbs_onhoverscrolling="false"
	               thumbs_scrollbuttons="true"
	               thumbs_scrollindicator="false"
	               tooltips_thumbs="false"
	               tooltips_hotspots="false"
	               tooltips_mapspots="false"
	               controlbar_offset="20"
	               />


	<action name="startup">
		if(startscene === null, copy(startscene,scene[0].name));
		loadscene(get(startscene), null, MERGE);
	</action>
	<#if d3images?exists>
	<#list d3images as image>
	<scene name="${image_index}" title="" onstart="" thumburl="${image.thumbnail!""}" lat="39.563340" lng="19.904324" heading="0.0">

		<view hlookat="-1" vlookat="11" fovtype="MFOV" fov="120" fovmin="70" fovmax="140" limitview="auto" />
		<image>
			<sphere url="${image.url}" />
		</image>
	</scene>
	</#list>
	</#if>
</krpano>
