<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员异动结果表管理</title>
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
		<li class="active"><a href="${ctx}/check/checkChangeResult/">人员异动结果表列表</a></li>
		<shiro:hasPermission name="check:checkChangeResult:edit"><li><a href="${ctx}/check/checkChangeResult/form">人员异动结果表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="checkChangeResult" action="${ctx}/check/checkChangeResult/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="check:checkChangeResult:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="checkChangeResult">
			<tr>
				<td><a href="${ctx}/check/checkChangeResult/form?id=${checkChangeResult.id}">
					${checkChangeResult.updateDate}
				</a></td>
				<shiro:hasPermission name="check:checkChangeResult:edit">
					<td>
    					<a href="${ctx}/check/checkChangeResult/form?id=${checkChangeResult.id}">修改</a>
						<a href="${ctx}/check/checkChangeResult/delete?id=${checkChangeResult.id}" onclick="return confirmx('确认要删除该人员异动结果表吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>