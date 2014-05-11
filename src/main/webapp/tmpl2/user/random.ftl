<#include "../frame.ftl">
<#escape x as x?html>
<@pangu>
	<@navbar module="recommend"/>
	<div class="container">
      <div class="row">
      	<div class="col-xs-6 col-md-3">
      	  <div class="well">
      	  	<div class="input-group">
      		  <input type="text" class="form-control" id="searchInput" name="searchInput" placeholder="请输入页码">
      		  <span class="input-group-btn">
        		<button class="btn btn-default" type="button" id="searchBtn"><span class="glyphicon glyphicon-search"></span></button>
      		  </span>
    		</div><!-- /input-group -->
      	  </div>
      	  <div class="well">
      	  	<form class="form-horizontal" id="randomForm" onsubmit="return false;" id="userForm" method="POST" enctype="multipart/form-data" role="form">
      	  	<div class="form-group">
			    <div class="col-sm-12">
			      <input type="text" class="form-control" id="pageIndex" name="pageIndex" value="" placeholder="页码">
			    </div>
			</div>
      	  	<div class="form-group">
			    <div class="col-sm-12">
			      <input type="text" class="form-control" id="pagePos" name="pagePos" value="" placeholder="页位置">
			    </div>
			</div>
      	  	<div class="form-group">
			    <div class="col-sm-12">
			      <input type="text" class="form-control" id="userId" name="userId" value="" placeholder="用户ID">
			    </div>
			</div>
			<div class="form-group">
			    <div class="col-sm-12">
			      <button type="button" class="btn btn-default" id="saveBtn">Save</button>
			    </div>
			</div>
    		</form><!-- /input-group -->
      	  </div>
      	</div>
      	<div class="col-xs-12 col-sm-6 col-md-9">
      	  <ol class="breadcrumb">
  			<li><a href="/random">Customer Recommend</a></li>
  			<li class="active">List</li>
		  </ol><!-- /.breadcrumb -->
		  
		  <table class="table table-bordered table-hover">
		  	<thead>
		  	  <tr>
		  	    <th>页码</th>
		  	    <th>页位置</th>
		  	    <th>推荐用户</th>
		  	    <th>Actions</th>
		  	  </tr>
		  	</thead>
		  	<tbody id="listBox">
		  	  <#if page?exists>
		  	  <#assign list = page.content>
		  	  <#list list as item>
		  	  <tr>
		  	    <td>${item.pageIndex!""}</td>
		  	    <td>${item.pagePos!""}</td>
		  	    <td><#if item.userProfile?has_content>${item.userProfile.realname!""}</#if></td>
		  	    <td>
		  	      <button type="button" class="btn btn-xs btn-danger" data-id="${item.id}"><span class="glyphicon glyphicon-trash" title="Delete"></span></button>
		  	    </td>
		  	  </tr>
		  	  </#list>
		  	  </#if>
		  	</tbody>
		  </table><!-- /.table -->
		  <#if page?exists>
		  <#assign pageSize = 10
	           current = page.getNumber() + 1
	           begin = max(1, current - pageSize / 2)
	           end = min(begin + (pageSize - 1), page.getTotalPages())
	      />
		  <ul class="pagination">
		    <#if page.hasPreviousPage()>
	    	<li><a href="?page=1">&lt;&lt;</a></li>
            <li><a href="?page=${current-1}">&lt;</a></li>
		    <#else>
  			<li class="disabled"><a href="javascript:;">&lt;&lt;</a></li>
  			<li class="disabled"><a href="javascript:;">&lt;</a></li>
  			</#if>
  			
  			<#list begin..end as i>
		  	<li<#if i==current> class="active"</#if>><a href="?page=${i}">${i}</a></li>
		  	</#list>
		  	
		  	<#if page.hasNextPage()>
            <li><a href="?page=${current+1}">&gt;</a></li>
            <li><a href="?page=${page.totalPages}">&gt;&gt;</a></li>
		  	<#else>
		  	<li class="disabled"><a href="javascript:;">&gt;&gt;</a></li>
		  	<li class="disabled"><a href="javascript:;">&gt;</a></li>
		  	</#if>
		  </ul><!-- pagination -->
		  </#if>
      	</div>
      </div>
    </div><!-- /.container -->
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${jsBase}/static/jquery/1.9.1/jquery.min.js"></script>
    <script src="${jsBase}/static/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <script src="${jsBase}/static/project/js/userrandom.js"></script>
</@pangu>
</#escape>