<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>

<!-- 这里编辑每个页面的导航条  starts -->
<div>
	<ul class="breadcrumb">
		<li>导航：<a href="/main.html">主页</a> <span class="divider">/</span></li>
		<li>用户管理</li>
	</ul>
</div>
<!-- 这里编辑每个页面的导航条  ends -->





<!-- 这里编辑页面要展示的内容  starts -->
<!-- <button class="btn btn-large btn-primary" id="addUser">添加用户</button> -->
<div class="row-fluid sortable">
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2>
				<i class="icon-user"></i> 用户列表
			</h2>
			<div class="box-icon">
				<span class="icon32 icon-color icon-add addUser" style="cursor: pointer" />
			</div>
		</div>
		<div class="box-content">
			<form action="userManage.html" method="post">
				<div class="searcharea">
					用户名称： <input type="text" name="s_userCode" value="${s_userCode}">
					角色： <select name="s_roleId" style="width: 100px;">
						<option value="" selected="selected">--请选择--</option>
						<c:forEach items="${roleList}" var="role">
							<option
								<c:if test="${role.id == s_roleId}">selected="selected"</c:if>
								value="${role.id}">${role.roleName}</option>
						</c:forEach>
					</select>
					<button class="btn btn-primary">
						<i class="icon-search icon-white"></i> 查询
					</button>
				</div>
			</form>
			<table class="table table-striped table-bordered bootstrap-datatable">
				<thead>
					<tr>
						<th>用户姓名</th>
						<th>登录账号</th>
						<th>用户角色</th>
						<th>所在单位</th>
						<th>注册日期</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>

					<c:if test="${page.items != null}">
						<c:forEach items="${page.items}" var="user">
							<tr>
								<td class="center">${user.userName}</td>
								<td class="center">${user.userCode}</td>
								<td class="center">${user.userRoleName }</td>
								<td class="center">${user.dept}</td>
								<td class="center">
									<fmt:formatDate value="${user.creationDate }" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td class="center"><a class="btn btn-success viewUser"
									href="#" id="${user.id }"> <i
										class="icon-zoom-in icon-white"></i> 查看
								</a> <a class="btn btn-info perfectUser" href="#" id="${user.id }">
										<i class="icon-edit icon-white"></i> 编辑
								</a> <a class="btn btn-danger deleteUser" href="#" id="${user.id }"
									userrole="${user.userRole }" usercode="${user.userCode }">
										<i class="icon-trash icon-white"></i> 删除
								</a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<div class="pagination pagination-centered">
				<ul>
					<c:choose>
						<c:when test="${page.currentPageNo == 1}">
							<li class="active"><a href="javascript:void();" title="首页">首页</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href='userManage.html?pageIndex=1&s_userCode=${s_userCode}&s_roleId=${s_roleId}'
								title="首页">首页</a></li>
						</c:otherwise>
					</c:choose>
					<c:if test="${page.prevPages != null}">
						<c:forEach items="${page.prevPages}" var="num">
							<li><a
								href='userManage.html?pageIndex=${num}&s_userCode=${s_userCode}&s_roleId=${s_roleId}'
								class="number" title="${num}">${num}</a></li>
						</c:forEach>
					</c:if>

					<li class="active"><a href="#" title="${page.currentPageNo}">${page.currentPageNo}</a>
					</li>

					<c:if test="${page.nextPages != null}">
						<c:forEach items="${page.nextPages}" var="num">
							<li><a
								href='userManage.html?pageIndex=${num}&s_userCode=${s_userCode}&s_roleId=${s_roleId}'
								class="number" title="${num}">${num}</a></li>
						</c:forEach>
					</c:if>

					<c:if test="${page.totalPageCount != null}">
						<c:choose>
							<c:when test="${page.currentPageNo == page.totalPageCount}">
								<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
							</c:when>
							<c:otherwise>
								<li><a
									href='userManage.html?pageIndex=${page.totalPageCount}&s_userCode=${s_userCode}&s_roleId=${s_roleId}'
									title="尾页">尾页</a></li>
							</c:otherwise>
						</c:choose>
					</c:if>

					<c:if test="${page.totalPageCount == null}">
						<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</div>

