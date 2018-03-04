<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript" src="res/custom/report/ppStatisticsGrid.js"></script>
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
				<div class="row">
					<form id="ppStatisticsGridQueryForm" onSubmit="return false;">
					</form>
				</div>
			</div>
			<!-- /.panel-heading -->

			<div class="panel-body">
				<table width="100%" style="white-space: nowrap" class="table  table-bordered " id="table_ppStatisticsGrid">
					<thead>
						<tr>
							<th>日期</th>
							<th>Supplier</th>
							<th>Line</th>
							<th>IPC本地读取数据</th>
							<th>IPC上传数据</th>
							<th>当天应给Aimia上传数据</th>
							<th>当天实际给Aimia上传数据</th>
							<th>14天后需要给Aimia上传数据</th>
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