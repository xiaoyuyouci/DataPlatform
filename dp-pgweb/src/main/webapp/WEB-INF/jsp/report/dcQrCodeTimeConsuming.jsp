<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript" src="res/custom/report/dcQrCodeTimeConsuming.js"></script>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="row">
					<form id="dcDailyGridQueryForm" onSubmit="return false;">
						<div class="form-group input-group col-md-6">
							<input id="qrCode" name="qrCode" class="form-control" placeholder="请输入QR码">
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
				<div>
					<label>条码所在文件：</label><label style="color:grey" id="label_fileName">请先查询QR码...</label>
				</div>
				<div>
					<label>此文件申请的时间：</label><label style="color:grey" id="label_applyDate">请先查询QR码...</label>
				</div>
				<div>
					<label>此文件条码申请的数量：</label><label style="color:grey" id="label_applyCount">请先查询QR码...</label>
				</div>
				<div>
					<label>此文件条码已经生产的数量：</label><label style="color:grey" id="label_uploadCount">请先查询QR码...</label>
				</div>
				<div>
					<label>此文件条码最后生产的时间：</label><label style="color:grey" id="label_lastUploadDate">请先查询QR码...</label>
				</div>
				<div>
					<label>此文件条码已经出库的数量：</label><label style="color:grey" id="label_outCount">请先查询QR码...</label>
				</div>
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->