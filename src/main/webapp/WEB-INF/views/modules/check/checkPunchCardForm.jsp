<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>打卡记录信息管理</title>
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
		<li><a href="${ctx}/check/checkPunchCard/">打卡记录信息列表</a></li>
		<li class="active"><a href="${ctx}/check/checkPunchCard/form?id=${checkPunchCard.id}">打卡记录信息<shiro:hasPermission name="check:checkPunchCard:edit">${not empty checkPunchCard.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="check:checkPunchCard:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="checkPunchCard" action="${ctx}/check/checkPunchCard/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工号：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打卡日期：</label>
			<div class="controls">
				<form:input path="punchDate" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上午打卡时间：</label>
			<div class="controls">
				<form:input path="shangCellTime" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">迟到早退标识(0:正常打卡  1：迟到 )：</label>
			<div class="controls">
				<form:input path="shangChi" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">迟到分钟数(单位分钟)：</label>
			<div class="controls">
				<form:input path="shangChiTime" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">迟到中文写法：</label>
			<div class="controls">
				<form:input path="shangChiTuiName" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上午请假标识(0：未请假  1：已请假）：</label>
			<div class="controls">
				<form:input path="shangAskLeave" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假时间(单位小时）：</label>
			<div class="controls">
				<form:input path="shangAskLeaveTime" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下班打卡时间：</label>
			<div class="controls">
				<form:input path="xiaCellTime" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">早退标识(0:正常打卡 1：早退)：</label>
			<div class="controls">
				<form:input path="xiaZao" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">早退分钟数(单位分钟)：</label>
			<div class="controls">
				<form:input path="xiaZaoTime" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">早退中文写法：</label>
			<div class="controls">
				<form:input path="xiaZaoTuiName" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下午请假标识(0：未请假  1：已请假）：</label>
			<div class="controls">
				<form:input path="xiaAskLeave" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假时间(单位小时）：</label>
			<div class="controls">
				<form:input path="xiaAskLeaveTime" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="check:checkPunchCard:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>