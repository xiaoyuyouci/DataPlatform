<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript" src="res/custom/report/fcDailyGrid.js"></script>
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
				<form id="fcDailyGridQueryForm" onSubmit="return false;">
				<!-- <div class="form-group col-md-3">
					<select class="form-control" name="show">
						<option value="all">显示所有数据</option>
						<option value="error">只显示异常数据</option>
						<option value="warning">只显示警告数据</option>
					</select>
				</div> -->
				<div class="form-group col-md-2">
					<input name="date" class="form-control" placeholder="请选择时间">
				</div>
				<div class="form-group col-md-2">
					<select class="form-control" name="bu">
								<option value="">请选择 BU</option>
								<option value="Hair">Hair</option>
								<option value="PCC">PCC</option>
								<option value="Baby">Baby</option>
								<option value="Fem Care">Fem Care</option>
								<option value="Oral">Oral</option>
								<option value="Shave Care">Shave Care</option>
							</select>
				</div>
				<div class="form-group col-md-2">
					<input name="plantCode" class="form-control" placeholder="请输入工厂代码">
				</div>
				<div class="form-group col-md-2">
					<input name="lineCode" class="form-control" placeholder="请输入线号">
				</div>
				<div class="form-group col-md-2">
					<input name="batchNo" class="form-control" placeholder="请输入批次号">
				</div>
				<div class="form-group input-group col-md-2">
					<input name="mCode" class="form-control" placeholder="请输入FPC">
					<span class="input-group-btn">
						<button id="btn_search" class="btn btn-default" type="button">
							<i class="fa fa-search"></i>
						</button>
					</span>
				</div>
			</form>
			</div>
			</div>
			<!-- /.panel-heading -->

			<div class="panel-body">
				<button type="button" id="btn_exportFcDailyGrid" class="btn btn-outline btn-default">导出</button>
				<table width="100%" style="white-space: nowrap" class="table table-striped table-bordered table-hover" id="table_fcDailyGrid">
					<thead>
						<tr>
							<th>事业部</th>
							<th>工厂代码</th>
							<th>线号</th>
							<th>当前批次</th>
							<th>FPC</th>
							<th>产品名称</th>
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