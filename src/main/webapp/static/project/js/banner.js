(function(){
	function addBanners(banners) {
    	$.ajax({
    	    type: 'POST',
    		url: '/banner/addbanners',
    		data: JSON.stringify(banners),
    		dataType: 'json',
    		contentType: 'application/json',
    		mimeType: 'application/json',
    		success: function(response){
    			alert("保存成功");
    			location.reload();
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
    		url: '/banner/delete?id=' + id,
    		success: function(response) {
    			$("#banner"+id).remove();
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
		
		$("#sortable").delegate("button","click",function(){
    		var id = parseInt($(this).data("id"));
    		if (id > 0) {
    			if (window.confirm("确定要删除吗？"))
    				delImage(id);
    		} else {
    			$(this).parent().parent().parent().parent().remove();
    		}
    	});
		
    	$("#saveBtn").click(function(){
    		var banners = [];
    		$("#sortable img").each(function(index){
    			var banner = {
    				id: $(this).data("id"),
    				url: $(this).data("src"),
    				thumbnail: $(this).data("src")
    			}
    			banner.uid = parseInt($(this).next().find("input[type=text]").val())||0;
    			banner.createTime = $("#createTime"+index).val();
    			banner.sort = index;
    			banners.push(banner);
    		});
    		addBanners(banners);
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
    			$("#saveBtn").parent().css("display","");
    			$("#progress").parent().css("display","block");
    		},
    		done: function(e, data){
    			$.each(data.result, function(index, file){
    				var rendered = Mustache.render(template, {id:0,url:file,createTime:''});
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