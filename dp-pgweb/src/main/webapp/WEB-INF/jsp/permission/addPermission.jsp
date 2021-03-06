<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<script type="text/javascript" src="res/custom/permission/addPermission.js"></script>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" id="addPermissionForm" onSubmit="return false;">
							<div class="form-group">
								<label>权限名</label> 
								<input name="name" class="form-control" placeholder="请输入权限名">
							</div>
							<div class="form-group">
								<label>权限中文名</label> 
								<input name="nameCn" class="form-control" placeholder="请输入权限中文名">
							</div>
							<div class="form-group">
								<label>资源路径</label> 
								<input type="text" id="url" name="url" class="form-control" placeholder="请输入资源路径">
							</div>
							<div class="form-group">
                            	<label>验证方式</label>
                                	<select name="filter" class="form-control">
                                    	<option value="authc">authc</option>
                                  		<option value="anon">anon</option>
                            	</select>
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