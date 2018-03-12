$(document).ready(
		function(){
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
);

function updateLabelValToSearching(){
	$("label[id^='label_']").html('查询中...');
}

function getAppliedFileInfo(){
	$.ajax({
		url: '/report/ajaxGetDcAppliedFileInfo',
		type: 'post',
		data:{'qrCode':$('#qrCode').val()},
		dataType: 'json',
		async: false,
		success: function(data){
			if(data.legal == true){
				if(data.result == null || data.result.FILENAME == null || data.result.FILENAME==''){
					$("label[id='label_fileName']").html('未查到数据');
					$("label[id='label_applyDate']").html('未查到数据');
					$("label[id='label_applyCount']").html('未查到数据');
				}
				else{
					$("label[id='label_fileName']").html(data.result.FILENAME);
					$("label[id='label_applyDate']").html(data.result.CREATETIME);
					$("label[id='label_applyCount']").html(data.result.APPLYCOUNT);
				}
			}
			else{
				$("label[id='label_fileName']").html('查询失败');
				$("label[id='label_applyDate']").html('查询失败');
				$("label[id='label_applyCount']").html('查询失败');
			}
		},
		complete: function(){
			
		}
	});
}

function getUploadProduceReportInfo(){
	if($("label[id='label_fileName']").html() != '' && $("label[id='label_fileName']").html() != '未查到数据'){
		$.ajax({
			url: '/report/ajaxGetDcUploadProduceReportInfo',
			type: 'post',
			data:{'fileName': $("label[id='label_fileName']").html()},
			dataType: 'json',
			async: true,
			success: function(data){
				if(data.legal == true){
					if(data.result == null || data.result.LASTPRODATE == null || data.result.LASTPRODATE==''){
						$("label[id='label_lastUploadDate']").html('未查到数据');
					}
					else{
						$("label[id='label_lastUploadDate']").html(data.result.LASTPRODATE);
					}
					if(data.result == null || data.result.FIRSTPRODATE == null || data.result.FIRSTPRODATE==''){
						$("label[id='label_firstUploadDate']").html('未查到数据');
					}
					else{
						$("label[id='label_firstUploadDate']").html(data.result.FIRSTPRODATE);
					}
					if(data.result == null || data.result.UPLOADCOUNT == null || data.result.UPLOADCOUNT==''){
						$("label[id='label_uploadCount']").html('未查到数据');
					}
					else{
						$("label[id='label_uploadCount']").html(data.result.UPLOADCOUNT);
					}
				}
				else{
				}
			}
		});
	}
	else{
		$("label[id='label_uploadCount']").html('未查到数据');
		$("label[id='label_lastUploadDate']").html('未查到数据');
	}
}

function getOutInfo(){
	if($("label[id='label_fileName']").html() != '' && $("label[id='label_fileName']").html() != '未查到数据'){
		$.ajax({
			url: '/report/ajaxGetDcOutInfo',
			type: 'post',
			data:{'fileName': $("label[id='label_fileName']").html()},
			dataType: 'json',
			async: true,
			success: function(data){
				//console.log(data);
				if(data.legal == true){
					if(data.result == null || data.result.OUTCOUNT == null || data.result.OUTCOUNT==''){
					}
					else{
						$("label[id='label_outCount']").html(data.result.OUTCOUNT);
					}
					if(data.result == null || data.result.FIRSTOUTDATE == null || data.result.FIRSTOUTDATE==''){
					}
					else{
						$("label[id='label_firstOutDate']").html(data.result.FIRSTOUTDATE);
					}
					if(data.result == null || data.result.LASTOUTDATE == null || data.result.LASTOUTDATE==''){
					}
					else{
						$("label[id='label_lastOutDate']").html(data.result.LASTOUTDATE);
					}
				}
				else{
				}
			}
		});
	}
	else{
		$("label[id='label_outCount']").html('未查到数据');
	}
	
}