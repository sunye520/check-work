<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>审核人员配置表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/check/checkApproverDeploy/">审核人员配置表列表</a></li>
		<li class="active"><a href="${ctx}/check/checkApproverDeploy/form?id=${checkApproverDeploy.id}">审核人员配置表<shiro:hasPermission name="check:checkApproverDeploy:edit">${not empty checkApproverDeploy.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="check:checkApproverDeploy:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="checkApproverDeploy" action="${ctx}/check/checkApproverDeploy/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">类型（1-事假，2-病假，3-婚假，4-产假，5-陪产假，6-丧假，7-年假，8-出差，9-外出，10-补卡）：</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门id：</label>
			<div class="controls">
				<form:input path="departmentId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核人id（用 | 分割）：</label>
			<div class="controls">
				<form:input path="approverId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核状态（1-审核成功，2-审核中，3审核失败）：</label>
			<div class="controls">
				<form:input path="approverType" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">抄送人id（用 | 分割）：</label>
			<div class="controls">
				<form:input path="ccId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假时间：</label>
			<div class="controls">
				<form:input path="time" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="check:checkApproverDeploy:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>