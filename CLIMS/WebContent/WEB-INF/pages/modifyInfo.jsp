<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<link href='${pageContext.request.contextPath }/statics/localcss/modifyInfo.css' rel='stylesheet'>

<!-- 这里编辑每个页面的导航条  starts -->
<div>
	<ul class="breadcrumb">
		<li>导航：<a href="main.html">主页</a> <span class="divider">/</span></li>
		<li>完善个人信息</li>
	</ul>
</div>
<!-- 这里编辑每个页面的导航条  ends -->


<div class="row-fluid sortable">
	<div class="box span12">
	<form action="modifyInfoSave.html" class="form-horizontal" method="post" onsubmit="return modifyInfoFunction();">
		<div class="box-header well" data-original-title>
			<h2><i class="icon-edit"></i> 完善个人信息</h2>
			<div class="box-icon">
				<span style="color:red;font-size:12px;">以下信息均需如实填写</span>
			</div>
		</div>
		
		
		
		
		<div class="box-content">
			
					<input type="hidden" name="id" value="${user.id }">
					
					<div class="control-group">
						<label class="control-label">登录账号</label>
						<div class="controls">
							<span class="input-xlarge uneditable-input unInput">${user.userCode }</span>
						</div>
					</div>
					
					
					
					<div class="control-group">
						<label class="control-label">姓名</label>
						<div class="controls">
							<input type="text" id="userName" value="${user.userName }" name="userName" required="required">
							<span class="t_userName" id="t_userName">*不能为空、不能超过20个字符</span>
						</div>
					</div>
					
					
					<div class="control-group">
						<label class="control-label">性别</label>
						<div class="controls">
							<select id="selectError3" class="gender" name="gender">
								<option value=""> 请选择</option>
								<option <c:if test='${user.userRole == "1" }'>selected="selected"</c:if> value="1">男</option>                                             
								<option <c:if test='${user.userRole == "2" }'>selected="selected"</c:if> value="2">女</option>
							</select>
						</div>
					</div>
					
					
					<div class="control-group">
						<label class="control-label">所在单位</label>
						<div class="controls">
							<select id="selectError" class="dept" data-rel="chosen" name="dept">
								<option value=""> 请选择</option>
								<c:if test="${deptList != null}">
						  			<c:forEach items="${deptList }" var="d">
						  				<option <c:if test="${user.dept == d.deptName }">selected="selected"</c:if> 
						  					value="${d.deptName}">${d.deptName}</option>
						  			</c:forEach>
						  		</c:if>
							</select>
						</div>
					</div>
					
					
					<div class="control-group">
						<label class="control-label">学号</label>
						<div class="controls">
							<input type="text" id="number" value="${user.number }" name="number" required="required">
							<span class="t_number">*不能为空、长度为11位</span>
						</div>
					</div>
					
					
					<div class="control-group">
						<label class="control-label">邮箱号</label>
						<div class="controls">
							<input type="text" id="email" value="${user.email }" name="email" required="required">
							<span class="t_email">*不能为空、必须是合法邮箱</span>
						</div>
					</div>
					
					
					<div class="control-group">
						<label class="control-label">手机号码</label>
						<div class="controls">
							<input type="text" id="phone" value="${user.phone }" name="phone" required="required">
							<span class="t_phone">*不能为空、不能是空号、长度为11位</span>
						</div>
					</div>
					
					
					<div class="control-group">
						<label class="control-label">身份证号</label>
						<div class="controls">
							<input type="text" id="idCard" value="${user.idCard }" name="idCard" required="required">
							<span class="t_idCard">*不能为空、不能包含汉字、长度为18位</span>
						</div>
					</div>
					
					
					
					<div class="form-actions">
						<a href="main.html" class="btn btn-large btn-primary"><i class="icon-chevron-left icon-white"></i> 返回</a>&emsp;&emsp; 
						<button type="submit" class="btn btn-large"><i class="icon-ok"></i> 提交</button>
					</div>
	
				
	
			
		</div>
		</form>
	</div><!--/span-->
</div><!--/row-->

<!-- 信息提示框 -->
<div class="modal hide fade" id="modTipDiv">
	<div class="modal-header">
		<h3>Warning!</h3>
	</div>
	<div class="modal-body">
		<ul id="modTip"></ul>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn modifyInfoCancel" data-dismiss="modal">知道了</a>
	</div>
</div>
<!-- 这里编辑页面要展示的内容  ends -->


<%@include file="/WEB-INF/pages/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/localjs/modifyInfo.js"></script>      



