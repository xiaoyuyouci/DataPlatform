<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript" src="res/custom/report/fcRealtimeGrid.js"></script>
<%-- 
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">User</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
--%>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
			</div>
			<!-- /.panel-heading -->
			
			<form id="userQueryForm" onSubmit="return false;">
			</form>
			
			<div class="panel-body">
				<table style="width:100%" class="table table-striped table-bordered table-hover" id="table_fcRealtimeGrid">
					<thead><tr><th></th></tr></thead>
					<tbody></tbody>
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