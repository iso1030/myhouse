<#include "/tmpl/common/frame.ftl">
<#escape x as x?html>
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
	<@topbar module="user"/>

    <div class="container-fluid">
      <div class=""><a href="${ctx}/user/edit">添加用户</a></div>
      <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead><tr><th>ID</th><th>电话</th><th>姓名</th><th>Email</th><th>公司</th><th>操作</th></tr></thead>
        <tbody>
            <#assign userList = page.content>
            <#list userList as item>
            <tr>
            	<td>${item.id}</td>
            	<td>${item.telephone!""}</td>
                <td><a href="${ctx}/user/edit?userId=${item.id}">${item.realname!""}</a></td>
                <td>${item.email!""}</td>
                <td>${item.company!""}</td>
                <td>
                	<button type="button" class="btn btn-mini btn-primary" onclick="editUser(${item.id})">编辑</button>
                	<button type="button" class="btn btn-mini" onclick="delUser(${item.id})">删除</button>
                </td>
            </tr>
            </#list>
        </tbody>
      </table>
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
    var editUser = function(uid){
    	location.href = "/user/edit?userId=" + uid;
    }
    var delUser = function(uid){
    	$.ajax({
    	    type: 'DELETE',
    		url: '${ctx}/api/v1/user/' + uid,
    		success: function(response){
    			location.reload()
    		},
    		error: function(request, status, error){
    			alert('用户删除失败！');
    		}
    	});
    };
    </script>
</@pangu>
</#escape>