<!-- 添加用户信息弹出框  -->
<div class="modal hide fade" id="addUserDiv">
	<form action="addUser.html" method="post" enctype="multipart/form-data"
		onsubmit="return addUserFunction();">
		<div class="modal-header">
			<button type="button" class="close addUserCancel"
				data-dismiss="modal">×</button>
			<h3>添加用户信息</h3>
		</div>
		<div class="modal-body">
			<ul id="add_formtip"></ul>
			<ul class="topul">
				<li><label>姓名：</label> <input id="userName" type="text"
					name="userName" value="" /> <span
					style="color: red; font-weight: bold;">*</span></li>
				<li><label>学号：</label> <input id="userCode" type="text"
					name="userCode" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" />
					<span style="color: red; font-weight: bold;">*</span></li>

				<li><label>身份证号：</label> <input id="userPassword" type="text"
					name="userPassword" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" />
					<span style="color: red; font-weight: bold;">*</span></li>


				<li><label>性别：</label> <select id="gender" name="gender"
					style="width: 100px">
						<option value="" selected="selected">--请选择--</option>
						<option value="1">男</option>
						<option value="2">女</option>
				</select> <span style="color: red; font-weight: bold;">*</span></li>


				<li><label>角色：</label> <select id="selectRole" name="userRole"
					style="width: 100px">
						<option value="" selected="selected">--请选择--</option>
						<c:if test="${roleList != null }">
							<c:forEach items="${roleList }" var="role">
								<option value="${role.id }">${role.roleName }</option>
							</c:forEach>
						</c:if>
				</select> <span style="color: red; font-weight: bold;">*</span></li>


				<li><label>所在单位：</label> <input id="selectDeptName"
					type="hidden" name="deptName" value="" /> <select id="selectDept"
					name="dept" style="width: 100px">
						<option value="" selected="selected">--请选择--</option>
						<c:if test="${deptList != null }">
							<c:forEach items="${deptList }" var="dept">
								<option value="${dept.deptName }">${dept.deptName }</option>
							</c:forEach>
						</c:if>
				</select> <span style="color: red; font-weight: bold;">*</span></li>
			</ul>
			<div class="clear"></div>
			<ul class="downul">
				<li><label>上传头像：</label></li>
			</ul>
		</div>



		<div class="modal-footer">
			<a href="#" class="btn addUserCancel" data-dismiss="modal">取消</a> <input
				type="submit" class="btn btn-primary" value="保存" />
		</div>
	</form>
</div>




<!-- 修改用户信息弹出框  -->
<div class="modal hide fade" id="perfectUserDiv">
	<form action="perfectUser.html" method="post"
		onsubmit="return perfectUserFunction();">
		<div class="modal-header">
			<h3>修改用户信息</h3>
		</div>
		<div class="modal-body">
			<input id="p_id" type="hidden" name="id" />
			<ul class="dashboard-list">
				<li><a style="font-size: 15px;"> <i class="icon-tags"></i>
						<span class="green" style="width: 88px;">登录账号</span> <input
						type="text" id="p_userCode" name="userCode" readonly="readonly" />
				</a></li>
				<li><a style="font-size: 15px;"> <i class="icon-tags"></i>
						<span class="red" style="width: 88px;">姓名</span> <input
						type="text" id="p_userName" name="userName" readonly="readonly" />
				</a></li>
				<li><input type="hidden" id="p_genderNumber" name="gender">
					<a style="font-size: 15px;"> <i class="icon-tags"></i> <span
						class="blue" style="width: 88px;">性别</span> <input type="text"
						id="p_gender" readonly="readonly" />
				</a></li>
				<li><a style="font-size: 15px;"> <i class="icon-tags"></i>
						<span class="green" style="width: 88px;">邮箱</span> <input
						type="text" id="p_email" name="email" />
				</a></li>
				<li><a style="font-size: 15px;"> <i class="icon-tags"></i>
						<span class="red" style="width: 88px;">手机号码</span> <input
						type="text" id="p_phone" name="phone" />
				</a></li>
				<li><a style="font-size: 15px;"> <i class="icon-tags"></i>
						<span class="blue" style="width: 88px;">所在单位</span> <select
						id="p_dept" name="dept" style="width: 150px">
							<option value="" selected="selected">--请选择--</option>
							<c:if test="${deptList != null }">
								<c:forEach items="${deptList }" var="de">
									<option value="${de.deptName }">${de.deptName }</option>
								</c:forEach>
							</c:if>
					</select>
				</a></li>
				<li><a style="font-size: 15px;"> <i class="icon-tags"></i>
						<span class="yellow" style="width: 88px;">角色</span> <select
						id="p_userRole" name="userRole" style="width: 150px">
							<option value="" selected="selected">--请选择--</option>
							<c:if test="${roleList != null }">
								<c:forEach items="${roleList }" var="role">
									<option value="${role.id }">${role.roleName }</option>
								</c:forEach>
							</c:if>
					</select>
				</a></li>
			</ul>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn perfectUserCancel" data-dismiss="modal"> 取消 </a> 
			<input type="submit" class="btn btn-primary" value=" 修 改 ">
		</div>
	</form>
</div>



<!-- 这里编辑页面要展示的内容  ends -->
<%@include file="/WEB-INF/pages/common/foot.jsp"%>

<script type="text/javascript" src="/statics/localjs/userManage.js"></script>
