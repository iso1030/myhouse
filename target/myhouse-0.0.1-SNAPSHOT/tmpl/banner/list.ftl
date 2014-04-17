<#include "/tmpl/common/frame.ftl">
<@pangu>
	<style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }

      @media (max-width: 980px) {
        /* Enable use of floated navbar text */
        .navbar-text.pull-right {
          float: none;
          padding-left: 5px;
          padding-right: 5px;
        }
      }
    </style>
	<@topbar module="banner"/>
    <div class="container-fluid">
      <div><input type="file" id="bannerImage" name="bannerImage" value=""></div>
      <ul class="thumbnails">
        <#assign bannerList = page.content>
		<#list bannerList as item>
		  <li class="span3">
		    <a href="${ctx}${item.url}" class="thumbnail" target="_blank">
		      <img data-src="${ctx}${item.url}" src="${ctx}${item.url}"  alt="" style="width: 200px; height: 300px;">
		    </a>
		    <p>上传时间：${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</p>
		    <p><a href="#" onclick="deleteBanner(${item.id});return false;">删除</a></p>
		  </li>
		</#list>
	  </ul>
      <#assign pageSize = 10
	           current = page.getNumber() + 1
	           begin = max(1, current - pageSize / 2)
	           end = min(begin + (pageSize - 1), page.getTotalPages())
	           sortType = ""
	           searchParams = ""/>
      <div class="pagination">
	    <ul>
	    	<#if page.hasPreviousPage()>
	    	<li><a href="?page=1">&lt;&lt;</a></li>
            <li><a href="?page=${current-1}">&lt;</a></li>
            <#else>
            <li class="disabled"><a href="#">&lt;&lt;</a></li>
            <li class="disabled"><a href="#">&lt;</a></li>
            </#if>
            
            <#list begin..end as i>
            <li class="active"><a href="?page=${i}">${i}</a></li>
            </#list>
            
            <#if page.hasNextPage()>
            <li><a href="?page=${current+1}">&gt;</a></li>
            <li><a href="?page=${page.totalPages}">&gt;&gt;</a></li>
            <#else>
            <li class="disabled"><a href="#">&gt;</a></li>
            <li class="disabled"><a href="#">&gt;&gt;</a></li>
            </#if>
	    </ul>
	  </div>
    </div>
    <!--/.fluid-container-->
    <script>
    function deleteBanner(id) {
    	$.ajax({
    	    type: 'DELETE',
    		url: '${ctx}/api/v1/banner/' + id,
    		success: function(response){
    			location.reload();
    		},
    		error: function(request, status, error){
    			alert('Banner删除失败！');
    		}
    	});
    }
    function ajaxUploadFile() {
    	//starting setting some animation when the ajax starts and completes
        $("#loading").ajaxStart(function(){
            $(this).show();
        }).ajaxComplete(function(){
            $(this).hide();
        });
    	$.ajaxFileUpload({
    	    type:'POST',
    		url: "${ctx}/banner/upload",
    		secureuri: false,
    		fileElementId: "bannerImage",
    		dataType: "json",
    		success: function(data, status){
    			location.reload();
    		},
    		error: function(data, status, error){
    			alert("图片上传失败");
    			$("#fileupload").val("");
    			console.log(data, status, error);
    		}
    	});
    }
    $(document).ready(function(){
        $("#packBtn").click(function(){
        	pack();
        });
    	$("#postBtn").click(function(){
    		addOrUpdate();
    	});
    	$("#bannerImage").change(function(){
    		var input = $(this).get(0),
    		    value = input.value.trim();
    		if (!value) {
    			alert("请选择一个文件");
    			return;
    		}
    		if (!/(jpg|png|jpeg|bmp)$/.test(value)) {
    			alert("只能上传jpg,png,jpeg,bmp格式的图片");
    			return;
    		}
    		ajaxUploadFile();
    	});
    });
    </script>
</@pangu>