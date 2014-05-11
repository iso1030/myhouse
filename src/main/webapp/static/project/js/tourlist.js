(function(){
	$(document).ready(function(){
		$("#searchBtn").click(function(){
			var phone = $("#searchAddress").val();
			location.href = '/house?query='+encodeURIComponent(phone);
		});
		$("#listBox").delegate("button","click",function(){
			var _this = this;
    		if (window.confirm("确认要删除吗？")) {
        		var id = parseInt($(_this).data("id"));
        		$.ajax({
            	    type: 'DELETE',
            		url: '/house/delete?id=' + id,
            		success: function(response) {
            			location.reload();
            		},
            		error: function(request, status, error){
            			alert('Tour删除失败！');
            		}
            	});
    		}
    	});
    });
})();