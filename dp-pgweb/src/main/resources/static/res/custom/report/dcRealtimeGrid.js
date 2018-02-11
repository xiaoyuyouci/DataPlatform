$(document).ready(
	function(){
		initFcRealtimeGrid();
		
		$("#btn_search").click(function(){
			var searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
			$('#table_dcRealtimeGrid').DataTable().ajax.reload( function ( json ) {
			    //这里的json返回的是服务器的数据
				layer.close(searchLayerIndex);
			});
		});
	}
);

function initFcRealtimeGrid() {
	var table = $('#table_dcRealtimeGrid').DataTable({
		"autoWidth": true,//默认为true
		"scrollX": true,
		"scrollY": "400px",
		"scrollCollapse": "true",
		//responsive : true,
		serverSide : true,//打开后台分页
		searching : false,
		searchDelay : 1000,
		select : false,
		ordering:  false,
		dom : 'frt<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p><"clear">',
		ajax : {
			type : 'post',
			url : '/report/ajaxGetDcRealtimeGrid',
			data : function(d) {
				$($("#dcRealtimeGridQueryForm").serializeArray()).each(
					function() {
						d[this.name] = this.value;
					}
				);
			},
			"dataSrc" : "result"
		},
		columnDefs:[
			{
				    	//DC 代码
				    	"data" : "OID",
				    	"targets" : [0]
				    },
					{
						//名称
						"data" : "ONAME",
						"targets"	:	[1]
					},
					{
						//平板号
						"data": "OID",
						"targets"	:[2],
						"render" : function(data){
							return "";
						}
					},
					{
						//操作人员
						"data": "SCANUSER",
						"targets"	:[3],
						"render" : function(data){
							return (data && "null"!=data && null!= data)? data: "";
						}
					},
					{
						//业务单号
						"data":"NCCODE",
						"targets"	:[4]
					},
					{
						//车牌号
						"data":"BUS_NO",
						"targets"	:[5]
					},
					{
						//客户代码
						"data":"RECEIVENO",
						"targets"	:[6]
					},
					{
						//客户名称
						"data":"RECEIVENAME",
						"targets"	:[7]
					},
					{
						//应扫
						"data":"SUMSARTONSCANNING",
						"targets"	:[8]
					},
					{
						//当前扫
						"data":"SUMIDCODE",
						"targets"	:[9]
					},
					{
						//状态
						"data":"SUMIDCODE",
						"targets"	:[10],
						"render" : function(data,type,full){
							return "";
						}
					}
		]
	});
}

//计算数量相加是否正确
function countIsRight(a,b,c){
	if(a && b && c){
		if(a == (b + c)){
			return true;
		}
	}
	return false;
}