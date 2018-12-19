<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>打卡记录信息管理</title>
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
		<li class="active"><a href="${ctx}/check/checkPunchCard/">打卡记录信息列表</a></li>
		<shiro:hasPermission name="check:checkPunchCard:edit"><li><a href="${ctx}/check/checkPunchCard/form">打卡记录信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="checkPunchCard" action="${ctx}/check/checkPunchCard/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>姓名</th>
				<shiro:hasPermission name="check:checkPunchCard:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="checkPunchCard">
			<tr>
				<td><a href="${ctx}/check/checkPunchCard/form?id=${checkPunchCard.id}">
					${checkPunchCard.updateDate}
				</a></td>
				<td>
					${checkPunchCard.name}
				</td>
				<shiro:hasPermission name="check:checkPunchCard:edit">
					<td>
    					<a href="${ctx}/check/checkPunchCard/form?id=${checkPunchCard.id}">修改</a>
						<a href="${ctx}/check/checkPunchCard/delete?id=${checkPunchCard.id}" onclick="return confirmx('确认要删除该打卡记录信息吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>