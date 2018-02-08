<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript" src="res/custom/role/listRolePermission.js"></script>
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
			<shiro:hasPermission name="role:addPermission"><button type="button" class="btn btn-default" onclick="openAddRolePermissionLayer()">新增</button></shiro:hasPermission>
			<shiro:hasPermission name="role:delPermission"><button type="button" class="btn btn-default" onclick="delRolePermission()">删除</button></shiro:hasPermission>
			</div>
			<!-- /.panel-heading -->
			
			<form id="rolePermissionQueryForm" onSubmit="return false;">
				<input type="hidden" id="roleId" name="roleId" value="${roleId }"> 
			</form>
			
			<div class="panel-body">
				<table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-rolePermission">
					<thead>
						<tr>
							<th>编号</th>
							<th>权限名</th>
							<th>权限中文名</th>
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