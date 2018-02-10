<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript" src="res/custom/user/listUserRole.js"></script>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
			<shiro:hasPermission name="user:addRole"><button type="button" class="btn btn-default" onclick="openAddUserRoleLayer()">新增</button></shiro:hasPermission>
			<shiro:hasPermission name="user:delRole"><button type="button" class="btn btn-default" onclick="delUserRole()">删除</button></shiro:hasPermission>
			</div>
			<!-- /.panel-heading -->
			
			<form id="userRoleQueryForm" onSubmit="return false;">
				 <input type="hidden" id="userId" name="userId" value="${userId }"/>
			</form>
			
			<div class="panel-body">
				<table style="width:100%" class="table table-striped table-bordered table-hover" id="dataTables-userRole">
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