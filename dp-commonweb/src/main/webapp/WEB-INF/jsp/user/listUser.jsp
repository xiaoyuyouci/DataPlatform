<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript" src="res/custom/user/listUser.js"></script>
<%-- 
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">User</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
--%>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
			<shiro:hasPermission name="user:add"><button type="button" class="btn btn-default" onclick="openAddUserLayer()">新增</button></shiro:hasPermission>
			<shiro:hasPermission name="user:edit"><button type="button" class="btn btn-default" onclick="openUpdUserLayer()">编辑</button></shiro:hasPermission>
			<shiro:hasPermission name="user:del"><button type="button" class="btn btn-default" onclick="delUser()">删除</button></shiro:hasPermission>
			<shiro:hasPermission name="user:role"><button type="button" class="btn btn-default" onclick="openUserRoleLayer()">角色管理</button></shiro:hasPermission>
			</div>
			<!-- /.panel-heading -->
			
			<form id="userQueryForm" onSubmit="return false;">
			</form>
			
			<div class="panel-body">
				<table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
					<thead>
						<tr>
							<th>编号</th>
							<th>登录名</th>
							<th>创建日期</th>
							<th>性别</th>
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
