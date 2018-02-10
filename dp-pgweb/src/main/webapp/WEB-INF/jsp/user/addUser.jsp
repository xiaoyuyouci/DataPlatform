<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" id="addUserForm" onSubmit="return false;">
							<div class="form-group">
								<label>登录名</label> 
								<input name="loginName" class="form-control" placeholder="请输入登录名">
							</div>
							<div class="form-group">
								<label>密码</label> 
								<input type="password" id="password" name="password" class="form-control" placeholder="请输入密码">
							</div>
							<div class="form-group">
								<label>确认密码</label> 
								<input type="password" name="confirmPwd" class="form-control" placeholder="请再次输入密码">
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
	$(document).ready(function() {
		$("#addUserForm button:submit").on('click', function(){
			$("#addUserForm").validate({
				submitHandler:function(form){
					var searchLayerIndex = null;
                    //ajaxæäº¤
                    $.ajax({
                        url:"/user/ajaxAddUser",
                        data:$("#addUserForm").serialize(),
                        type:'post',
                        dataType:'json',
                        beforeSend:function () {
                            $("#addUserForm button:submit").attr({ disabled: "disabled" });
                            searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
                        },
                        complete:function(){
                            $("#addUserForm button:submit").removeAttr("disabled");
                            layer.close(searchLayerIndex);
                        },
                        success:function(result){
                        	if (!result.legal){
                        		layer.tips(result.message, '#addUserForm input[name=\'loginName\']', { time: 2000, tips: 3 });
        					} else {
        						search();
        						closeAddUserLayer();
        					}
                        }

                    });
                },
                rules: {
                	loginName: {
                        required: true,
                        maxlength: 100
                    },
                    password: {
                        required: true,
                        maxlength: 100
                    },
                    confirmPwd:{
                        equalTo:"#password"
                    }
                },
                showErrors: function (errorMap, errorList) {
                    var msg = "";
                    $.each(errorList, function (i, v) {
                        layer.tips(v.message, v.element, { time: 2000, tips: 3 });
                        return false;
                    });  
                },
                onfocusout: false
            });
		});
	});
	
	function closeAddUserLayer(){
		layer.close(addUserLayerIndex);
	}
</script>