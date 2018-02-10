<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<html lang="en">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>winsafe</title>

    <!-- Bootstrap Core CSS -->
    <link href="res/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="res/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="res/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="res/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">请登录</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" id="loginForm" onSubmit="return false;">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="账号" id="loginName" name="loginName" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="密码" id="password" name="password" type="password" value="">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">记住我
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <button type="submit" id="loginBtn" class="btn btn-lg btn-success btn-block">Login</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- jQuery -->
    <script src="res/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="res/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="res/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="res/dist/js/sb-admin-2.js"></script>
    
    <script src="res/layer/layer.js"></script>
    
    <script src="res/jquery/jquery.validate.min.js"></script>

   <script>
		$(document).ready(function() {
			$("#loginBtn").on('click', function(){
				$("#loginForm").validate({
					submitHandler:function(form){
						var searchLayerIndex = null;
	                    $.ajax({
	                        url:"/login",
	                        data:$("#loginForm").serialize(),
	                        type:'post',
	                        dataType:'json',
	                        beforeSend:function () {
	                            $("#loginForm #loginBtn").attr({ disabled: "disabled" });
	                            searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
	                        },
	                        complete:function(){
	                            $("#loginForm #loginBtn").removeAttr("disabled");
	                            layer.close(searchLayerIndex);
	                        },
	                        success:function(result){
	                        	if (!result.legal){
	                        		layer.tips(result.message, '#loginForm input[name=\'loginName\']', { time: 2000 });
	        					} else {
	        						window.location.href='/'
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
	                },
	                showErrors: function (errorMap, errorList) {
	                    var msg = "";
	                    $.each(errorList, function (i, v) {
	                        layer.tips(v.message, v.element, { time: 2000 });
	                        return false;
	                    });  
	                },
	                onfocusout: false
	            });
			});
		});
	</script>
</body>