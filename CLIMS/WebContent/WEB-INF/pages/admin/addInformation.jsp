<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div>
	<ul class="breadcrumb">
		<li>导航：<a href="/main.html">主页</a> <span class="divider">/</span></li>
		<li><a href="infoManage.html">资讯管理</a> <span class="divider">/</span></li>
		<li>添加资讯</li>
	</ul>
</div>

<div class="row-fluid sortable">
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2><i class="icon-edit"></i> 添加资讯信息</h2>
		</div>
		<div class="box-content">
			<form action="addInfoSave.html" method="post" class="form-horizontal" id="addInfo_form">
				<fieldset>
					<div class="control-group">
						<label class="control-label">标题</label>
						<div class="controls">
							<input class="input-xlarge focused" id="informationTitle" type="text" name="title">
						</div>
					</div>
				
					<div class="control-group">
						<label class="control-label">资讯类型</label>
						<div class="controls">
							<select id="docType" name="typeId" style="width:120px;">
								<option value="">--请选择--</option>
								<c:if test="${dicList != null}">
									<c:forEach items="${dicList}" var="dic">
										<option value="${dic.valueId}">${dic.valueName}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
				
					<div class="control-group">
						<label class="control-label">上传附件</label>
						<div class="controls">
							<input id="uploadInformationFile" name="uploadInformationFile" type="file"> <input type="button" id="informationuploadbtn" value="上传">
							<span style="color:red;font-weight:bold;">*注：上传大小不得超过 50M</span>
						 	<input type="hidden" id="uploadfilepathhide" name="filePath">
						 	<input type="hidden" id="uploadfilenamehide" name="fileName">
						 	<input type="hidden" id="uploadfilesizehide" name="fileSize">
						 	<div id="filearea" style="display:none;"></div>
						</div>
					</div>
				
					<div class="control-group">
						<label class="control-label" for="textarea2">资讯内容</label>
						<div class="controls">
							<textarea class="cleditor" name="content" rows="3" id="informationContent"></textarea>
						</div>
					</div>
					<div class="form-actions">
						<a href="infoManage.html" class="btn btn-primary">返回</a>&emsp;&emsp;&emsp;
						<button class="btn addInfoSubmitBtn">提交</button>
					</div>
				</fieldset>
			</form>

		</div>
	</div>
	<!--/span-->

</div>
<!--/row-->
<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script src="/statics/localjs/addInformation.js"></script>