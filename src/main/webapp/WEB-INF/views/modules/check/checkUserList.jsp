<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>员工信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","/api/employeeExport");
                        $("#searchForm").submit();
                        $("#searchForm").attr("action","${ctx}/check/checkUser/checkUserList");
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        //图片上传
        var xhr;
        //上传文件方法
        function UpladFile() {
            var fileObj = document.getElementById("file").files[0]; // js 获取文件对象
            var url =  "http://localhost:8080" + "/api/employeeImport"; // 接收上传文件的后台地址

            var form = new FormData(); // FormData 对象
            form.append("file", fileObj); // 文件对象

            xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
            xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
            xhr.onload = uploadComplete; //请求完成
            xhr.onerror =  uploadFailed; //请求失败

            xhr.upload.onprogress = progressFunction;//【上传进度调用方法实现】
            xhr.upload.onloadstart = function(){//上传开始执行方法
                ot = new Date().getTime();   //设置上传开始时间
                oloaded = 0;//设置上传开始时，以上传的文件大小为0
            };

            xhr.send(form); //开始上传，发送form数据
        }

        //上传成功响应
        function uploadComplete(evt) {
            //服务断接收完文件返回的结果

            var data = JSON.parse(evt.target.responseText);
            if(data.success) {
                alert("上传成功！");
            }else{
                alert("上传失败！");
            }

        }
        //上传失败
        function uploadFailed(evt) {
            alert("上传失败！");
        }
        //取消上传
        function cancleUploadFile(){
            xhr.abort();
        }


        // 上传进度实现方法，上传过程中会频繁调用该方法
        function progressFunction(evt) {
            var progressBar = document.getElementById("progressBar");
            var percentageDiv = document.getElementById("percentage");
            // event.total是需要传输的总字节，event.loaded是已经传输的字节。如果event.lengthComputable不为真，则event.total等于0
            if (evt.lengthComputable) {//
                progressBar.max = evt.total;
                progressBar.value = evt.loaded;
                percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
            }
            var time = document.getElementById("time");
            var nt = new Date().getTime();//获取当前时间
            var pertime = (nt-ot)/1000; //计算出上次调用该方法时到现在的时间差，单位为s
            ot = new Date().getTime(); //重新赋值时间，用于下次计算
            var perload = evt.loaded - oloaded; //计算该分段上传的文件大小，单位b
            oloaded = evt.loaded;//重新赋值已上传文件大小，用以下次计算
            //上传速度计算
            var speed = perload/pertime;//单位b/s
            var bspeed = speed;
            var units = 'b/s';//单位名称
            if(speed/1024>1){
                speed = speed/1024;
                units = 'k/s';
            }
            if(speed/1024>1){
                speed = speed/1024;
                units = 'M/s';
            }
            speed = speed.toFixed(1);
            //剩余时间
            var resttime = ((evt.total-evt.loaded)/bspeed).toFixed(1);
            time.innerHTML = '，速度：'+speed+units+'，剩余时间：'+resttime+'s';
            if(bspeed==0) time.innerHTML = '上传已取消';
        }
	</script>

</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/check/checkUser/">员工信息列表</a></li>
		<shiro:hasPermission name="check:checkUser:edit"><li><a href="${ctx}/check/checkUser/form">员工信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="checkUser" action="${ctx}/check/checkUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="clearfix"></li>
		</ul>

		<progress id="progressBar" value="0" max="100" style="width: 300px;"></progress>
		<span id="percentage"></span><span id="time"></span>
		<br /><br />
		<input type="file" id="file" name="myfile" />
		<input type="button" onclick="UpladFile()" value="上传" />
		<input type="button" onclick="cancleUploadFile()" value="取消" />

	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>姓名</th>
				<shiro:hasPermission name="check:checkUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="checkUser">
			<tr>
				<td><a href="${ctx}/check/checkUser/form?id=${checkUser.id}">
					${checkUser.updateDate}
				</a></td>
				<td>
					${checkUser.name}
				</td>
				<shiro:hasPermission name="check:checkUser:edit">
					<td>
    					<a href="${ctx}/check/checkUser/form?id=${checkUser.id}">修改</a>
						<a href="${ctx}/check/checkUser/delete?id=${checkUser.id}" onclick="return confirmx('确认要删除该员工信息吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>