$(document).ready(
	function() {
		$('#dcDailyGridQueryForm input[name="startDate"]').datetimepicker(
			{
				language:"zh-CN",
				format: 'yyyy-mm-dd',
				minView : 2,
				autoclose : true
			}
		);
		$('#dcDailyGridQueryForm input[name="endDate"]').datetimepicker(
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
			$('#table_dcDailyGrid').DataTable().ajax.reload( function ( json ) {
			    //这里的json返回的是服务器的数据
				layer.close(searchLayerIndex);
			});
		});
		
		$("#btn_exportDcDailyGrid").click(function(){
			exportDcDailyGrid();
		});
	}
);

function initFcDailyGrid(){
	$('#table_dcDailyGrid').DataTable({
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
			url : '/report/ajaxGetDcDailyGrid',
			data : function(d) {
				$($("#dcDailyGridQueryForm").serializeArray()).each(
					function() {
						d[this.name] = this.value;
					}
				);
			},
			"dataSrc" : "result"
		},
		columnDefs:[
			{
				//业务单号
				"data" : "NCCODE",
				"targets" : [0]
			},
			{
				//发货方
				"data" : "OID",
				"targets" : [1]
			},
			{
				//发货方名称
				"data":"ONAME",
				"targets"	:[2]
			},
			{
				//车牌号
				"data":"BUS_NO",
				"targets"	:[3]
			},
			{
				//ship-to
				"data":"RECEIVENO",
				"targets"	:[4]
			},
			{
				//单据总量
				"data":"SUMQUANTITY",
				"targets"	:[5]
			},
			{
				//需扫数量
				"data":"SUMSARTONSCANNING",
				"targets"	:[6]
			},
			{
				//已扫数量
				"data":"SUMIDCODE",
				"targets"	:[7]
			},
			{
				//甩货总量
				"data":"SCANRECARGONUM",
				"targets"	:[8]
			},
			{
				//短提总量
				"data":"SCANSHORTNUM",
				"targets"	:[9],
			},
			{
				//无效码量
				"data":"SIGNSCANNUM",
				"targets"	:[10]
			},
			{
				//扫描率
				"data":"PERCENTS",
				"targets"	:[11]
			},
			{
				//制单日期
				"data":"MAKEDATE",
				"targets"	:[12],
				"render"	:function(data){
					return data;
				}
			},
			{
				//操作人员
				"data":"SCANUSER",
				"targets"	:[13],
				"render" : function(data){
					if(data){
						return data;
					}else{
						return "";
					}
				}
			},
			{
				//是否出库
				"data":"ISAUDIT",
				"targets"	:[14],
				"render" : function(data){
					if(data && data >0)
						return "是";
					return "否";
				}
			},
			{
				//是否作废
				"data":"ISBLANKOUT",
				"targets"	:[15],
				"render" : function(data){
					if(data && data >0)
						return "是";
					return "否";
				}
			},
			{
				//是否延迟
				"data":"ISDELAY",
				"targets"	:[16],
				"render" : function(data){
					if(data && data >0)
						return "是";
					return "否";
				}
			}
		]
	});
}

function exportDcDailyGrid(){
	var form=$("<form>");//定义一个form表单
	form.attr("style", "display:none");
	form.attr("target", "");
	form.attr("method", "post");
	form.attr("action", "/report/ajaxExportDcDailyGrid");
	
	$($("#dcDailyGridQueryForm").serializeArray()).each(function(){
        var input = $("<input>");
        input.attr("type", "hidden");
        input.attr("name", this.name);
        input.attr("value", this.value);
        form.append(input);
    });
	
	$("body").append(form);//将表单放置在web中
	form.submit();//表单提交
	$(form).remove();
}