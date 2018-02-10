<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
			<div class="panel-heading"></div>
			<!-- /.panel-heading -->

			<form id="fcRealtimeGridQueryForm" onSubmit="return false;"></form>

			<div class="panel-body">
				<table width="100%" class="table table-striped table-bordered table-hover" id="table_fcRealtimeGrid">
					<thead>
						<tr>
							<th>事业部</th>
									<th>工厂代码</th>
									<th>线号</th>
									<th>当前批次</th>
									<th>FPC</th>
									<th>产品名称</th>
									<th>当前状态</th>
									<th>上 传 时 间</th>
									<th>箱瓶是否关联</th>
									<th>Case Count</th>
									<th>Case 打印机回传数量</th>
									<th>Case 扫描数量</th>
									<th>Case 剔除数量</th>
									<th>Case IPC本地数量</th>
									<th>Case 后台系统数量</th>
									<th>Case Context接收数量</th>
									<th>Case 生产合格率</th>
									<th>Item 相机扫描数量</th>
									<th>Item 相机回传数量</th>
									<th>Item 剔除数量</th>
									<th>Item IPC本地数量</th>
									<th>Item 后台系统数量</th>
									<th>Item Context接收数量</th>
									<th>Item 生产合格率</th>
									<th>是否符合Case Count</th>
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