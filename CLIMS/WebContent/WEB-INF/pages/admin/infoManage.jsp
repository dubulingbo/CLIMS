<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<link href='/statics/localcss/information.css' rel='stylesheet'>
<div>
	<ul class="breadcrumb">
		<li>导航：<a href="/main.html">主页</a> <span class="divider">/</span></li>
		<li>资讯管理</li>
	</ul>
</div>
<div class="row-fluid sortable">		
	<div class="box span12">
		<div class="box-header well">
			<h2><i class="icon-user"></i> 资讯列表</h2>
		</div>
		
		<div class="box-content">
		<a href="addInformation.html" class="btn btn-large"><i class="icon-plus"></i> 添加资讯</a>
		<hr>
		<c:if test="${page.items != null}">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>编号</th>
						<th>标题</th>
						<th>文件大小(KB)</th>
						<th>发布者</th>
						<th>发布时间</th>
						<th>上传时间</th>
						<th>操作</th>
					</tr>
				</thead>   
				<tbody>
				<c:forEach items="${page.items}" var="infor">
					<tr>
						<td class="center">${infor.id}</td>
						<td class="center">${infor.title}</td>
						<td class="center">
							<fmt:formatNumber value="${infor.fileSize/1024}" maxFractionDigits="2"/>
						</td>
						<td class="t_publisher${infor.id }">${infor.publisher}</td>
						<td class="t_publishDate${infor.id }">
							<fmt:formatDate value="${infor.publishDate}" pattern="yyyy-MM-dd HH:mm"/>
						</td>
						<td class="center"><fmt:formatDate value="${infor.uploadDate}" pattern="yyyy-MM-dd"/></td>
						<td class="center">
						
						<div class="btn-group">
							<button class="btn">选择操作</button>
							<button class="btn dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
							<ul class="dropdown-menu t_action${infor.id }">
<li><a class="viewinformation" href="javascript:void(0)" id="${infor.id}"><i class="icon-zoom-in"></i> 查看</a></li>
<c:if test="${infor.state == 0 }">
<li><a class="modifyInformationState" href="javascript:void(0)" id="${infor.id}" inforstate="${infor.state }"><i class="icon-share"></i> 发布</a></li>
<li><a class="modifyinformation" href="javascript:void(0)" id="${infor.id}" title="${infor.title}"><i class="icon-edit"></i> 修改</a></li>
<li><a class="delinformation" href="javascript:void(0)" id="${infor.id}"><i class="icon-trash"></i> 删除</a></li>
</c:if>
							</ul>
						</div>
							
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>   
			<div class="pagination pagination-centered">
				<ul>
					<c:choose>
						<c:when test="${page.currentPageNo == 1}">
							<li class="active"><a href="javascript:void(0)" title="首页">首页</a></li>
						</c:when>
						<c:otherwise>
							<li><a href='infoManage.html?pageIndex=1' title="首页">首页</a></li>
						</c:otherwise>
					</c:choose>
				
					<c:if test="${page.prevPages != null}">
						<c:forEach items="${page.prevPages}" var="num">
							<li><a href='infoManage.html?pageIndex=${num}' title="${num}">${num}</a></li>
						</c:forEach>
					</c:if>

					<li class="active"><a href="javascript:void(0)" title="${page.currentPageNo}">${page.currentPageNo}</a></li>

					<c:if test="${page.nextPages != null}">
						<c:forEach items="${page.nextPages}" var="num">
							<li><a href='infoManage.html?pageIndex=${num}' title="${num}">${num}</a></li>
						</c:forEach>
					</c:if>

					<c:if test="${page.totalPageCount != null}">
						<c:choose>
							<c:when test="${page.currentPageNo == page.totalPageCount}">
								<li class="active"><a href="javascript:void(0)" title="尾页">尾页</a></li>
							</c:when>
							<c:otherwise>
								<li><a href='infoManage.html?pageIndex=${page.totalPageCount}' title="尾页">尾页</a></li>
							</c:otherwise>
						</c:choose>
					</c:if>

					<c:if test="${page.totalPageCount == null}">
						<li class="active"><a href="javascript:void(0)" title="尾页">尾页</a></li>
					</c:if>
				</ul>
			</div>
		</c:if>
		</div>
		
	</div><!--/span-->
</div><!--/row-->



	 
<div class="modal hide fade" id="modifyInfoDiv">
<form action="modifyInformation.html" enctype="multipart/form-data" method="post" onsubmit="return modifyInfoFunction();">
	<div class="modal-header">
		<button type="button" class="close modifyinfocancel" data-dismiss="modal">×</button>
		<h3>修改资讯信息</h3>
	</div>
	<div class="add_information_modal-body">
		<ul id="modify_formtip"></ul>
		<ul class="topul">
			<li><label>标题：</label><input id="informationTitleModify" type="text" name="title" value=""></li>
			<li>
				<label>资讯类型：</label>
				<select id="docTypeModity" name="typeId" style="width:100px;">
					<option value="">--请选择--</option>
			  		<c:if test="${dicList != null }">
				  		<c:forEach items="${dicList }" var="dic">
				  			<option value="${dic.valueId}"> ${dic.valueName }</option>
				  		</c:forEach>
			  		</c:if>
			  		<c:if test="${dicList == null }">
			  			<option value="">加载列表失败</option>
			  		</c:if>
			  	</select>
			</li>
		</ul>
		<div class="clear"></div>
		<ul class="downul">
			<li>
				<span>上传附件：</span><input id="uploadInformationFileM" name="uploadInformationFile" type="file" /> <input type="button" id="informationuploadMbtn" value="上传"/>
			 	<span style="color:red;font-weight: bold;">*注：上传大小不得超过 50M</span>
			 	<input type="hidden" id="infoIdModify" name="id"/>
			 	<input type="hidden" id="uploadfilepathhideM" name="filePath" />
			 	<input type="hidden" id="uploadfilenamehideM" name="fileName" />
			 	<input type="hidden" id="fileSizehideM" name="fileSize" />
			 </li>
			 <li id="fileareaM">
			 	
			 </li>
		</ul>
		<ul class="downul">
			<li id="modifyinformationli"></li>
		</ul>
	</div>
	<div class="modal-footer">
		<a href="javascript:void(0)" class="btn modifyinfocancel" data-dismiss="modal">取消</a>
		<input type="submit"  class="btn btn-primary" value="保存" />
	</div>
</form>
</div>
	 
<div class="modal hide fade" id="viewInfoDiv">
	<div class="modal-header">
		<button type="button" class="close addusercancel" data-dismiss="modal">×</button>
		<h3>查看资讯信息</h3>
	</div>
	<div class="view_information_modal-body">
		<ul class="viewinformationul" id="viewContent"></ul>
		<div class="clear"></div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn addusercancel" data-dismiss="modal">关闭</a>
	</div>
</div>
	 
	 	 
<%@include file="/WEB-INF/pages/common/foot.jsp"%>

<script type="text/javascript" src="/statics/localjs/information.js"></script> 
