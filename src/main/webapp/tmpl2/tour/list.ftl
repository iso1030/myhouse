<#include "../frame.ftl">
<#escape x as x?html>
<@pangu>
	<@navbar module="tour"/>
	<div class="container">
      <div class="row">
      	<div class="col-xs-6 col-md-3">
      	  <div class="well">
      	  	<div class="input-group">
      		  <input type="text" class="form-control" id="searchAddress" name="searchAddress" placeholder="Tour's address">
      		  <span class="input-group-btn">
        		<button class="btn btn-default" type="button" id="searchBtn"><span class="glyphicon glyphicon-search"></span></button>
      		  </span>
    		</div><!-- /input-group -->
      	  </div>
      	</div>
      	<div class="col-xs-12 col-sm-6 col-md-9">
      	  <ol class="breadcrumb">
  			<li><a href="/house">Tours</a></li>
  			<li class="active">List</li>
		  </ol><!-- /.breadcrumb -->
		  
		  <table class="table table-bordered table-hover">
		  	<thead>
		  	  <tr>
		  	    <th>Address</th>
		  	    <th>TourID</th>
		  	    <th>Customer</th>
		  	    <th>View</th>
		  	    <th>Create Date</th>
		  	    <th>Actions</th>
		  	  </tr>
		  	</thead>
		  	<tbody id="listBox">
		  	  <#assign tourList = page.content>
		  	  <#list tourList as item>
		  	  <tr>
		  	    <td>${item.address!""}</td>
		  	    <td>${item.id!""}</td>
		  	    <td><#if item.userProfile?has_content>${item.userProfile.realname!""}</#if></td>
		  	    <td>
		  	    	<a target="_blank" href="/vtour/${item.id}" class="glyphicon glyphicon-eye-open"></a>
		  	    	<a target="_blank" href="/download/${item.id}" class="glyphicon glyphicon-download"></a>
		  	    </td>
		  	    <td>${item.createTime?default(0)?number_to_date?string("yyyy-MM-dd")}</td>
		  	    <td>
		  	      <a href="/house/edit?houseId=${item.id}" class="btn btn-xs btn-info"><span class="glyphicon glyphicon-edit" title="Edit Tour"></span></a>
		  	      <button type="button" class="btn btn-xs btn-danger" data-id="${item.id}"><span class="glyphicon glyphicon-trash" title="Delete Tour"></span></button>
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
    <script src="${jsBase}/static/project/js/tourlist.js"></script>
</@pangu>
</#escape>