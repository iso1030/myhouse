(function(){
	$(document).ready(function(){
		$("#saveBtn").click(function(){
			var form = $("#musicForm").get(0);
			$.ajax({
	    	    type: 'POST',
	    		url: '/house/update/bgmusic',
	    		data: {
	    			id: form.houseId.value,
	    			bgMusic: form.music.value
	    		},
	    		dataType: 'json',
	    		success: function(response){
	    			alert("保存成功");
	    		},
	    		error: function(request, status, error){
	    			alert("保存失败");
	    		}
	    	});
		});
		$("#packBtn").click(function(){
			$(this).button("loading");
			var id = $(this).data("id");
			$.ajax({
	    	    type: 'POST',
	    		url: '/house/package?id='+id,
	    		data: {},
	    		dataType: 'json',
	    		success: function(response){
	    			$("#packBtn").button("reset");
	    			if (response.error) {
	    				alert(response.error);
	    				return;
	    			}
//	    			$("#packBtn").parent().append('<a href="'+response.url+'" target="_blank">http://'+location.host+response.url+'</a>');
	    			$("#packBtn").prev().html('<a href="'+response.url+'" target="_blank">http://'+location.host+response.url+'</a>');
	    			alert("打包成功");
	    		},
	    		error: function(request, status, error){
	    			$("#packBtn").button("reset");
	    			alert("打包失败");
	    		}
	    	});
		});
    });
})();