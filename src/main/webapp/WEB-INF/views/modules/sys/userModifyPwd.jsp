<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
				},
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
		<li><a href="${ctx}/sys/user/info">个人信息</a></li>
		<li class="active"><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	<%--<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modifyPwd" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" value="" maxlength="50" minlength="3" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" class="required" equalTo="#newPassword"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>--%>


	<form action="/api/downloadAdjustingExcel"method="post" enctype="multipart/form-data" id="form">
		<table>
			<tbody >
			<tr >
				<td>下载文件:</td>
				<td style="padding-left: 80px;">
					<button type="submit" class="btn btn-primary btn-q btn-outline fa fa-upload" class="easyui-validatebox" data-options="required:true">下载</button>
				</td>
			</tr>
			　　		</tbody>
		</table>
	</form>




		<%--<form action="/api/test"method="post" enctype="multipart/form-data" id="form">--%>
            <%--<table>--%>
                <%--<tbody >--%>
                    <%--<tr >--%>
                        <%--<td>上传文件:</td>--%>
                            <%--<td style="padding-left: 10px;">--%>
                                <%--<input type="file" name="file" id="fileInput">--%>

                            <%--</td>--%>
                        <%--<td style="padding-left: 80px;">--%>
                        <%--<button type="submit" class="btn btn-primary btn-q btn-outline fa fa-upload" class="easyui-validatebox" data-options="required:true">上传</button>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
        <%--　　		</tbody>--%>
            <%--</table>--%>
        <%--</form>--%>

</body>
</html>