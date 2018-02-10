<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<script type="text/javascript" src="res/custom/role/updRole.js"></script>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" id="updRoleForm" onSubmit="return false;">
							<div class="form-group">
								<label>角色名</label> 
								<input type="hidden" id="id" name="id" value="${role.id }">
								<input name="name" value="${role.name }" class="form-control" placeholder="请输入角色名" disabled>
							</div>
							<button type="submit" class="btn btn-default">提交</button>
							<button type="reset" class="btn btn-default">重置</button>
						</form>
					</div>
					<!-- /.col-lg-12 (nested) -->
				</div>
				<!-- /.row (nested) -->
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
	
</script>