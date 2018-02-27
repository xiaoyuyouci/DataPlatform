$(document).ready(
	function() {
		$('#fcDailyGridQueryForm input[name="date"]').datetimepicker(
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
			$('#table_fcDailyGrid').DataTable().ajax.reload( function ( json ) {
			    //这里的json返回的是服务器的数据
				layer.close(searchLayerIndex);
			});
		});
		
		$("#btn_exportFcDailyGrid").click(function(){
			exportFcDailyGrid();
		});
	}
);

function initFcDailyGrid(){
	$('#table_fcDailyGrid').DataTable({
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
			url : '/report/ajaxGetFcDailyGrid',
			data : function(d) {
				$($("#fcDailyGridQueryForm").serializeArray()).each(
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
//			    	"data" : "BU",
			    	"data" : "r_bu",
			    	"targets" : [0],
			    	"render" : function(data,type,full){
			    		if(!data || data =="null" || data.length<=0){
			    			return full.bu;
			    		}
			    		return data;
			    		
			    	}
			    },
				{
					//工厂代码
//					"data" : "PLANTCODE",
					"data" : "r_plantcode",
					"targets"	:	[1],
				  	"render" : function(data,type,full){
			    		if(!data || data =="null" || data.length<=0){
			    			return full.plantcode;
			    		}
			    		return data;
			    		
			    	}
				},
				{
					//线号
//					"data":"LINECODE",
					"data":"r_linecode",
					"targets"	:[2],
				  	"render" : function(data,type,full){
			    		if(!data || data =="null" || data.length<=0){
			    			return full.linecode;
			    		}
			    		return data;
			    		
			    	}
				},
				{
					//批次
//					"data":"BATCHNO",
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
					//箱瓶是否关联
					"data":"case_item",
					"targets":[6]
				},
				{
					//case count
//					"data":"COUNT2",
					"data":"case_package",
					"targets"	:[7]
				},
				{
					//CASE 打印机回传数量 未知
//					"data":"LINECODE",
					"data":"linecode",
					"targets"	:[8],
					"render" : function(){
						return "";
					}
				},
				{
					//CASE 扫描数量
//					"data":"CKQR",
					"data":"ckqrnum2",
					"targets"	:[9],
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
//					"data":"LINECODE",
					"data":"elqrnum2",
					"targets"	:[10],
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
//					"data":"LINECOD",
					"data":"scannum2",
					"targets"	:[11],
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
					"targets"	:[12],
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
					"targets"	:[13],
					"render" : function(){
						return "";
					}
				},
				{
					//case 生产合格率 count2/ckqrnum2
					"data":"case_percent",
					"targets"	:[14],
					"render" : function(data,type,full){
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
					"targets"	:[15],
					"render" : function(){
						return "";
					}
				},
				{
					//item 相机回传数量
					"data":"ckqrnum1",
					"targets"	:[16],
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
					"targets"	:[17],
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
					"targets"	:[18],
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
					"targets"	:[19],
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
					"targets"	:[20],
					"render" : function(){
						return "";
					}
				},
				{
					//生产合格率 count1/ckqrnum1
					"data":"item_percent",
					"targets"	:[21],
					"render" : function(data,type,full){
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
					"targets"	:[22],
					"render" : function(data,type,full){
						if(data && data == 'N'){
							return "<div class='backbg bg_red'>"+data+"</div>";
						}else if(data && data == 'Y'){
							return "<div class='backbg bg_green'>"+data+"</div>";
						}
						return data;
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

function checkWrongData(batchno){
	if(wrong_data !=null){
		var len = wrong_data.length;
		if(len >0){
			for(var i=0;i<len;i++){
				if(wrong_data[i].batchno == batchno){
					return true;
				}
			}
		}
	}
	return false;
}

function checkWarnData(batchno){
	if(warn_data !=null){
		var len = warn_data.length;
		if(len >0){
			for(var i=0;i<len;i++){
				if(warn_data[i].batchno == batchno){
					return true;
				}
			}
		}
	}
	return false;
}

function exportFcDailyGrid(){
	var form=$("<form>");//定义一个form表单
	form.attr("style", "display:none");
	form.attr("target", "");
	form.attr("method", "post");
	form.attr("action", "/report/ajaxExportFcDailyGrid");
	
	$($("#fcDailyGridQueryForm").serializeArray()).each(function(){
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