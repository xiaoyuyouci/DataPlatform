<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript" src="res/custom/report/dcRealtimeGrid.js"></script>
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
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="row">
					<form role="form" id="dcRealtimeGridQueryForm"
						onSubmit="return false;">
					</form>
				</div>
			</div>
			<!-- /.panel-heading -->

			<div class="panel-body">

				<table width="100%" style="white-space: nowrap"
					class="table table-striped table-bordered table-hover"
					id="table_dcRealtimeGrid">
					<thead>
						<tr>
							<th>DC 代码</th>
							<th>名称</th>
							<th>平板号</th>
							<th>操作人员</th>
							<th>业务单号</th>
							<th>车牌号</th>
							<th>客户代码</th>
							<th>客户名称</th>
							<th>应扫</th>
							<th>当前扫</th>
							<th>状态</th>
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