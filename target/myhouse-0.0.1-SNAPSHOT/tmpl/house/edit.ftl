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
      .preview {
      	display:block;
      	height:100px;
      	width:100px;
      	border:1px solid #ccc;
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
	<@topbar module="house"/>
    <div class="container-fluid">
    <ul class="nav nav-tabs" id="myTab">
	  <li class="active"><a href="#home">基本信息</a></li>
	  <li <#if !house?exists>class="disabled"</#if>><a href="#profile">2D图片</a></li>
	  <li <#if !house?exists>class="disabled"</#if>><a href="#messages">3D图片</a></li>
	</ul>
	 
	<div class="tab-content">
	  <div class="tab-pane active container-fluid" id="home">
	    <#if !house?exists><#assign house = {}></#if>
	    <form class="form-horizontal" onsubmit="return false;" id="houseForm" method="POST" enctype="multipart/form-data" action="">
	      <iframe style="display:none;" name="avatar_frame" id="avatar_frame" src="${ctx}/crossdomain.html"></iframe>
	      <input type="hidden" name="id" value="${house.id?default(0)}"/>
	      <input type="hidden" id="coverImg" name="coverImg" value="${house.coverImg?default("")}"/>
		  <div class="control-group">
		    <label class="control-label" for="address">详细地址</label>
		    <div class="controls">
		      <input type="text" id="address" name="address" value="${house.address?default("")}">
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="price">价格</label>
		    <div class="controls">
		      <input type="text" id="price" name="price" value="${house.price?default("")}">
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="area">面积</label>
		    <div class="controls">
		      <input type="text" id="area" name="area" value="${house.price?default("")}">
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="bedrooms">厅室</label>
		    <div class="controls">
		      <input type="text" id="bedrooms" name="bedrooms" value="${house.bedrooms?default("")}">
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="openTime">开放时间</label>
		    <div class="controls">
		      <input type="text" id="openTime" name="openTime" value="<#if house.openTime?exists>${house.openTime?string("MM/dd/yyyy")}</#if>">
		    </div>
		  </div>
		  <div class="control-group" style="display:none;">
		    <label class="control-label" for="bgMusic">背景音乐</label>
		    <div class="controls">
		      <input type="text" id="bgMusic" name="bgMusic" value="${house.bgMusic?default("")}">
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="coverImg">封面图片</label>
		    <div class="controls">
		      <img class="preview" id="preview"<#if house.coverImg?has_content> src="${house.coverImg}"</#if>/>
		      <input type="file" id="coverImgFile" name="coverImgFile" value="">
	      	  <span id="loading" style="display:none;">上传中...</span>
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="email">房屋屋主</label>
		    <div class="controls">
		      <input type="text" id="userId" name="userId" value="${house.uid?default("")}">
		    </div>
		  </div>
		  <div class="control-group">
		    <div class="controls">
		      <button class="btn" id="postBtn">保存</button>
		      <button class="btn" id="packBtn">打包</button>
		    </div>
		  </div>
		</form><!--/.fluid-container-->
	  </div>
	  <div class="tab-pane  container-fluid" id="profile">
		<ul class="thumbnails">
		<#if d2images?exists>
		<#list d2images as item>
		  <li class="span3">
		    <a href="${item.url}" class="thumbnail" target="_blank">
		      <img data-src="${item.thumbnail}" src="${item.thumbnail}" alt="180x180" src="" style="width: 180px; height: 180px;">
		    </a>
		    <p><a href="#" onclick="delete2d(${item.id});return false;">删除</a></p>
		  </li>
		</#list>
		</#if>
		</ul>
		<div class="control-group">
	      <label class="control-label" for="coverImg">缩略图片</label>
	      <div class="controls">
	        <img class="preview" id="preview1"/>
	        <input type="file" id="2dthumnail" name="thumnail" value="">
      	    <span id="2dloading" style="display:none;">上传中...</span>
	      </div>
	    </div>
	    <div class="control-group">
	      <label class="control-label" for="coverImg">原图片</label>
	      <div class="controls">
	        <img class="preview" id="preview2"/>
	        <input type="file" id="2dorigin" name="origin" value="">
      	    <span id="2dloading" style="display:none;">上传中...</span>
	      </div>
	    </div>
	    <div class="control-group">
	      <div class="controls">
	        <button class="btn" id="postBtn2D">保存</button>
	      </div>
	    </div>
	  </div>
	  <div class="tab-pane" id="messages">
	  	<ul class="thumbnails">
	  	<#if d3images?exists>
		<#list d3images as item>
		  <li class="span3">
		    <a href="${item.url}" class="thumbnail" target="_blank">
		      <img data-src="${item.thumbnail}" src="${item.thumbnail}"  alt="180x180" src="" style="width: 180px; height: 180px;">
		    </a>
		    <p><a href="#" onclick="delete3d(${item.id});return false;">删除</a></p>
		  </li>
		</#list>
		</#if>
		</ul>
		<div class="control-group">
	      <label class="control-label" for="coverImg">缩略图片</label>
	      <div class="controls">
	        <img class="preview" id="preview3"/>
	        <input type="file" id="3dthumnail" name="thumnail" value="">
      	    <span id="3dloading" style="display:none;">上传中...</span>
	      </div>
	    </div>
	    <div class="control-group">
	      <label class="control-label" for="coverImg">原图片</label>
	      <div class="controls">
	        <img class="preview" id="preview4"/>
	        <input type="file" id="3dorigin" name="origin" value="">
      	    <span id="3dloading" style="display:none;">上传中...</span>
	      </div>
	    </div>
	    <div class="control-group">
	      <div class="controls">
	        <button class="btn" id="postBtn3D">保存</button>
	      </div>
	    </div>
	  </div>
	</div>
	</div>
	<script>
	  $(function () {
	  	$('#openTime').datepicker();
	  	var search = location.search,
	  	    tindex = 0;
	  	if (search) {
	  	  search = search.substr(1);
	  	  var list = search.split("&");
	  	  for (var i=0; i<list.length; ++i) {
	  	  	var tmp = list[i].split("=");
	  	  	if (tmp[0] == "tabindex") {
	  	  	  tindex = parseInt(tmp[1]);
	  	  	  break;
	  	  	}
	  	  }
	  	}
	  	if (tindex < 0 || tindex > 2)
	  	  tindex = 0;
	  	if (tindex == 2)
	    	$('#myTab a:last').tab('show');
	    else if (tindex == 1)
	        $('#myTab li:eq(1) a').tab('show');
	    else
	       $('#myTab a:first').tab('show');
	  })
	  $('#myTab a').click(function (e) {
		  e.preventDefault();
		  if ($(this).parent().hasClass("disabled"))
		  	return;
		  $(this).tab('show');
		})
	</script>
    <script>
    function delete2d(id) {
    	$.ajax({
    	    type: 'DELETE',
    		url: '${ctx}/api/v1/image/' + id,
    		success: function(response){
    			location.href = location.href + "&tabindex=1";
    		},
    		error: function(request, status, error){
    			alert('图片删除失败！');
    		}
    	});
    }
    function delete3d(id) {
    	$.ajax({
    	    type: 'DELETE',
    		url: '${ctx}/api/v1/image/' + id,
    		success: function(response){
    			location.href = location.href + "&tabindex=2";
    		},
    		error: function(request, status, error){
    			alert('图片删除失败！');
    		}
    	});
    }
    function pack() {
    	var domFrom = $("#houseForm").get(0);
    	var id = parseInt(domFrom.id.value);
    	$.ajax({
    	    type: 'POST',
    		url: '${ctx}/api/v1/house/pack.v',
    		data: {
    			id: domFrom.id.value
    		},
    		dataType: 'json',
    		success: function(response){
    			if (response.code != 200) {
    				alert(response.msg);
    				return;
    			}
    			alert("打包成功");
    		},
    		error: function(request, status, error){
    			alert("打包失败");
    		}
    	});
    }
    function addOrUpdate() {
    	var domFrom = $("#houseForm").get(0);
    	var id = parseInt(domFrom.id.value);
    	$.ajax({
    	    type: 'POST',
    		url: id>0?'${ctx}/api/v1/house/update.v':'${ctx}/api/v1/house/create1.v',
    		data: {
    			id: domFrom.id.value,
    			address: domFrom.address.value, 
    			price: domFrom.price.value,
    			area: domFrom.area.value,
    			bedrooms: domFrom.bedrooms.value,
    			openTime: $("#openTime").datepicker("getDate").getTime(),
    			bgMusic: domFrom.bgMusic.value,
    			coverImg: domFrom.coverImg.value,
    			userId: domFrom.userId.value
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
    function ajaxUploadFile() {
    	//starting setting some animation when the ajax starts and completes
        $("#loading").ajaxStart(function(){
            $(this).show();
        }).ajaxComplete(function(){
            $(this).hide();
        });
    	$.ajaxFileUpload({
    	    type:'POST',
    		url: "${ctx}/upload/?type=cover",
    		secureuri: false,
    		fileElementId: "coverImgFile",
    		dataType: "json",
    		success: function(data, status){
    			$("#coverImgFile").val("");
    			$("#preview").attr("src",data.files[0]);
    			$("#coverImg").attr("value",data.files[0]);
    		},
    		error: function(data, status, error){
    			$("#coverImgFile").val("");
    		}
    	});
    }
    function addOrUpdate2D() {
    	var domFrom = $("#houseForm").get(0);
    	var id = parseInt(domFrom.id.value);
    	$.ajax({
    	    type: 'POST',
    		url: '${ctx}/api/v1/image/create1.v',
    		data: {
    		    type: '2',
    		    hid: id,
    		    url: $("#preview2").attr("src"),
    		    thumbnail: $("#preview1").attr("src")
    		},
    		dataType: 'json',
    		success: function(response){
    			alert("保存成功");
    			location.href = location.href + "&tabindex=1";
    		},
    		error: function(request, status, error){
    			alert("保存失败");
    		}
    	});
    }
    function ajaxUpload2DFile(type) {
    	var domFrom = $("#houseForm").get(0);
    	var id = parseInt(domFrom.id.value);
    	var fileElementId = type==0?'2dthumnail':'2dorigin';
    	//starting setting some animation when the ajax starts and completes
        $("#2dloading").ajaxStart(function(){
            $(this).show();
        }).ajaxComplete(function(){
            $(this).hide();
        });
    	$.ajaxFileUpload({
    	    type:'POST',
    		url: "${ctx}/upload/houseimg/?houseId="+id+"&type=2",
    		secureuri: false,
    		fileElementId: fileElementId,
    		dataType: "json",
    		success: function(data, status){
    			$("#"+fileElementId).val("");
    			$("#preview"+(type==0?1:2)).attr("src",data.files[0]);
    		},
    		error: function(data, status, error){
    			$("#"+fileElementId).val("");
    			alert("上传失败");
    		}
    	});
    }
    
    function addOrUpdate3D() {
    	var domFrom = $("#houseForm").get(0);
    	var id = parseInt(domFrom.id.value);
    	$.ajax({
    	    type: 'POST',
    		url: '${ctx}/api/v1/image/create1.v',
    		data: {
    		    type: '3',
    		    hid: id,
    		    url: $("#preview4").attr("src"),
    		    thumbnail: $("#preview3").attr("src")
    		},
    		dataType: 'json',
    		success: function(response){
    			alert("保存成功");
    			location.href = location.href + "&tabindex=2";
    		},
    		error: function(request, status, error){
    			alert("保存失败");
    		}
    	});
    }
    function ajaxUpload3DFile(type) {
    	var domFrom = $("#houseForm").get(0);
    	var id = parseInt(domFrom.id.value);
    	var fileElementId = type==0?'3dthumnail':'3dorigin';
    	//starting setting some animation when the ajax starts and completes
        $("#3dloading").ajaxStart(function(){
            $(this).show();
        }).ajaxComplete(function(){
            $(this).hide();
        });
    	$.ajaxFileUpload({
    	    type:'POST',
    		url: "${ctx}/upload/houseimg/?houseId="+id+"&type=3",
    		secureuri: false,
    		fileElementId: fileElementId,
    		dataType: "json",
    		success: function(data, status){
    			$("#"+fileElementId).val("");
    			$("#preview"+(type==0?3:4)).attr("src",data.files[0]);
    		},
    		error: function(data, status, error){
    			$("#"+fileElementId).val("");
    			alert("上传失败");
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
    	$("#coverImgFile").change(function(){
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
    	$("#postBtn2D").click(function(){
    		addOrUpdate2D();
    	});
    	$("#2dthumnail").change(function(){
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
    		ajaxUpload2DFile(0);
    	});
    	$("#2dorigin").change(function(){
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
    		ajaxUpload2DFile(1);
    	});
    	$("#postBtn3D").click(function(){
    		addOrUpdate3D();
    	});
    	$("#3dthumnail").change(function(){
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
    		ajaxUpload3DFile(0);
    	});
    	$("#3dorigin").change(function(){
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
    		ajaxUpload3DFile(1);
    	});
    });
    </script>
</@pangu>