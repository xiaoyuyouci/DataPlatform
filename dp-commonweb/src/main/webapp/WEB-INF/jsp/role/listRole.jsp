<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript" src="res/custom/role/listRole.js"></script>
<%-- 
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Role</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
--%>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
			<shiro:hasPermission name="role:add"><button type="button" class="btn btn-default" onclick="openAddRoleLayer()">新增</button></shiro:hasPermission>
			<shiro:hasPermission name="role:edit"><button type="button" class="btn btn-default" onclick="openUpdRoleLayer()">编辑</button></shiro:hasPermission>
			<shiro:hasPermission name="role:del"><button type="button" class="btn btn-default" onclick="delRole()">删除</button></shiro:hasPermission>
			<shiro:hasPermission name="role:permission"><button type="button" class="btn btn-default" onclick="listRolePermission()">权限管理</button></shiro:hasPermission>
			</div>
			<!-- /.panel-heading -->
			
			<form id="roleQueryForm" onSubmit="return false;">
			</form>
			
			<div class="panel-body">
				<table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
					<thead>
						<tr>
							<th>编号</th>
							<th>角色名</th>
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
<script>

</script>