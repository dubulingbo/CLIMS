<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<!-- <link href="/statics/localcss/instrumentApply.css" rel="stylesheet"> -->
<!-- 这里编辑每个页面的导航条  starts -->
<div>
	<ul class="breadcrumb">
		<li><a href="/main.html">主页</a> <span class="divider">/</span></li>
		<li>仪器报废申请</li>
	</ul>
</div>
<!-- 这里编辑每个页面的导航条  ends -->


<!-- 这里编辑页面要展示的内容  starts -->
<div class="row-fluid sortable">
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2><i class="icon-edit"></i> 申请报废</h2>
		</div>
		<div class="box-content">
			<div class="form-horizontal">
				<fieldset>
					<div class="control-group">
						<label class="control-label" for="focusedInput">仪器编号</label>
						<div class="controls">
							<input class="input-xlarge focused" id="sc_assignId" type="text" required="required">
							<span style="color: red; font-weight: bold;" id="sc_assignIdTip"> *</span>
						</div>
				  	</div>
				  	<div class="control-group">
						<label class="control-label" for="focusedInput">仪器名称</label>
						<div class="controls">
							<input class="input-xlarge focused" id="sc_instrumentName" type="text" style="background:white;" readonly="readonly">
							<span style="color:red;font-weight:bold;"> *</span>
						</div>
				  	</div>
				  	
				  	<div class="control-group">
						<label class="control-label" for="focusedInput">报废数量</label>
						<div class="controls">
							<select id="sc_scrapNumber" style="width:80px;">
								<option value="">-请选择-</option>
							</select>
							<span style="color: red; font-weight: bold;"> *</span>
						</div>
				  	</div>
				  	<div class="control-group">
						<label class="control-label" for="focusedInput">报废原因</label>
						<div class="controls">
							<textarea class="autogrow" id="sc_scrapDetail" required="required"></textarea>
							<span style="color: red; font-weight: bold;"> *</span>
						</div>
				  	</div>
				  	
				  	<div class="control-group">
						<label class="control-label" for="focusedInput">申请人</label>
						<div class="controls">
							<input class="input-xlarge focused" id="sc_applyPerson" type="text" style="background:white;" readonly="readonly">
							<span style="color:red; font-weight:bold;"> *</span>
						</div>
				  	</div>
				  	
				  	<div class="form-actions">
						<input type="submit" class="btn btn-primary instScrapApplySave" value=" 提交">
						<input type="reset" class="btn" value=" 重置">
					</div>
				</fieldset>
			</div>
		</div>
	</div>
</div>
<!-- 这里编辑页面要展示的内容  ends -->

<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<!-- <script type="text/javascript" src="/statics/laydate/laydate.js"></script> -->
<script type="text/javascript" src="/statics/localjs/instrumentApply.js"></script>


