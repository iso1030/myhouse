<#include "/tmpl/common/frame.ftl">
<@pangu>
	<style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }
      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>
	<div class="container">
      <form id="loginForm" action="${ctx}/login" method="post" class="form-signin">
        <!--<h2 class="form-signin-heading">Please sign in</h2>-->
        <#if Request?? && Request['shiroLoginFailure']?has_content>
      	<div class="alert alert-error input-medium controls">
		  <button class="close" data-dismiss="alert">×</button>登录失败，请重试.
	 	</div>
	 	</#if>
        <input type="text" id="username" name="username" value="${username!""}" class="input-block-level required" placeholder="Email address">
        <input type="password" id="password" name="password" class="input-block-level required" placeholder="Password">
        <label class="checkbox">
          <input type="checkbox" id="rememberMe" name="rememberMe"> Remember me
        </label>
        <button id="submit_btn" class="btn btn-large btn-primary" type="submit">Sign in</button>
      </form>
    </div>
	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
		});
	</script>
</@pangu>