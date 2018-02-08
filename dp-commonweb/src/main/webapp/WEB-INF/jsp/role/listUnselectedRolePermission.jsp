<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<script type="text/javascript" src="res/custom/role/listUnselectedRolePermission.js"></script>
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
			<button type="button" class="btn btn-default" onclick="addRolePermission()">确定</button>
			</div>
			<!-- /.panel-heading -->
			
			<form id="unselectedRolePermissionQueryForm" onSubmit="return false;">
				<input type="hidden" id="roleId" name="roleId" value="${roleId }"> 
			</form>
			
			<div class="panel-body">
				<table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-unselectedRolePermission">
					<thead>
						<tr>
							<th>编号</th>
							<th>权限名</th>
							<th>资源路径</th>
							<th>验证方式</th>
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