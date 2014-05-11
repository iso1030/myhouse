(function(){
	function addOrUpdate() {
    	var domForm = $("#randomForm").get(0);
    	$.ajax({
    	    type: 'POST',
    		url: '/random/add',
    		data: {
    			index: domForm.pageIndex.value, 
    			pos: domForm.pagePos.value,
    			userId: domForm.userId.value
    		},
    		dataType: 'json',
    		success: function(response){
    			alert("保存成功");
    			location.reload();
    		},
    		error: function(request, status, error){
    			alert("保存失败");
    		}
    	});
    }
	
	$(document).ready(function(){
		$("#searchBtn").click(function(){
			var phone = $("#searchInput").val();
			location.href = '/random?query='+encodeURIComponent(phone);
		});
    	$("#saveBtn").click(function(){
    		addOrUpdate();
    	});
    	
    	$("#sortable").delegate("button","click",function(){
    		var id = parseInt($(this).data("id"));
    		if (id > 0) {
    			delImage(id);
    		} else {
    			$(this).parent().parent().parent().parent().remove();
    		}
    	});
    	$("#listBox").delegate("button","click",function(){
    		var _this = this;
    		if (window.confirm("确认要删除吗？")) {
        		var id = parseInt($(_this).data("id"));
        		$.ajax({
            	    type: 'POST',
            		url: '/random/delete?id=' + id,
            		success: function(response) {
            			location.reload();
            		},
            		error: function(request, status, error){
            			alert('图片删除失败！');
            		}
            	});
    		}
    	});
    });
})();