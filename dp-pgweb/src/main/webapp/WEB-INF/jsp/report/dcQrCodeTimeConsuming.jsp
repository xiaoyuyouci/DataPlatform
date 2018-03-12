<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript" src="res/custom/report/dcQrCodeTimeConsuming.js"></script>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
					<form id="dcQrCodeTimeConsumingForm" onSubmit="return false;">
						<div class="form-group input-group col-md-4">
							<input id="qrCode" name="qrCode" class="form-control" placeholder="请输入QR码">
							<span class="input-group-btn">
								<button id="btn_search" class="btn btn-default" type="button">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
					</form>
			</div>
			<!-- /.panel-heading -->

			<div class="panel-body">
				<table id="table_dcQrQuery" width="100%" style="white-space: nowrap" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
						<th>所在文件</th>
						<th>申请时间</th>
						<th>申请数量</th>
						<th>首次生产时间</th>
						<th>最后生产时间</th>
						<th>已生产数量</th>
						<th>首次出库时间</th>
						<th>最后出库时间</th>
						<th>已出库数量</th>
						</tr>
					</thead>
					<tbody>
						<tr id="tr_detailInfo" style="display:none">
							<td id="td_fileName"></td>
							<td id="td_applyDate"></td>
							<td id="td_applyCount"></td>
							<td id="td_firstUploadDate"></td>
							<td id="td_lastUploadDate"></td>
							<td id="td_uploadCount"></td>
							<td id="td_firstOutDate"></td>
							<td id="td_lastOutDate"></td>
							<td id="td_outCount"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div id="div_past6Month" class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				过去六个月的码使用情况
				<form id="dcPast6MonthForm" onSubmit="return false;"></form>
			</div>
			<div class="panel-body">
				<table id="table_dcPast6Month" width="100%" style="white-space: nowrap" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>文件</th>
							<th>申请时间</th>
							<th>申请数量</th>
							<th>已生产数量</th>
							<th>已出库数量</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>