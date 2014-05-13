(function(){
	$(document).ready(function(){
		$("#saveBtn").click(function(){
			var form = $("#videoForm").get(0);
			$.ajax({
	    	    type: 'POST',
	    		url: '/house/update/videotour',
	    		data: {
	    			id: form.houseId.value,
	    			youtube: form.youtube.value
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