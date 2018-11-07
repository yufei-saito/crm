<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/Style1.css"
	rel="stylesheet" type="text/css" />
<script language="javascript"
	src="${pageContext.request.contextPath}/js/public.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	function showDetail(oid) {
		var $val = $("#but" + oid).val();
		if ($val == "订单详情") {
			// ajax 显示图片,名称,单价,数量
			$("#div" + oid)
					.append(
							"<img width='60' height='65' src='${pageContext.request.contextPath}/products/1/c_0028.jpg'>&nbsp;xxxx&nbsp;998<br/>");

			$("#but" + oid).val("关闭");
		} else {
			$("#div" + oid).html("");
			$("#but" + oid).val("订单详情");
		}
	}
</script>
</HEAD>
<body>
	<br>
	<form id="Form1" name="Form1"
		action="${pageContext.request.contextPath}/user/list.jsp"
		method="post">
		<table cellSpacing="1" cellPadding="0" width="100%" align="center"
			bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3"><strong>订单列表</strong>
					</TD>
				</tr>

				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

								<td align="center" width="5%">序号</td>
								<td align="center" width="10%">订单编号</td>
								<td align="center" width="5%">订单金额</td>
								<td align="center" width="5%">收货人</td>
								<td align="center" width="5%">订单状态</td>
								<td align="center" width="70%">订单详情</td>
							</tr>
							<c:forEach items="${list}" var="o" varStatus="state">
								<tr onmouseover="this.style.backgroundColor = 'white'"
									onmouseout="this.style.backgroundColor = '#F5FAFE';">
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="5%">${state.count}</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="10%">${o.oid}</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="5%">${o.total}</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="5%">${o.name}</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="5%">
										<c:if test="${o.state==1}">未付款</c:if> 
										<c:if test="${o.state==2}">
											<a href="${pageContext.request.contextPath}/AdminServlet?method=updateState&oid=${o.oid}">发货</a>
										</c:if> 
										<c:if test="${o.state==3}">已发货</c:if> 
										<c:if test="${o.state==4}">订单完成</c:if></td>
									<td align="center" style="HEIGHT: 22px" width="70%"><input
										class="show" type="button" value="订单详情" id="${o.oid}" />
										<table border="1" width="100%">

										</table></td>

								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr align="center">
					<td colspan="7"></td>
				</tr>
			</TBODY>
		</table>
	</form>
</body>
<script type="text/javascript">
	$(function() {
		$(".show")
				.click(
						function() {
							var $table = $(this).next();
							$table.html("");
							var txt = this.value;
							if (txt == "订单详情") {
								$table.append("<tr><th>商品图片</th><th>商品名称</th><th>商城价</th><th>数量</th></tr>");
								var id = this.id;
								var url = "/GuguStore/AdminServlet";
								var obj = {"method" : "showOrderItems","oid" : id};
								$.post(url,obj,function(data) {$.each(data,function(i,obj) {
									$table.append("<tr><th><img src='/GuguStore/"+obj.p.pimage+"' width='50px'></th><th>"+ obj.p.pname+ "</th><th>"+ obj.p.shop_price+ "</th><th>"+ obj.quantity+ "</th></tr>");
								});}, "json");
								this.value="关闭订单";
							}else{
								this.value="订单详情";
								$table.html("");
							}
							
						});

	});
</script>
</HTML>

