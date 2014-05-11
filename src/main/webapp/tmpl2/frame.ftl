<#assign ctx = "">
<#assign host = "">
<#assign jsBase = "">
<#assign cssBase = "">

<#macro pangu>
<!DOCTYPE html>
<html lang="">
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<#--
	<link rel="shortcut icon" href="../../assets/ico/favicon.ico">
	-->
	
	<title>HouseTour</title>
	
	<!-- Bootstrap core CSS -->
	<link href="${cssBase}/static/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Custom styles for this template -->
	<link href="${cssBase}/static/project/css/template.css" rel="stylesheet">
	
	<!-- Just for debugging purposes. Don't actually copy this line! -->
	<!--[if lt IE 9]>
	  <script src="../../assets/js/ie8-responsive-file-warning.js"></script>
	<![endif]-->
	
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	  <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	</head>
	<body>
		<#nested>
	</body>
</#macro>

<#macro navbar module="user">
<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">HouseTour</a>
    </div>
    <div class="collapse navbar-collapse">
      <ul class="nav navbar-nav">
        <li<#if module=="user"> class="active"</#if>><a href="/user">Customers</a></li>
        <li<#if module=="tour"> class="active"</#if>><a href="/house">Tours</a></li>
        <li<#if module=="banner"> class="active"</#if>><a href="/banner">Banners</a></li>
        <li<#if module=="recommend"> class="active"</#if>><a href="/random">Recommend</a></li>
      </ul>
    </div><!-- /.nav-collapse -->
  </div><!-- /.container -->
</div><!-- /.navbar -->
</html>
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