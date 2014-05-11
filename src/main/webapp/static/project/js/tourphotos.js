(function(){
	function addImages(images) {
    	$.ajax({
    	    type: 'POST',
    		url: '/house/addimages?houseId='+GHouseId,
    		data: JSON.stringify(images),
    		dataType: 'json',
    		contentType: 'application/json',
    		mimeType: 'application/json',
    		success: function(response){
    			alert("保存成功");
    		},
    		error: function(request, status, error){
    			alert("保存失败");
    		}
    	});
    }
	function delImage(id) {
		$("#delBtn"+id).button('loading');
		$.ajax({
    	    type: 'DELETE',
    		url: '/banner/delete/?id=' + id + "&type=2",
    		success: function(response) {
    			$("#photo"+id).remove();
    		},
    		error: function(request, status, error){
    			$("#delBtn"+id).button("reset");
    			alert('图片删除失败！');
    		}
    	});
	}
	
	$(document).ready(function(){
		// init template
		var template = $("#template").html();
		Mustache.parse(template);
		
		// init sortable
		$('#sortable').sortable();
		
		// init event
    	$("#saveBtn").click(function(){
    		var images = [];
    		$("#sortable img").each(function(index){
    			var image = {
    				id: $(this).data("id"),
    				url: $(this).data("url"),
    				name: $(this).data("name"),
    				thumbnail: $(this).data("src")
    			}
    			image.type = 2;
    			image.sort = index;
    			images.push(image);
    		});
    		addImages(images);
    	});
    	
    	$("#sortable").delegate("button","click",function(){
    		var id = parseInt($(this).data("id"));
    		if (id > 0) {
    			delImage(id);
    		} else {
    			$(this).parent().parent().parent().parent().remove();
    		}
    	});
    	
    	$("#fileupload").fileupload({
    		dataType: 'json',
    		singleFileUploads: true,
    		submit: function(e, data){
    			if (data.files.length<=0) {
    				alert("请选择一个文件");
        			return false;
    			}
    			if (!/(zip|rar)$/.test(data.files[0].name)) {
        			alert("只能上传zip,rar格式的图片");
        			return false;
        		}
    			return true;
    		},
    		send: function(e, data){
    			$("#progress").parent().css("display","block");
    		},
    		done: function(e, data){
    			$("#saveBtn").parent().css("display","");
    			if (data && data.result && data.result.length > 0) {
    				data.result.sort(function(a,b){return a.name<=b.name})
    			}
    			$.each(data.result, function(index, file){
    				var rendered = Mustache.render(template, file);
    				$("#sortable").prepend(rendered);
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