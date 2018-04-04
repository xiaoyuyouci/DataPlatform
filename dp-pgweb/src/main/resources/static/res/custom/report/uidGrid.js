$(document).ready(
	function() {
		$('#uidGridQueryForm input[name="startDate"]').datetimepicker(
			{
				language:"zh-CN",
				format: 'yyyy-mm-dd',
				minView : 2,
				autoclose : true
			}
		);
		$('#uidGridQueryForm input[name="endDate"]').datetimepicker(
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
			$('#table_uidGrid').DataTable().ajax.reload( function ( json ) {
			    //这里的json返回的是服务器的数据
				layer.close(searchLayerIndex);
			});
		});
		
		$('#uidGridQueryForm input').bind('input propertychange', function() { 
			var val = $(this).val();
			//$('#uidGridQueryForm input[name="cartonUid"]').val('');
			//$('#uidGridQueryForm input[name="itemUid"]').val('');
			//$('#uidGridQueryForm input[name="batchNo"]').val('');
			$('#uidGridQueryForm input').val('');
			$(this).val(val);
		});
	}
);

function initFcDailyGrid(){
    //console.log( 'Table initialisation start: '+new Date().getTime() );
	var searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
	var table = $('#table_uidGrid')
	.on( 'init.dt', function () {
		//console.log( ' your table has fully been initialised, data loaded and drawn: '+new Date().getTime() );
        layer.close(searchLayerIndex);
        searchLayerIndex = null;
    } )
    .on( 'page.dt', function () {
    	//console.log( 'table\'s paging state changes: '+new Date().getTime() );
    	searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
	} )
	.DataTable({
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
			url : '/report/ajaxGetUidGrid',
			data : function(d) {
				$($("#uidGridQueryForm").serializeArray()).each(
					function() {
						d[this.name] = this.value;
					}
				);
			},
			"dataSrc" : "result"
		},
		columnDefs:[
			{
				    	//FPC
				    	"data" : "MCODE",
				    	"targets" : [0]
				    },
					{
						//case gtin /Item GTIN
						"data" : "CASEGTIN",
						"targets"	:	[1],
						"render": function(data,type,full){
							/*if(gtin_flag == 2){
								return full.ITEMGTIN;
							}*/
							return data;
						}
					},
					{
						//产品中文名称
						"data":"MATERICALCHDES",
						"targets"	:[2]
					},
					{
						//Master data
						"data":"MCODE",
						"targets"	:[3],
						"render"	: function(data){
							return "";
						}
					},
					{
						//包材厂名称
						"data":"MCODE",
						"targets"	:[4],
						"render"	:function(data){
							return "";
						}
					},
					{
						//下载时间
						"data":"MCODE",
						"targets"	:[5],
						"render"	: function(data){
							return "";
						}
					},
					{
						//回传时间
						"data":"MCODE",
						"targets"	:[6],
						"render"	: function(data){
							return "";
						}
					},
					{
						//下载和回传文件的链接
						"data":"MCODE",
						"targets"	:[7],
						"render"	:function(data){
							return "";
						}
					},
					{
						//工厂名称
						"data":"ORGANNAME",
						"targets"	:[8]
					},
					{
						//线号
						"data":"LINECODE",
						"targets"	:[9]
					},
					{
						//批次
						"data":"BATCHNO",
						"targets"	:[10]
					},{
						//工厂统计状态链接的链接
						"data":"MCODE",
						"targets"	:[11],
						"render"	:function(data){
							return "";
						}
					},
					{
						//生产文件是否推送成功
						"data":"ISSENDARVATO",
						"targets"	:[12],
						"render"	:function(data){
							if(data && data >0){
								return "是";
							}
							return "";
						}
						
					},
					{
						//DC
						"data":"ONAME",
						"targets"	:[13],
					},
					{
						//操作员
						"data":"SCANUSER",
						"targets"	:[14],
						"render"	:function(data){
							if(data){
								return data;
							}else{
								return "";
							}
						}
					},
					{
						//业务单号
						"data":"NCCODE",
						"targets"	:[15]
					},
					{
						//客户代码
						"data":"RECEIVENO",
						"targets"	:[16]
					},
					{
						//客户名称
						"data":"RECEIVENAME",
						"targets"	:[17]
					},
					{
						//订单推送时间
						"data":"MAKEDATE",
						"targets"	:[18],
						"render"	:function(data){
							return data;
						}
					},
					{
						//开始扫描时间
						"data":"STARTSCANNINGTIME",
						"targets"	:[19],
						"render"	:function(data){
							return data;
						}
					},
					{
						//结束时间
						"data":"ENDSCANNINGTIME",
						"targets"	:[20],
						"render"	:function(data){
							return data;
						}
					},
					{
						//扫码率
						"data":"PERCENTS",
						"targets"	:[21]
					},
					{
						//单据是否推送成功
						"data":"MCODE",
						"targets"	:[22],
						"render"	:function(data){
							return "";
						}
					},
					{
						//扫描单据统计页面链接
						"data":"MCODE",
						"targets"	:[23],
						"render"	:function(data){
							return "";
						}
					}
		]
	});
	table.on( 'draw', function () {
	    //console.log( 'Redraw occurred at: '+new Date().getTime() );
	    if(searchLayerIndex){
	    	layer.close(searchLayerIndex);
	    	searchLayerIndex = null;
	    }
	} );
}
