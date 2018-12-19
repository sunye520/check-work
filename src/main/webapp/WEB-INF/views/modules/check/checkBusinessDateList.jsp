<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>时间设置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/check/checkBusinessDate/">时间设置列表</a></li>
		<shiro:hasPermission name="check:checkBusinessDate:edit"><li><a href="${ctx}/check/checkBusinessDate/form">时间设置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="checkBusinessDate" action="${ctx}/check/checkBusinessDate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<shiro:hasPermission name="check:checkBusinessDate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="checkBusinessDate">
			<tr>
				<td><a href="${ctx}/check/checkBusinessDate/form?id=${checkBusinessDate.id}">
					${checkBusinessDate.updateDate}
				</a></td>
				<shiro:hasPermission name="check:checkBusinessDate:edit">
					<td>
    					<a href="${ctx}/check/checkBusinessDate/form?id=${checkBusinessDate.id}">修改</a>
						<a href="${ctx}/check/checkBusinessDate/delete?id=${checkBusinessDate.id}" onclick="return confirmx('确认要删除该时间设置吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>