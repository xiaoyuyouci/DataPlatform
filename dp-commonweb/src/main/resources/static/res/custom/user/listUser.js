var addUserLayerIndex = null;
var updUserLayerIndex = null;
var userRoleLayerIndex = null;

$(document)
		.ready(
				function() {
					$('#dataTables-example')
							.DataTable(
									{
										responsive : true,
										serverSide : true,
										searching : true,
										searchDelay : 1000,
										select : true,
										dom : 'frt<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p><"clear">',
										ajax : {
											type : 'post',
											url : '/user/ajaxGetUser',
											data : function(d) {
												$(
														$("#userQueryForm")
																.serializeArray())
														.each(
																function() {
																	d[this.name] = this.value;
																});
											},
											"dataSrc" : "result"
										},
										columns : [ {
											data : "id"
										}, {
											data : "loginName"
										}, null, null ],
										columnDefs : [
												{
													"render" : function(data,
															type, row) {
														var d = new Date(
																row.creationTime.time);
														return d.getFullYear()
																+ '-'
																+ (d.getMonth() + 1)
																+ '-'
																+ d.getDate();
													},
													"targets" : 2
												},
												{
													"render" : function(data,
															type, row) {
														return row.gender == 0 ? "女"
																: "男";
													},
													"targets" : 3
												} ]
									});
				});

function search() {
	var searchLayerIndex = layer.load(0, {
		shade : [ 0.2, '#fff' ]
	});
	$('#dataTables-example').DataTable().ajax.reload(function(json) {
		layer.close(searchLayerIndex);
	});
}

function openAddUserLayer() {
	var url = "/user/addUser";
	$.ajax({
		url : url,
		type : "GET",
		dataType : "html",
		success : function(data) {
			addUserLayerIndex = layer.open({
				type : 1,
				title : [ '新增用户' ],
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

function openUpdUserLayer() {
	var row = $('#dataTables-example').DataTable().row('.selected').data();
	if (row) {
		var url = '/user/updUser';
		$.ajax({
			url : url,
			type : "GET",
			data : {
				"id" : row.id
			},
			dataType : "html",
			success : function(data) {
				updUserLayerIndex = layer.open({
					type : 1,
					title : [ '修改用户' ],
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

function delUser() {
	var row = $('#dataTables-example').DataTable().row('.selected').data();
	if (row) {
		layer.confirm("删除选中数据?", {
			icon : 3,
			title : '确认'
		}, function(index) {
			var searchLayerIndex = layer.load(0, {
				shade : [ 0.2, '#fff' ]
			});
			var aj = $.ajax({
				url : '/user/ajaxDelUser',// è·³è½¬å° action  
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
							offset : 'rb' //具体配置参考：offset参数项
							,
							content : '<div style="padding: 20px 80px;">'
									+ result.message + '</div>',
							btn : '知道了',
							btnAlign : 'c' //按钮居中
							,
							shade : 0 //不显示遮罩
							,
							yes : function() {
								layer.close(thisIndex);
							},
							time : 2000
						});
					} else {
						//刷新Datatable
						search();
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

function openUserRoleLayer() {
	var row = $('#dataTables-example').DataTable().row('.selected').data();
	if (row) {
		var url = '/user/listUserRole';
		$.ajax({
			url : url,
			type : "GET",
			data : {
				"userId" : row.id
			},
			dataType : "html",
			success : function(data) {
				updUserLayerIndex = layer.open({
					type : 1,
					title : [ '用户角色' ],
					area : [ '90%', '90%' ],
					scrollbar : false,
					maxmin : true,
					moveOut : false,
					content : data
				});
			}
		});
	}
	else {
		layer.alert('请选择一个用户', {
			icon : 5
		});
		return false;
	}
}
