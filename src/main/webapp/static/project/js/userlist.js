(function(){
	$(document).ready(function(){
		$("#searchBtn").click(function(){
			var phone = $("#searchPhone").val();
			location.href = '/user?query='+encodeURIComponent(phone);
		});
		$("#listBox").delegate("button","click",function(){
			var _this = this;
    		if (window.confirm("确认要删除吗？")) {
        		var id = parseInt($(_this).data("id"));
        		$.ajax({
            	    type: 'DELETE',
            		url: '/user/delete?id=' + id,
            		success: function(response) {
            			location.reload();
            		},
            		error: function(request, status, error){
            			alert('客户删除失败！');
            		}
            	});
    		}
    	});
    });
})();