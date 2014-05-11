<#include "../frame.ftl">
<#escape x as x?html>
<@pangu>
	<@navbar module="user"/>
	<div class="container">
      <div class="row">
      	<div class="col-xs-6 col-md-3">
      	  <div class="well">
      	  	<div class="input-group">
      		  <input type="text" class="form-control" id="searchPhone" name="searchPhone" placeholder="Customers' phone">
      		  <span class="input-group-btn">
        		<button class="btn btn-default" type="button" id="searchBtn"><span class="glyphicon glyphicon-search"></span></button>
      		  </span>
    		</div><!-- /input-group -->
      	  </div>
      	</div>
      	<div class="col-xs-12 col-sm-6 col-md-9">
      	  <ol class="breadcrumb">
  			<li><a href="/user">Customers</a></li>
  			<li class="active">Edit</li>
		  </ol><!-- /.breadcrumb -->
		  
    	  <#if !user?exists><#assign user = {}></#if>
		  <form class="form-horizontal" onsubmit="return false;" id="userForm" method="POST" enctype="multipart/form-data" role="form">
		      <input type="hidden" name="id" value="${user.id?default(0)}"/>
		      <input type="hidden" name="nickname" value="${user.nickname?default("")}"/>
		      <input type="hidden" id="avatar" name="avatar" value="${user.avatar?default("")}"/>
			  <div class="form-group">
			    <label for="telephone" class="col-sm-2 control-label">Telephone</label>
			    <div class="col-sm-8">
			      <input type="text" class="form-control" id="telephone" name="telephone" value="${user.telephone?default("")}" placeholder="">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="realname" class="col-sm-2 control-label">Realname</label>
			    <div class="col-sm-8">
			      <input type="text" class="form-control" id="realname" name="realname" value="${user.realname?default("")}" placeholder="">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="email" class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-8">
			      <input type="email" class="form-control" id="email" name="email" value="${user.email?default("")}" placeholder="">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="company" class="col-sm-2 control-label">Company</label>
			    <div class="col-sm-8">
			      <input type="text" class="form-control" id="company" name="company" value="${user.company?default("")}" placeholder="">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="fileupload" class="col-sm-2 control-label">Avatar</label>
			    <div class="col-sm-8">
			      <input type="file" class="" id="fileupload" name="fileupload" data-url="/upload/avatar">
			      <div class="progress" style="display:none;margin-top:10px;">
					<div id="progress" class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
					  <span class="sr-only">60% Complete</span>
					</div>
				  </div>
			      <p class="help-block"><img id="preview"<#if user.avatar?has_content> src="${user.avatar}"</#if> style="width:180px;height:180px;"/></p>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-8">
			      <button type="button" class="btn btn-default" id="saveBtn">Save</button>
			    </div>
			  </div>
		  </form><!-- /.form -->
		  
      	</div>
      </div>
    </div><!-- /.container -->
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${jsBase}/static/jquery/1.9.1/jquery.min.js"></script>
    <script src="${jsBase}/static/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <script src="${jsBase}/static/jquery-fileupload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${jsBase}/static/jquery-fileupload/js/jquery.iframe-transport.js"></script>
    <script src="${jsBase}/static/jquery-fileupload/js/jquery.fileupload.js"></script>
    <script src="${jsBase}/static/project/js/useredit.js"></script>
</@pangu>
</#escape>