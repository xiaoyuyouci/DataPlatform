<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<script type="text/javascript" src="res/custom/user/listUnselectedUserRole.js"></script>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
			<button type="button" class="btn btn-default" onclick="addUserRole()">确定</button>
			</div>
			<!-- /.panel-heading -->
			
			<form id="unselectedUserRoleQueryForm" onSubmit="return false;">
				 <input type="hidden" id="userId" name="userId" value="${userId }"/>
			</form>
			
			<div class="panel-body">
				<table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-unselectedUserRole">
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