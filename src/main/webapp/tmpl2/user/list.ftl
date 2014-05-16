<#include "../frame.ftl">
<#escape x as x?html>
<@pangu>
	<@navbar module="user"/>
	<div class="container">
      <div class="row">
      	<div class="col-xs-6 col-md-3">
      	  <div class="well">
      	  	<a href="/user/edit"><span class="glyphicon glyphicon-plus"></span> Add a New Customer</a>
      	  </div>
      	  <div class="well">
      	  	<div class="input-group">
      		  <input type="text" class="form-control" id="searchPhone" name="searchPhone" placeholder="Phone or name">
      		  <span class="input-group-btn">
        		<button class="btn btn-default" type="button" id="searchBtn"><span class="glyphicon glyphicon-search"></span></button>
      		  </span>
    		</div><!-- /input-group -->
      	  </div>
      	</div>
      	<div class="col-xs-12 col-sm-6 col-md-9">
      	  <ol class="breadcrumb">
  			<li><a href="/user">Customers</a></li>
  			<li class="active">List</li>
		  </ol><!-- /.breadcrumb -->
		  
		  <table class="table table-bordered table-hover">
		  	<thead>
		  	  <tr>
		  	    <th>Customer Name</th>
		  	    <th>Email</th>
		  	    <th>Phone</th>
		  	    <th>Company</th>
		  	    <th>Create Date</th>
		  	    <th>Actions</th>
		  	  </tr>
		  	</thead>
		  	<tbody id="listBox">
		  	  <#assign userList = page.content>
		  	  <#list userList as item>
		  	  <tr>
		  	    <td>${item.realname!""}</td>
		  	    <td>${item.email!""}</td>
		  	    <td>${item.telephone!""}</td>
		  	    <td>${item.company!""}</td>
		  	    <td>${item.createTime?default(0)?number_to_date?string("yyyy-MM-dd")}</td>
		  	    <td>
		  	      <a href="/house/?userId=${item.id}" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-list-alt" title="List Tour"></span></a>
		  	      <a href="/house/add?userId=${item.id}" class="btn btn-xs btn-success"><span class="glyphicon glyphicon-plus" title="New Tour"></span></a>
		  	      <a href="/user/edit?userId=${item.id}" class="btn btn-xs btn-info"><span class="glyphicon glyphicon-edit" title="Edit Customer"></span></a>
		  	      <button type="button" class="btn btn-xs btn-danger" data-id="${item.id}"><span class="glyphicon glyphicon-trash" title="Delete Customer"></span></button>
		  	    </td>
		  	  </tr>
		  	  </#list>
		  	</tbody>
		  </table><!-- /.table -->
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
      	</div>
      </div>
    </div><!-- /.container -->
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${jsBase}/static/jquery/1.9.1/jquery.min.js"></script>
    <script src="${jsBase}/static/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <script src="${jsBase}/static/project/js/userlist.js"></script>
</@pangu>
</#escape>