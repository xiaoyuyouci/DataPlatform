$(document).ready(
	function(){
		initFcRealtimeGrid();
		
		$("#btn_search").click(function(){
			var searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
			$('#table_fcRealtimeGrid').DataTable().ajax.reload( function ( json ) {
			    //这里的json返回的是服务器的数据
				layer.close(searchLayerIndex);
			});
		});
		
		$("#btn_exportFcRealtimeGrid").click(function(){
			exportFcRealtimeGrid();
		});
	}
);

function initFcRealtimeGrid() {
	var table = $('#table_fcRealtimeGrid').DataTable({
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
			url : '/report/ajaxGetFcRealtimeGrid',
			data : function(d) {
				$($("#fcRealtimeGridQueryForm").serializeArray()).each(
					function() {
						d[this.name] = this.value;
					}
				);
			},
			"dataSrc" : "result"
		},
		columnDefs:[
			{
				//事业部
				"data":"bu",
				"targets":[0],
				"width": "20%"
			},
			{
				//工厂代码
				"data":"plantcode",
				"targets":[1]
			},
			{
				//线号
				"data":"linecode",
				"targets":[2]
			},
			{
				//批次
				"data":"batchno",
				"targets"	:[3]
			},
			{
				//FPC
				"data":"mcode",
				"targets"	:[4]
			},
			{
				//产品名称
				"data":"productname",
				"targets"	:[5]
			},
			{
				//当前状态
				"data":"status",
				"targets"   :[6],
				"render"	: function(data){
						if(data ==1){
							return "<span class='btn btn-success btn-sm'> 生 产 </span>"
						}
						return "<span class='btn btn-warning btn-sm'> 暂 停 </span>"
				}
			},
			{
				//时间
				"data":"up_time",
				"targets"	:[7]
			},
			{
				//想瓶是否关联
				"data":"case_item",
				"targets":[8]
			},
			{
				//case count
			//	"data":"COUNT2",
				"data":"case_package",
				"targets"	:[9]
			},
			{
				//CASE 打印机回传数量 未知
				"data":"linecode",
				"targets"	:[10],
				"render" : function(){
					return "";
				}
			},
			{
				//CASE 扫描数量
			//	"data":"CKQR",
				"data":"ckqrnum2",
				"targets"	:[11],
				"render" : function(data,type,full){
					if(countIsRight(full.ckqrnum2,full.elqrnum2,full.scannum2)){
						return full.ckqrnum2;
					}else if(full.ckqrnum2){
						return "<div class='backbg bg_red'>"+full.ckqrnum2+"</div>";
					}
					return "";
				}
			},
			{
				//case 剔除数量
			//	"data":"LINECODE",
				"data":"elqrnum2",
				"targets"	:[12],
				"render" : function(data,type,full){
					if(countIsRight(full.ckqrnum2,full.elqrnum2,full.scannum2)){
						return full.elqrnum2;
					}else if(full.elqrnum2){
						return "<div class='backbg bg_red'>"+full.elqrnum2+"</div>";
					}
					return "";
				}
			},
			{
				//case 本地数量
			//	"data":"LINECOD",
				"data":"scannum2",
				"targets"	:[13],
				"render" :function(data,type,full){
					if(countIsRight(full.ckqrnum2,full.elqrnum2,full.scannum2)){
						return full.scannum2;
					}else if(full.scannum2){
						return "<div class='backbg bg_red'>"+full.scannum2+"</div>";
					}
					if(full.count2 && full.scannum2 && full.scannum2> full.count2){
						return "<div class='backbg bg_red'>"+full.scannum2+"</div>";
					}
					return "";
				}
			},
			{
				//case后台系统数量
				"data":"count2",
				"targets"	:[14],
				"render" :function(data,type,full){
					if(full.count2 && full.scannum2 && full.scannum2> full.count2){
						return "<div class='backbg bg_red'>"+full.count2+"</div>";
					}
					return data;
				}
			},
			{
				//case context接收数量 （以后由接口来给）
				"data":"linecode",
				"targets"	:[15],
				"render" : function(){
					return "";
				}
			},
			{
				//case 生产合格率 count2/ckqrnum2
				"data":"case_percent",
				"targets"	:[16],
				"render" : function(data){
					if(data){
						if(data >= 99.75){
							return "<div class='backbg bg_green'>"+data+"%</div>";
						}else if(data >= 95){
							return "<div class='backbg bg_yellow'>"+data+"%</div>";
						}else{
							return "<div class='backbg bg_red'>"+data+"%</div>";
						}
					}
					return "";
				}
			},
			{
				//item 相机扫描数量 未知
				"data":"linecode",
				"targets"	:[17],
				"render" : function(){
					return "";
				}
			},
			{
				//item 相机回传数量
				"data":"ckqrnum1",
				"targets"	:[18],
				"render" : function(data,type,full){
					if(countIsRight(full.ckqrnum1,full.elqrnum1,full.scannum1)){
						return full.ckqrnum1;
					}else if(full.ckqrnum1){
						return "<div class='backbg bg_red'>"+full.ckqrnum1+"</div>";
					}
					return "";
				}
			},
			{
				//item 剔除数量
				"data":"elqrnum1",
				"targets"	:[19],
				"render" : function(data,type,full){
					if(countIsRight(full.ckqrnum1,full.elqrnum1,full.scannum1)){
						return full.elqrnum1;
					}else if(full.elqrnum1){
						return "<div class='backbg bg_red'>"+full.elqrnum1+"</div>";
					}
					return "";
				}
			},
			{
				//item ipc本地数量
				"data":"scannum1",
				"targets"	:[20],
				"render" :function(data,type,full){
					if(countIsRight(full.ckqrnum1,full.elqrnum1,full.scannum1)){
						return full.scannum1;
					}else if(full.scannum1){
						return "<div class='backbg bg_red'>"+full.scannum1+"</div>";
					}
					return "";
				}
			},
			{
				//item 后台系统数量 
				"data":"count1",
				"targets"	:[21],
				"render" :function(data,type,full){
					if(full.count1 && full.scannum1 && full.scannum1> full.count1){
						return "<div class='backbg bg_red'>"+full.count1+"</div>";
					}
					return data;
				}
			},
			{
				//Item Context接收数量 后续接口提供
				"data":"linecode",
				"targets"	:[22],
				"render" : function(){
					return "";
				}
			},
			{
				//生产合格率 count1/ckqrnum1
				"data":"item_percent",
				"targets"	:[23],
				"render" : function(data){
					if(data){
						if(data >= 99.85){
							return "<div class='backbg bg_green'>"+data+"%</div>";
						}else{
							return "<div class='backbg bg_red'>"+data+"%</div>";
						}
					}
					return "";
				}
			},
			{
				//是否符合case count
				"data":"istrue",
				"targets"	:[24],
				"render" : function(data){
					if(data && data == 'N'){
						return "<div class='backbg bg_red'>"+data+"</div>";
					}else if(data && data == 'Y'){
						return "<div class='backbg bg_green'>"+data+"</div>";
					}
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

function exportFcRealtimeGrid(){
	var form=$("<form>");//定义一个form表单
	form.attr("style", "display:none");
	form.attr("target", "");
	form.attr("method", "post");
	form.attr("action", "/report/ajaxExportFcRealtimeGrid");
	
	$($("#fcRealtimeGridQueryForm").serializeArray()).each(function(){
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












/*$(document).ready(
	function(){
		initTable();
	}
);

function initTable(){
	var table = $('#table_fcRealtimeGrid').DataTable();
	$.getJSON("/report/ajaxGetFcRealtimeGrid", function(result){
		table.destroy();  
        $('#table_fcRealtimeGrid').empty(); // 动态列
        table = $('#table_fcRealtimeGrid').DataTable( {
        	responsive : true,
			//serverSide : true,
			searching : false,
			searchDelay : 1000,
			select : true,
        	dom : 'frt<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p><"clear">',
            columns: result.columns,  
            data:    result.rows
        } );
	});
}*/