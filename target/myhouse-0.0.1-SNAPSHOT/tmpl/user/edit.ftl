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
	<@topbar module="user"/>

    <div class="container-fluid">
    <#if !user?exists><#assign user = {}></#if>
    <form class="form-horizontal" onsubmit="return false;" id="userForm" method="POST" enctype="multipart/form-data" action="">
      <iframe style="display:none;" name="avatar_frame" id="avatar_frame" src="${ctx}/crossdomain.html"></iframe>
      <input type="hidden" name="id" value="${user.id?default(0)}"/>
      <input type="hidden" name="nickname" value="${user.nickname?default("")}"/>
      <input type="hidden" id="avatar" name="avatar" value="${user.avatar?default("")}"/>
	  <div class="control-group">
	    <label class="control-label" for="telephone">Telephone</label>
	    <div class="controls">
	      <input type="text" id="telephone" name="telephone" value="${user.telephone?default("")}">
	    </div>
	  </div>
	  <div class="control-group">
	    <label class="control-label" for="realname">Realname</label>
	    <div class="controls">
	      <input type="text" id="realname" name="realname" value="${user.realname?default("")}" placeholder="Realname">
	    </div>
	  </div>
	  <div class="control-group">
	    <label class="control-label" for="email">Email</label>
	    <div class="controls">
	      <input type="text" id="email" name="email" value="${user.email?default("")}" placeholder="Email">
	    </div>
	  </div>
	  <div class="control-group">
	    <label class="control-label" for="company">Company</label>
	    <div class="controls">
	      <input type="text" id="company" name="company" value="${user.company?default("")}" placeholder="Company">
	    </div>
	  </div>
	  <div class="control-group">
	    <label class="control-label" for="avatar">Avatar</label>
	    <div class="controls">
	      <img class="preview" id="preview"<#if user.avatar?has_content> src="${user.avatar}"</#if>/>
	      <input type="file" id="fileupload" name="fileupload" value="">
	      <span id="loading" style="display:none;">上传中...</span>
	    </div>
	  </div>
	  <div class="control-group">
	    <div class="controls">
	      <button class="btn" id="postBtn">保存</button>
	    </div>
	  </div>
	</form>
    </div><!--/.fluid-container-->
    <script>
    function addOrUpdate() {
    	var domFrom = $("#userForm").get(0);
    	var id = parseInt(domFrom.id.value);
    	$.ajax({
    	    type: 'POST',
    		url: id>0?'${ctx}/api/v1/user/update.v':'${ctx}/api/v1/user/create1.v',
    		data: {
    			id: domFrom.id.value,
    			nickname: domFrom.nickname.value, 
    			telephone: domFrom.telephone.value,
    			realname: domFrom.realname.value,
    			email: domFrom.email.value,
    			avatar: domFrom.avatar.value,
    			company: domFrom.company.value
    		},
    		dataType: 'json',
    		success: function(response){
    			alert("保存成功");
    		},
    		error: function(request, status, error){
    			alert("保存成功");
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
    		url: "${ctx}/upload/?type=avatar",
    		secureuri: false,
    		fileElementId: "fileupload",
    		dataType: "json",
    		success: function(data, status){
    			$("#fileupload").val("");
    			$("#preview").attr("src",data.files[0]);
    			$("#avatar").attr("value",data.files[0]);
    		},
    		error: function(data, status, error){
    			$("#fileupload").val("");
    			console.log(data, status, error);
    		}
    	});
    }
    $(document).ready(function(){
    	$("#postBtn").click(function(){
    		addOrUpdate();
    	});
    	$("#fileupload").change(function(){
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