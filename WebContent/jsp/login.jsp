<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

.container .row div {
	/* position:relative;
	 float:left; */
	
}

font {
	color: #666;
	font-size: 22px;
	font-weight: normal;
	padding-right: 17px;
}
</style>
<script type="text/javascript">
	$(function() {
		$("input[type='submit']").click(function() {
			$("#nameInfo").html("");
			$("#pwdInfo").html("");
			var flag;
			if ($("#username").val() == "") {
				$("#nameInfo").html("<font>用户名不能为空</font>");
				flag = false;
			}
			if ($("#password").val() == "") {
				$("#pwdInfo").html("<font>密码不能为空</font>");
				flag = false;
			}
			if ($("#username").val() != "" && $("#password").val() != "") {
				flag = true;
			}
			return flag;
		});
	});
</script>
</head>
<body>




	<%@include file="/jsp/top.jsp"%>







	<div class="container"
		style="width:100%;height:460px;background:#FF2C4C url('${pageContext.request.contextPath}/img/loginbg.jpg') no-repeat;">
		<div class="row">
			<div class="col-md-7">
				<!--<img src="image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
			</div>

			<div class="col-md-5">
				<div
					style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
					<font>会员登录</font>USER LOGIN

					<div>&nbsp;</div>
					<form class="form-horizontal"
						action="${pageContext.request.contextPath}/UserServlet?method=login"
						method="post">

						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-6">
								<input type="text" class="form-control"
									value="${cookie.username_on.value}" } id="username"
									name="username" placeholder="请输入用户名"><span
									id="nameInfo"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="password"
									name="password" placeholder="请输入密码"><span id="pwdInfo"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="inputPassword3"
									placeholder="请输入验证码">
							</div>
							<div class="col-sm-3">
								<img src="${pageContext.request.contextPath}/img/captcha.jhtml" />
							</div>

						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div class="checkbox">
									<label> <input type="checkbox" name="auto_login"
										value="1"
										${not empty cookie.auto_login? "checked='checked'" :"" }>
										自动登录
									</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label> <input
										type="checkbox" name="username_on" value="1"
										${not empty cookie.username_on? "checked='checked'" :"" }>
										记住用户名
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" width="100" value="登录" name="submit"
									border="0"
									style="background: url('${pageContext.request.contextPath}/img/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
    height:35px;width:100px;color:white;">
							</div>
							<span>${msg}</span>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%@include file="/jsp/floor.jsp" %>
</body>
</html>