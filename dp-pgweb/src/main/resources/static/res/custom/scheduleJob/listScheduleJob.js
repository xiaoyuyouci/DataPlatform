var addScheduleJobLayerIndex = null;
var updScheduleJobLayerIndex = null;
var scheduleJobRoleLayerIndex = null;

$(document).ready(
	function() {
		initDatatable();
		bindOnclick();
	}
);

function bindOnclick(){
	$('#btn_add').on('click', function(){
		openAddScheduleJobLayer();
	});
	$('#btn_edit').on('click', function(){
		openUpdScheduleJobLayer();
	});
	$('#btn_del').on('click', function(){
		delScheduleJob();
	});
	$('#btn_start').on('click', function(){
		run('start');
	});
	$('#btn_end').on('click', function(){
		run('stop');
	});
}

function initDatatable(){
	$('#table_scheduleJob').DataTable(
			{
				responsive : false,
				serverSide : false,
				searching : true,
				searchDelay : 1000,
				ordering:  false,
				select : true,
				"sScrollX": "100%",
				"scrollY": "400px",
				"scrollCollapse": "true",
				dom : 'frt<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p><"clear">',
				ajax : {
					type : 'post',
					url : '/scheduleJob/ajaxGetScheduleJob',
					data : function(d) {
						$($("#scheduleJobQueryForm").serializeArray()).each(
							function() {
								d[this.name] = this.value;
							}
						);
					},
					"dataSrc" : "result"
				},
				columns : [ {data : "id"}, 
				            {data : "jobGroup"},
				            {data : "jobName"},
				            {data : "description"},
				            {data : "isConcurrent"},
				            {data : "jobStatusInMemory"},
				            {data : "jobStatus"},
				            {data : "cronExpression"},
				            {data : "springId"},
				            {data : "beanClass"},
				            {data : "methodName"}
				],
				columnDefs:[
					{ 
						"targets": [4], // 目标列位置，下标从0开始
						"data": "isConcurrent", // 数据列名
						"render": function(data, type, full){
							return (data != null && data == '1')? "是": "否";
						}
					},
					{ 
						"targets": [5], // 目标列位置，下标从0开始
						"data": "jobStatusInMemory", // 数据列名
						"render": function(data, type, full){
							return (data != null && data == '1')? "是": "否";
						}
					},
					{ 
						"targets": [6], // 目标列位置，下标从0开始
						"data": "jobStatus", // 数据列名
						"render": function(data, type, full){
							return (data != null && data == '1')? "是": "否";
						}
					}
				]
			}
		);
}

function searchScheduleJob() {
	var searchLayerIndex = layer.load(0, {
		shade : [ 0.2, '#fff' ]
	});
	$('#table_scheduleJob').DataTable().ajax.reload(function(json) {
		layer.close(searchLayerIndex);
	});
}

function openAddScheduleJobLayer() {
	var url = "/scheduleJob/toAddScheduleJob";
	$.ajax({
		url : url,
		type : "GET",
		dataType : "html",
		success : function(data) {
			addScheduleJobLayerIndex = layer.open({
				type : 1,
				title : [ '新增定时任务' ],
				area : [ '90%', '90%' ],
				scrollbar : false,
				maxmin : true,
				moveOut : true,
				content : data,
				end : function() {
				}
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

function openUpdScheduleJobLayer() {
	var row = $('#table_scheduleJob').DataTable().row('.selected').data();
	if (row) {
		var url = '/scheduleJob/toUpdScheduleJob';
		$.ajax({
			url : url,
			type : "GET",
			data : {
				"id" : row.id
			},
			dataType : "html",
			success : function(data) {
				updScheduleJobLayerIndex = layer.open({
					type : 1,
					title : [ '修改定时任务' ],
					area : [ '90%', '90%' ],
					scrollbar : false,
					maxmin : true,
					moveOut : false,
					content : data
				});
			}
		});
	} else {
		layer.alert('Please select a record', {
			icon : 5
		});
		return false;
	}
}

function delScheduleJob() {
	var row = $('#table_scheduleJob').DataTable().row('.selected').data();
	if (row) {
		layer.confirm("删除选中数据?", {
			icon : 3,
			title : '确认'
		}, function(index) {
			var searchLayerIndex = layer.load(0, {
				shade : [ 0.2, '#fff' ]
			});
			var aj = $.ajax({
				url : '/scheduleJob/ajaxDelScheduleJob',// è·³è½¬å° action  
				data : {
					id : row.id
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(result) {
					if (!result.legal) {
						//边缘弹出
						var thisIndex = layer.open({
							type : 1,
							offset : 'rb', //具体配置参考：offset参数项
							content : '<div style="padding: 20px 80px;">' + result.message + '</div>',
							btn : '知道了',
							btnAlign : 'c', //按钮居中
							shade : 0, //不显示遮罩
							yes : function() {
								layer.close(thisIndex);
							},
							time : 2000
						});
					} else {
						//刷新Datatable
						searchScheduleJob();
					}
				},
				error : function() {
					layer.alert('å¼å¸¸ï¼', {
						icon : 2
					});
					layer.close(index);
					layer.close(searchLayerIndex);
				},
				complete : function() {
					layer.close(index);
					layer.close(searchLayerIndex);
				}
			});

		});
	} else {
		layer.alert('Please select a record', {
			icon : 5
		});
		return false;
	}
}

function run(cmd) {
	var row = $('#table_scheduleJob').DataTable().row( '.selected' ).data();
	if(row){
		layer.confirm("确定要操作所选记录吗？", 
				{icon: 3, title:'提示'}, 
				function(index){
					var searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
					var aj = $.ajax( {  
					    url:'/scheduleJob/ajaxChangeScheduleJobStatus',// 跳转到 action  
					    data:{id:row.id, cmd:cmd},  
					    type:'post',  
					    cache:false,  
					    dataType:'json',  
					    success: function(results){
					    	var result = results;
							if (!result.legal){
								//边缘弹出
								var thisIndex = layer.open({
									type : 1,
									offset : 'rb', //具体配置参考：offset参数项
									content : '<div style="padding: 20px 80px;">' + result.message + '</div>',
									btn : '知道了',
									btnAlign : 'c', //按钮居中
									shade : 0, //不显示遮罩
									yes : function() {
										layer.close(thisIndex);
									},
									time : 2000
								});
								layer.close(searchLayerIndex);
							} 
							else {
								//刷新Datatable
								searchScheduleJob();
							}
							layer.close(searchLayerIndex);
						}, 
					    error : function() {  
					        layer.alert('处理异常，请重新登录', {icon: 2});
					        layer.close(searchLayerIndex);
					    },  
						complete: function(){
							layer.close(index);
						}
					});
				}, 
				function(){
					layer.close(searchLayerIndex);
				});
	}
	else{
		layer.alert('请选择一条记录', {icon: 3});
		return false;
	}
}