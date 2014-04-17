<#assign ctx = "">

<#macro pangu>
<!doctype html>
<html>
<head>
<title>My House</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/bootstrap/2.3.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/ui-lightness/jquery-ui-1.9.2.custom.min.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ajaxfileupload.js" type="text/javascript"></script>
</head>
<body>
	<#nested>
</body>
</html>
</#macro>

<#macro topbar module="">
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container-fluid">
      <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="brand" href="${ctx}/house">MyHouse</a>
      <div class="nav-collapse collapse">
        <p class="navbar-text pull-right">
          Logged in as <a href="#" class="navbar-link">Username</a>
        </p>
        <ul class="nav">
          <li<#if module=="user"> class="active"</#if>><a href="${ctx}/user">客户管理</a></li>
          <li<#if module=="house"> class="active"</#if>><a href="${ctx}/house">房屋管理</a></li>
          <li<#if module=="banner"> class="active"</#if>><a href="${ctx}/banner">Banner管理</a></li>
        </ul>
      </div><!--/.nav-collapse -->
    </div>
  </div>
</div>
</#macro>

<#function max itema itemb>
	<#if itema gte itemb>
		<#return itema>
	<#else>
		<#return itemb>
	</#if>
</#function>

<#function min itema itemb>
	<#if itema lte itemb>
		<#return itema>
	<#else>
		<#return itemb>
	</#if>
</#function>
