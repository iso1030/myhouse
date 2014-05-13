(function(){
	function addOrUpdate() {
    	var domFrom = $("#userForm").get(0);
    	$.ajax({
    	    type: 'POST',
    		url: '/user/addorupdate',
    		data: {
    			id: domFrom.userId.value,
    			nickname: domFrom.nickname.value, 
    			avatar: domFrom.avatar.value,
    			telephone: domFrom.telephone.value,
    			realname: domFrom.realname.value,
    			email: domFrom.email.value,
    			company: domFrom.company.value
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
		$("#searchBtn").click(function(){
			var phone = $("#searchPhone").val();
			location.href = '/user?query='+encodeURIComponent(phone);
		});
    	$("#saveBtn").click(function(){
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
    				$("#avatar").attr("value", file);
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