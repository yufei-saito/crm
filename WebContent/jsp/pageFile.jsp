<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%--分页显示的开始 --%>
    	<div style="text-align:center">
    		共${page.maxPage}页/第${page.pageNum}页
    		
    		<a href="${pageContext.request.contextPath}/${page.url}&pageNum=1">首页</a>
    		<c:if test="${page.pageNum==1}">
    			上一页
    		</c:if>
    		<c:if test="${page.pageNum>1}">
    		<a href="${pageContext.request.contextPath}/${page.url}&pageNum=${page.pageNum-1}">上一页</a>
    		</c:if>
    		<%--显示的页码，使用forEach遍历显示的页面 --%>
    		<c:forEach begin="${page.startPage}" end="${page.endPage}" var="p">
    			<a href="${pageContext.request.contextPath}/${page.url}&pageNum=${p}">${p}</a>
    		</c:forEach>
    		<c:if test="${page.pageNum==page.maxPage}">
    			下一页
    		</c:if>
    		<c:if test="${page.pageNum<page.maxPage}">
    		<a href="${pageContext.request.contextPath}/${page.url}&pageNum=${page.pageNum+1}">下一页</a>
    		</c:if>
    		<a href="${pageContext.request.contextPath}/${page.url}&pageNum=${page.maxPage}">末页</a>
    		<input type="text" id="pagenum" name="pagenum" size="1"/><input type="button" value="前往" onclick="jump()" />
    		<script type="text/javascript">
    			function jump(){
    				var totalpage = ${page.maxPage};
    				var pagenum = document.getElementById("pagenum").value;
    				//判断输入的是一个数字
    				var reg =/^[1-9][0-9]{0,1}$/;
    				if(!reg.test(pagenum)){
    					//不是一个有效数字
    					alert("请输入符合规定的数字");
    					return ;
    				}
    				//判断输入的数字不能大于总页数
    				if(parseInt(pagenum)>parseInt(maxPage)){
    					//超过了总页数
    					alert("不能大于总页数");
    					return;
    				}
    				//转向分页显示的Servlet
    				window.location.href="${pageContext.request.contextPath}/${page.url}&pageNum="+pagenum;
    			}
    		</script>
    	</div>
    	<%--分页显示的结束--%>
