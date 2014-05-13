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
    });
})();