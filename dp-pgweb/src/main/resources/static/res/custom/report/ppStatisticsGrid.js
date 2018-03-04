$(document).ready(
	function() {
		//initFcDailyGrid();
		getData();
		
		/*$("#btn_search").click(function(){
			var searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
			$('#table_ppStatisticsGrid').DataTable().ajax.reload( function ( json ) {
			    //这里的json返回的是服务器的数据
				layer.close(searchLayerIndex);
			});
		});*/
	}
);

function getData(){
	$.ajax({
		type: 'POST',
		url : '/report/ajaxGetPpStatisticsGrid',
		data: {},
		dataType:'json',
		success: function(data,textStatus,jqXHR){
			//console.log(data);
			if(data.legal == true){
				drawTable(data.result);
			}
			else{
				
			}
		}
	});
}

function drawTable(data){
	//console.log(data);
	//console.log(data.part1);
	var table = $('#table_ppStatisticsGrid').DataTable({
		"sScrollX": "100%",
		"scrollY": "400px",
		"scrollCollapse": "true",
		ordering:  false,
		searching: false,
		paging: false,
		info: false,
		columns: [
		            { data: 'date' },
		            { data: 'FACTORYNO' },
		            { data: 'LINESNO' },
		            { data: 'c1' },
		            { data: 'IPCUPLOADCOUNT' },
		            { data: 'c2' },
		            { data: 'c3' },
		            { data: 'c4' }
		        ],
		        columnDefs: [{
		            targets: [5,6,7],
		            createdCell: function (td, cellData, rowData, row, col) {
		            	//console.log(rowData);
		                var rowspan = rowData.rowspan;
		                if (rowspan >= 1) {
		                    $(td).attr('rowspan', rowspan);
		                    if(col == 5){
		                    	//console.log(rowData.CTOC_P);
		                    	$(td).html('中国至中国:'+rowData.CTOC_P+'<br>日本至中国:'+rowData.JTOC_P);
		                    }
		                    else if(col == 6){
		                    	$(td).html('中国至中国:'+rowData.CTOC_A+'<br>日本至中国:'+rowData.JTOC_A);
		                    }
		                    else if(col == 7){
		                    	$(td).html(rowData.D14);
		                    }
		                }
		                if (rowspan == 0) {
		                    $(td).remove();
		                }
		            }
		        }]
	});
	table.clear();
	table.rows.add(data.part1).draw();
	//console.log(table.cell(0,5).node());
	//$(table.cell(0,5).node()).attr('rowspan', 2);
}

/*function initFcDailyGrid(){
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
		"paging":   false,
	    "ordering": false,
	    "info":     false,
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
			"dataSrc" : "result.part1"
		},
		columnDefs:[
			 {
				    	//日期
				    	"data" : "date",
				    	"targets" : [0]
				    },
					{
						//supplier 
						"data" : "factoryno",
						"targets"	:	[1]
					},
					{
						//线号
						"data":"linesno",
						"targets"	:[2]
					},
					{
						//ipc上传数据
						"data":"ipcuploadcount",
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
}*/
