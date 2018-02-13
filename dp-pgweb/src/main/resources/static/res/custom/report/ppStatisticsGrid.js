$(document).ready(
	function() {
		$('#ppStatisticsGridQueryForm input[name="startDate"]').datetimepicker(
			{
				language:"zh-CN",
				format: 'yyyy-mm-dd',
				minView : 2,
				autoclose : true
			}
		);
		$('#ppStatisticsGridQueryForm input[name="endDate"]').datetimepicker(
				{
					language:"zh-CN",
					format: 'yyyy-mm-dd',
					minView : 2,
					autoclose : true
				}
		);
		
		initFcDailyGrid();
		
		$("#btn_search").click(function(){
			var searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
			$('#table_ppStatisticsGrid').DataTable().ajax.reload( function ( json ) {
			    //这里的json返回的是服务器的数据
				layer.close(searchLayerIndex);
			});
		});
	}
);

function initFcDailyGrid(){
	$('#table_ppStatisticsGrid').DataTable({
		"sScrollX": "100%",
		"scrollY": "400px",
		"scrollCollapse": "true",
		//responsive : true,
		serverSide : true,
		searching : false,
		searchDelay : 1000,
		select : false,
		ordering:  false,
		dom : 'frt<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p><"clear">',
		ajax : {
			type : 'post',
			url : '/report/ajaxGetPpStatisticsGrid',
			data : function(d) {
				$($("#ppStatisticsGridQueryForm").serializeArray()).each(
					function() {
						d[this.name] = this.value;
					}
				);
			},
			"dataSrc" : "result"
		},
		columnDefs:[
			 {
				    	//日期
				    	"data" : "date",
				    	"targets" : [0]
				    },
					{
						//supplier 
						"data" : "supplier",
						"targets"	:	[1]
					},
					{
						//线号
						"data":"linenum",
						"targets"	:[2]
					},
					{
						//ipc本地读取数据
						"data":"ipc_local",
						"targets"	:[3],
						"render"	: function(){
							return "";
						}
					},
					{
						//ipc上传数据
						"data":"ipc_upload",
						"targets"	:[4]
					},
					{
						//当天应给Aimia上传数据 
						"data":"up1_cc ",
						"targets"	:[5],
						"render"	: function(data,type,full){
							return "中国至中国 "+full.up1_cc+"<br>"+"日本至中国 "+full.up1_jc;
						}
					},
					{
						//当天实际给Aimia 上传数据
						"data":"up2_cc",
						"targets"	:[6],
						"render"	: function(data,type,full){
							return "中国至中国 "+full.up2_cc+"<br>"+"日本至中国 "+full.up2_jc;
						}
					},
					{
						//14天后需要给Aimia上传数据
						"data":"up_14",
						"targets"	:[7]
					}
		]
	});
}
