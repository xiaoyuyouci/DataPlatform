<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript" src="res/custom/report/uidGrid.js"></script>
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
					<form id="uidGridQueryForm" onSubmit="return false;">
						<div class="form-group col-md-4">
							<input name="cartonUid" class="form-control" placeholder="请输入箱码UID">
						</div>
						<div class="form-group col-md-4">
							<input name="itemUid" class="form-control" placeholder="请输入ItemUID">
						</div>
						<div class="form-group input-group col-md-4">
							<input name="batchNo" class="form-control" placeholder="请输入批次号 ">
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
				<table width="100%" style="white-space: nowrap"
					class="table table-striped table-bordered table-hover"
					id="table_uidGrid">
					<thead>
						<tr>
							<th>FPC</th>
									<th id="title_text">Case GTIN</th>
									<th>产品名称</th>
									<th>Master data</th>
									<th>包材厂名称</th>
									<th>下载时间</th>
									<th>回传时间</th>
									<th>下载和回传文件的链接</th>
									<th>工厂名称</th>
									<th>线号</th>
									<th>批次</th>
									<th>工厂状态统计链接</th>
									<th>生产文件是否推送成功</th>
									<th>DC</th>
									<th>操作员</th>
									<th>业务单号</th>
									<th>客户代码</th>
									<th>客户名称</th>
									<th>订单推送时间</th>
									<th>开始扫描时间</th>
									<th>结束时间</th>
									<th>扫码率</th>
									<th>单据是否推送成功</th>
									<th>扫描单据统计页面链接</th>
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