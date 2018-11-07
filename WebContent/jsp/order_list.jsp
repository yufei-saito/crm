<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>订单列表</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>

		<%@include file="/jsp/top.jsp" %>	
		

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
						<tbody>
							<c:forEach  items="${page.list}" var="order" >
								<tr class="success">
									<th colspan="5">订单编号:${order.oid}  总金额:${order.total}&nbsp;
									订单状态:
									<c:if test="${order.state==1}">
										未支付 &nbsp;<a href="${pageContext.request.contextPath}/OrderServlet?method=showOneOrder&id=${order.oid}">支付</a>
									</c:if>
									<c:if test="${order.state==2}">
										等待发货
									</c:if>
									<c:if test="${order.state==3}">
										等待收货 &nbsp;<a href="">收货</a>
									</c:if>
									<c:if test="${order.state==4}">
										订单完成
									</c:if>
									 </th>
								</tr>
								<tr class="warning">
									<th>图片</th>
									<th>商品</th>
									<th>价格</th>
									<th>数量</th>
									<th>小计</th>
								</tr>
								<c:forEach items="${order.list}" var="oi">
									<tr class="active">
										<td width="60" width="40%">
											<input type="hidden" name="id" value="22">
											<img src="${pageContext.request.contextPath}/${oi.p.pimage}" width="70" height="60">
										</td>
										<td width="30%">
											<a target="_blank">${oi.p.pname}</a>
										</td>
										<td width="20%">
											￥${oi.p.shop_price}
										</td>
										<td width="10%">
											${oi.quantity}
										</td>
										<td width="15%">
											<span class="subtotal">￥${oi.total}</span>
										</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
						
						
					</table>
				</div>
			</div>
			<%@ include file="/jsp/pageFile.jsp" %>
		</div>

		

		<%@include file="/jsp/floor.jsp" %>
	</body>

</html>