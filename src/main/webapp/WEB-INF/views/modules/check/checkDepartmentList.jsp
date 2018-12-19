<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>部门信息管理</title>
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
		<li class="active"><a href="${ctx}/check/checkDepartment/">部门信息列表</a></li>
		<shiro:hasPermission name="check:checkDepartment:edit"><li><a href="${ctx}/check/checkDepartment/form">部门信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="checkDepartment" action="${ctx}/check/checkDepartment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>部门名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>部门名称</th>
				<shiro:hasPermission name="check:checkDepartment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="checkDepartment">
			<tr>
				<td><a href="${ctx}/check/checkDepartment/form?id=${checkDepartment.id}">
					${checkDepartment.updateDate}
				</a></td>
				<td>
					${checkDepartment.name}
				</td>
				<shiro:hasPermission name="check:checkDepartment:edit">
					<td>
    					<a href="${ctx}/check/checkDepartment/form?id=${checkDepartment.id}">修改</a>
						<a href="${ctx}/check/checkDepartment/delete?id=${checkDepartment.id}" onclick="return confirmx('确认要删除该部门信息吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>