(function(){
	function addOrUpdate() {
    	var domForm = $("#tourForm").get(0);
    	$.ajax({
    	    type: 'POST',
    		url: '/house/addorupdate',
    		data: {
    			id: domForm.id.value,
    			address: domForm.address.value, 
    			price: domForm.price.value,
    			area: domForm.area.value,
    			bedrooms: domForm.bedrooms.value,
    			photographer: domForm.photographer.value,
    			openTime: $("#openTime").datepicker("getDate").getTime(),
    			coverImg: domForm.coverImg.value,
    			userId: domForm.uid.value
    		},
    		dataType: 'json',
    		success: function(response){
    			alert("保存成功");
    		},
    		error: function(request, status, error){
    			alert("保存失败");
    		}
    	});
    }
	
	$(document).ready(function(){
		$('#openTime').datepicker();
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
	    			$("#packBtn").parent().append('<a href="'+response.url+'" target="_blank">'+response.url+'</a>');
	    			$("#packBtn").remove();
	    			alert("打包成功");
	    		},
	    		error: function(request, status, error){
	    			$("#packBtn").button("reset");
	    			alert("打包失败");
	    		}
	    	});
		});
    	$("#saveInfoBtn").click(function(){
    		addOrUpdate();
    	});
    	$("#fileupload").fileupload({
    		dataType: 'json',
    		singleFileUploads: true,
    		submit: function(e, data){
    			if (data.files.length<=0) {
    				alert("请选择一个文件");
        			return false;
    			}
    			if (!/(jpg|png|jpeg|bmp)$/.test(data.files[0].name)) {
        			alert("只能上传jpg,png,jpeg,bmp格式的图片");
        			return false;
        		}
    			return true;
    		},
    		send: function(e, data){
    			$("#progress").parent().css("display","block");
    		},
    		done: function(e, data){
    			$.each(data.result, function(index, file){
    				$("#preview").attr("src", file);
    				$("#coverImg").attr("value", file);
    			});
    		},
    		fail: function(e, data){
    			alert("Upload Error");
    		},
    		progress: function(e, data){
    			var progress = parseInt(data.loaded * 100 / data.total, 10);
    			$("#progress").css("width", progress + "%");
    		}
    	});
    });
})();