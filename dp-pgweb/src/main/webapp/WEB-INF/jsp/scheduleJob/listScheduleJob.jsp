<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript" src="res/custom/scheduleJob/listScheduleJob.js"></script>
<%-- 
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">ScheduleJob</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
--%>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
			<shiro:hasPermission name="scheduleJob:add"><button id="btn_add" type="button" class="btn btn-default">新增</button></shiro:hasPermission>
			<shiro:hasPermission name="scheduleJob:edit"><button id="btn_edit" type="button" class="btn btn-default">编辑</button></shiro:hasPermission>
			<shiro:hasPermission name="scheduleJob:del"><button id="btn_del" type="button" class="btn btn-default">删除</button></shiro:hasPermission>
			<shiro:hasPermission name="scheduleJob:start"><button id="btn_start" type="button" class="btn btn-default">运行</button></shiro:hasPermission>
			<shiro:hasPermission name="scheduleJob:stop"><button id="btn_end" type="button" class="btn btn-default">挂起</button></shiro:hasPermission>
			</div>
			<!-- /.panel-heading -->
			
			<form id="scheduleJobQueryForm" onSubmit="return false;">
			</form>
			
			<div class="panel-body">
				<table width="100%" style="white-space: nowrap" class="table table-striped table-bordered table-hover" id="table_scheduleJob">
					<thead>
						<tr>
							<th>编号</th>
							<th>任务组</th>
					        <th>任务名称</th>
					        <th>描述</th>
					        <th>是否同时执行</th>
					        <th>运行状态(内存)</th>
					        <th>运行状态(数据库)</th>
					        <th>Cron表达式</th>
					        <th>SpringId</th>
					        <th>执行类</th>
					        <th>类方法</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<!-- /.table-responsive -->
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<!-- Page-Level Demo Scripts - Tables - Use for reference -->
