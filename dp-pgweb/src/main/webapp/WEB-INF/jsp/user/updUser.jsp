<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<script type="text/javascript" src="res/custom/user/updUser.js"></script>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" id="updUserForm" onSubmit="return false;">
							<div class="form-group">
								<label>用户名</label> 
								<input type="hidden" name="id" value="${user.id }" class="form-control" placeholder="Enter text">
								<input readonly name="loginName" value="${user.loginName }" class="form-control" placeholder="Enter text">
							</div>
							<button type="submit" class="btn btn-default">Submit</button>
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