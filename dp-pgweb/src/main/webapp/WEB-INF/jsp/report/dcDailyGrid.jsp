<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript" src="res/custom/report/dcDailyGrid.js"></script>
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
					<form id="dcDailyGridQueryForm" onSubmit="return false;">
						<div class="form-group col-md-2">
							<input name="startDate" class="form-control" placeholder="请选择开始时间">
						</div>
						<div class="form-group col-md-2">
							<input name="endDate" class="form-control" placeholder="请选择结束时间">
						</div>
						<div class="form-group col-md-2">
							<input name="ncCode" class="form-control" placeholder="请输入业务单">
						</div>
						<div class="form-group col-md-2">
							<input name="oid" class="form-control" placeholder="请输入收货方">
						</div>
						<div class="form-group col-md-2">
							<input name="batchNo" class="form-control" placeholder="请输入批次号">
						</div>
						<div class="form-group col-md-2">
							<input name="deliveryNo" class="form-control" placeholder="请输入交货号">
						</div>
						<div class="form-group col-md-2">
							<input name="receiveNo" class="form-control" placeholder="请输入客户代码">
						</div>
						<div class="form-group col-md-2">
							<input name="busNo" class="form-control" placeholder="请输入车牌号">
						</div>
						<div class="form-group col-md-2">
							<select class="form-control" name="needScan">
								<option value="">请选择需扫数量</option>
								<option value="0">为0</option>
								<option value="1">非0</option>
							</select>
						</div>
						<div class="form-group col-md-2">
							<select class="form-control" name="isBlankout">
								<option value="">请选择是否作废</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
						<div class="form-group col-md-2">
							<select class="form-control" name="isAudit">
								<option value="">请选择是否出库</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
						<div class="form-group col-md-2">
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
				<button type="button" id="btn_exportDcDailyGrid" class="btn btn-outline btn-default">导出</button>
				<table width="100%" style="white-space: nowrap"
					class="table table-striped table-bordered table-hover"
					id="table_dcDailyGrid">
					<thead>
						<tr>
							<th>业务单</th>
							<th>发货方</th>
							<th>发货方名称</th>
							<th>车牌号</th>
							<th>Ship-to</th>
							<th>单据总量</th>
							<th>需扫数量</th>
							<th>已扫数量</th>
							<th>甩货总量</th>
							<th>短提总量</th>
							<th>无效码量</th>
							<th>扫描率（%）</th>
							<th>制单日期</th>
							<th>操作人员</th>
							<th>是否出库</th>
							<th>是否作废</th>
							<th>是否延迟</th>
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