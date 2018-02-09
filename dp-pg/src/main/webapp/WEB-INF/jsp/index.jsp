<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Bootstrap Admin Theme</title>


    <!-- Bootstrap Core CSS -->
    <link href="res/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="res/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

	<!-- DataTables CSS -->
    <link href="res/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="res/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

	<link href="res/DataTables-1.10.15/extensions/Select/css/select.dataTables.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="res/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="res/vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="res/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

	<link href="res/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
	
</head>

<body>

    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">报表平台</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
            	<li><a href="/logout">账号${loginName }，退出<i class="fa fa-sign-out fa-fw"></i></a>
                </li>
            </ul>
            <!-- /.navbar-top-links -->
			
            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
					<ul class="nav" id="side-menu">
						<shiro:hasPermission name="dashboard:list">
						<li><a class="hah" href="/dashboard"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="report:list">
						<li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> 我的报表<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li><a class="hah" href="report/fcRealtimeGrid"> 工厂实时状态</a></li>
                                <li><a class="hah" href="#"> 工厂每日状态</a></li>
                                <li><a class="hah" href="#"> DC实时状态</a></li>
                                <li><a class="hah" href="#"> DC每日状态</a></li>
                                <li><a class="hah" href="#"> UID查询</a></li>
                                <li><a class="hah" href="#"> 包材厂数据统计</a></li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        </shiro:hasPermission>
						<shiro:hasPermission name="user:list">
							<li><a class="hah" href="user/listUser"><i class="fa fa-gear fa-fw"></i> 用户管理</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="role:list">
							<li><a class="hah" href="role/listRole"><i class="fa fa-gear fa-fw"></i> 角色管理</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="permission:list">
							<li><a class="hah" href="permission/listPermission"><i class="fa fa-gear fa-fw"></i> 权限管理</a></li>
						</shiro:hasPermission>
					</ul>
				</div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>

        <div id="page-wrapper">
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="res/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="res/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="res/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="res/vendor/raphael/raphael.min.js"></script>
    <script src="res/vendor/morrisjs/morris.min.js"></script>
    <script src="res/data/morris-data.js"></script>

	<!-- DataTables JavaScript -->
    <script src="res/vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="res/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="res/vendor/datatables-responsive/dataTables.responsive.js"></script>
	<script src="res/DataTables-1.10.15/extensions/Select/js/dataTables.select.js"></script>
	
    <!-- Custom Theme JavaScript -->
    <script src="res/dist/js/sb-admin-2.js"></script>
	
	<script src="res/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	
	<script src="res/layer/layer.js"></script>
	
	<script src="res/jquery/jquery.validate.min.js"></script>
	
	<script>
		$(function(){	
			$('#side-menu a.hah').click(function() {
				$.ajaxSetup({cache : false});
				var url=$(this).attr('href');
				$('#page-wrapper').load(url);
				return false;
			});
			$('#page-wrapper').load($('#side-menu a').first().attr('href'));
		});
	</script>
</body>

</html>
