$(document).ready(
		function(){
			initDcQrQuery();
			bindSearchBthOnclick();
			initDcPast6Month();
		}
);

function initDcQrQuery(){
	$('#table_dcQrQuery').DataTable({
		"sScrollX": "100%",
		"scrollY": "400px",
		"scrollCollapse": "true",
		serverSide : false,
		searching : false,
		select : false,
		ordering:  false,
		paging: false,
		info: false
	});
}

function bindSearchBthOnclick(){
	$("#btn_search").click(function(){
		var qrCode = $('#qrCode').val();
		if($.trim(qrCode) == ''){
			layer.tips('请输入Qr码', $('#qrCode'), { time: 1000 });
			$('#qrCode').focus();
			return;
		}
		updateLabelValToSearching();
		getAppliedFileInfo();
		getUploadProduceReportInfo();
		getOutInfo();
	});
}

function initDcPast6Month(){
	$('#table_dcPast6Month').DataTable({
		"scrollX": true,
		"scrollY": "400px",
		"scrollCollapse": "true",
		//responsive : true,
		serverSide : true,//打开后台分页
		searching : false,
		searchDelay : 1000,
		select : false,
		ordering:  false,
		//"lengthMenu": [[5, 10, 15, 20], [5, 10, 15, 20]],
		dom : 'frt<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p><"clear">',
		"processing": true,
		"language": {
			 "processing": "数据正在查询中..."
	      },
		ajax : {
			type : 'post',
			url : '/report/ajaxGetDcQrCodeUsageRate',
			data : function(d) {
				$($("#dcPast6MonthForm").serializeArray()).each(
					function() {
						d[this.name] = this.value;
					}
				);
			},
			"dataSrc" : "result",
		},
		"columns": [
	            { "data": "filename" },
			    { "data": "applytime" }, 
			    { "data": "applycount" },
			    { "data": "firstuploadtime" },
			    { "data": "lastuploadtime" },
			    { "data": "uploadcount" },
			    { "data": "firstouttime" },
			    { "data": "lastouttime" },
			    { "data": "outcount" }
	    ]
	});
}

function updateLabelValToSearching(){
	$("td[id^='td_']").html('查询中...');
	$('#table_dcQrQuery').DataTable().draw();
}

function getAppliedFileInfo(){
	$('#tr_detailInfo').removeAttr("style")
	$.ajax({
		url: '/report/ajaxGetDcAppliedFileInfo',
		type: 'post',
		data:{'qrCode':$('#qrCode').val()},
		dataType: 'json',
		async: false,
		success: function(data){
			if(data.legal == true){
				if(data.result == null || data.result.FILENAME == null || data.result.FILENAME==''){
					$("td[id='td_fileName']").html('未查到数据');
					$("td[id='td_applyDate']").html('未查到数据');
					$("td[id='td_applyCount']").html('未查到数据');
				}
				else{
					$("td[id='td_fileName']").html(data.result.FILENAME);
					$("td[id='td_applyDate']").html(data.result.CREATETIME);
					$("td[id='td_applyCount']").html(data.result.APPLYCOUNT);
				}
			}
			else{
				$("td[id='td_fileName']").html('查询失败');
				$("td[id='td_applyDate']").html('查询失败');
				$("td[id='td_applyCount']").html('查询失败');
			}
			$('#table_dcQrQuery').DataTable().draw();
		},
		complete: function(){
			
		}
	});
}

function getUploadProduceReportInfo(){
	if($("td[id='td_fileName']").html() != '' && $("td[id='td_fileName']").html() != '未查到数据'){
		$.ajax({
			url: '/report/ajaxGetDcUploadProduceReportInfo',
			type: 'post',
			data:{'fileName': $("td[id='td_fileName']").html()},
			dataType: 'json',
			async: true,
			success: function(data){
				if(data.legal == true){
					if(data.result == null || data.result.LASTPRODATE == null || data.result.LASTPRODATE==''){
						$("td[id='td_lastUploadDate']").html('未查到数据');
					}
					else{
						$("td[id='td_lastUploadDate']").html(data.result.LASTPRODATE);
					}
					if(data.result == null || data.result.FIRSTPRODATE == null || data.result.FIRSTPRODATE==''){
						$("td[id='td_firstUploadDate']").html('未查到数据');
					}
					else{
						$("td[id='td_firstUploadDate']").html(data.result.FIRSTPRODATE);
					}
					if(data.result == null || data.result.UPLOADCOUNT == null || data.result.UPLOADCOUNT==''){
						$("td[id='td_uploadCount']").html('未查到数据');
					}
					else{
						$("td[id='td_uploadCount']").html(data.result.UPLOADCOUNT);
					}
				}
				else{
				}
				drawDcQrQueryTable();
			}
		});
	}
	else{
		$("td[id='td_uploadCount']").html('未查到数据');
		$("td[id='td_lastUploadDate']").html('未查到数据');
	}
	drawDcQrQueryTable();
}

function getOutInfo(){
	if($("td[id='td_fileName']").html() != '' && $("td[id='td_fileName']").html() != '未查到数据'){
		$.ajax({
			url: '/report/ajaxGetDcOutInfo',
			type: 'post',
			data:{'fileName': $("td[id='td_fileName']").html()},
			dataType: 'json',
			async: true,
			success: function(data){
				//console.log(data);
				if(data.legal == true){
					if(data.result == null || data.result.OUTCOUNT == null || data.result.OUTCOUNT==''){
					}
					else{
						$("td[id='td_outCount']").html(data.result.OUTCOUNT);
					}
					if(data.result == null || data.result.FIRSTOUTDATE == null || data.result.FIRSTOUTDATE==''){
					}
					else{
						$("td[id='td_firstOutDate']").html(data.result.FIRSTOUTDATE);
					}
					if(data.result == null || data.result.LASTOUTDATE == null || data.result.LASTOUTDATE==''){
					}
					else{
						$("td[id='td_lastOutDate']").html(data.result.LASTOUTDATE);
					}
				}
				else{
				}
				drawDcQrQueryTable();
			}
		});
	}
	else{
		$("td[id='td_outCount']").html('未查到数据');
	}
	drawDcQrQueryTable();
	
}

function drawDcQrQueryTable(){
	$('#table_dcQrQuery').DataTable().draw();
}