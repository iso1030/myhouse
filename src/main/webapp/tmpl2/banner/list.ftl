<#include "../frame.ftl">
<#escape x as x?html>
<@pangu>
	<@navbar module="banner"/>
	<div class="container">
      <div class="row">
      	  <ol class="breadcrumb">
  			<li><a href="/banner">Banners</a></li>
  			<li class="active">List</li>
		  </ol><!-- /.breadcrumb -->
		  
		  <div class="well">
            <div class="form-group">
			  <input type="file" class="form-control" id="fileupload" name="fileupload" data-url="/upload?type=banner">
            </div><!-- /form-group -->
	      	<div class="progress" style="display:none;margin-top:10px;">
			  <div id="progress" class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
			  	<span class="sr-only">60% Complete</span>
			  </div>
		  	</div>
		  </div>
		  
		  <div class="well">
          <div class="row" id="sortable">
              <#assign blist = list>
              <#list blist as i>
              <div class="col-sm-6 col-md-3" id="banner${i.id}">
	  			<input type="hidden" value="${i.id}">
	  			<input type="hidden" id="createTime${i.id}" value="${i.createTime!0}">
                <div class="thumbnail">
                  <img data-id="${i.id}" data-src="${i.url}"
                    alt="" style="width:200px;height:300px;" <#if i.thumbnail?has_content>src="${i.thumbnail}"</#if>>
                  <div class="caption">
                  	<p><input type="text" id="user${i.id}" value="${i.uid!0}" placeholder="关联用户ID"/></p>
                    <p>上传时间：${i.createTime?default(0)?number_to_date?string("yyyy-MM-dd")}</p>
                    <p><button type="button" id="delBtn${i.id}" data-id="${i.id}" class="btn btn-primary btn-xs">Delete</button> <a href="${i.url}" class="btn btn-default btn-xs" target="_blank">Origin</a></p>
                  </div>
                </div>
              </div>
              </#list>
          </div>
		  <br/>
		  <div class="row" <#if !blist?exists || blist?size lt 1>style="display:none;"</#if>>
			  <button type="button" class="btn btn-default" id="saveBtn">Save</button>
		  </div>
          </div>
      </div>
    </div><!-- /.container -->
	<script id="template" type="x-tmpl-mustache">
	  <div class="col-sm-6 col-md-3" id="banner{{id}}">
	  	<input type="hidden" value="{{id}}">
	  	<input type="hidden" id="createTime{{id}}" value="0">
        <div class="thumbnail">
          <img data-id="{{id}}" data-src="{{url}}"
            alt="" style="width:200px;height:300px;" src="{{url}}">
          <div class="caption">
            <p><input type="text" id="user{{id}}" value="0" placeholder="关联用户ID"/></p>
            <p>上传时间：{{createTime}}</p>
            <p><button type="button" id="delBtn{{id}}" data-id="{{id}}" class="btn btn-primary btn-xs">Delete</button> <a href="{{url}}" class="btn btn-default btn-xs" target="_blank">Origin</a></p>
          </div>
        </div>
      </div>
	</script>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${jsBase}/static/jquery/1.9.1/jquery.min.js"></script>
	<script src="${jsBase}/static/jquery/jquery-ui-1.9.2.custom.min.js"></script>
    <script src="${jsBase}/static/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <script src="${jsBase}/static/jquery-fileupload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${jsBase}/static/jquery-fileupload/js/jquery.iframe-transport.js"></script>
    <script src="${jsBase}/static/jquery-fileupload/js/jquery.fileupload.js"></script>
    <script src="${jsBase}/static/mustache/mustache.js"></script>
    <script src="${jsBase}/static/project/js/banner.js"></script>
</@pangu>
</#escape>