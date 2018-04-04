var addRoleLayerIndex = null;
var updRoleLayerIndex = null;
var rolePermissionLayerIndex = null;

$(document).ready(function() {
    //console.log( 'Table initialisation start: '+new Date().getTime() );
	var searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
	var table = $('#dataTables-example')
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
		responsive : true,
		serverSide : true,
		searching : true,
		searchDelay : 1000,
		select : true,
		"dom" : 'frt<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p><"clear">',
		ajax : {
			type : 'post',
			url : '/role/ajaxGetRole',
			data : function(d) {
				$($("#roleQueryForm").serializeArray()).each(function() {
					d[this.name] = this.value;
				});
			},
			"dataSrc" : "result"
		},
		columns : [ {
			data : "id"
		}, {
			data : "name"
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
});

function search() {
	var searchLayerIndex = layer.load(0, {
		shade : [ 0.2, '#fff' ]
	});
	$('#dataTables-example').DataTable().ajax.reload(function(json) {
		layer.close(searchLayerIndex);
	});
}

function openAddRoleLayer() {
	var url = "/role/addRole";
	$.ajax({
		url : url,
		type : "GET",
		dataType : "html",
		success : function(data) {
			addRoleLayerIndex = layer.open({
				type : 1,
				title : [ '新增角色' ],
				area:['90%','90%'],
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

function openUpdRoleLayer() {
	var row = $('#dataTables-example').DataTable().row('.selected').data();
	if (row) {
		var url = '/role/updRole';
		$.ajax({
			url : url,
			type : "GET",
			data : {
				"id" : row.id
			},
			dataType : "html",
			success : function(data) {
				updRoleLayerIndex = layer.open({
					type : 1,
					title : [ '编辑角色' ],
					area : [ '90%', '90%' ],
					scrollbar : false,
					maxmin : true,
					moveOut : false,
					content : data
				});
			}
		});
	} else {
		layer.alert('请选择一个角色', {
			icon : 5
		});
		return false;
	}
}

function delRole() {
	var row = $('#dataTables-example').DataTable().row('.selected').data();
	if (row) {
		if (row.deletable == null || row.deletable == 0) {
			//边缘弹出
			var thisIndex = layer.open({
				type : 1,
				offset : 'rb', //具体配置参考：offset参数项
				content : '<div style="padding: 20px 80px;">' + '当前角色无法删除' + '</div>',
				btn : '知道了',
				btnAlign : 'c', //按钮居中
				shade : 0, //不显示遮罩
				yes : function() {
					layer.close(thisIndex);
				},
				time : 2000
			});
			return;
		}

		layer.confirm("确定要删除这条记录吗?", {
			icon : 3,
			title : 'Confirm'
		}, function(index) {
			var searchLayerIndex = layer.load(0, {
				shade : [ 0.2, '#fff' ]
			});
			var aj = $.ajax({
				url : '/role/ajaxDelRole',// è·³è½¬å° action  
				data : {
					id : row.id
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(result) {
					if (!result.legal) {
						layer.tips(result.message,
								'#addRoleForm input[name=\'name\']', {
									time : 2000
								});
					} else {
						//å·æ°Datatable
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
		layer.alert('请选择一条数据', {
			icon : 5
		});
		return false;
	}
}

function listRolePermission(){
	var row = $('#dataTables-example').DataTable().row('.selected').data();
	if (row) {
		var url = "/role/listRolePermission";
		$.ajax({
			url : url,
			data : {
				roleId : row.id
			},
			type : "GET",
			dataType : "html",
			success : function(data) {
				rolePermissionLayerIndex = layer.open({
					type : 1,
					title : [ '角色权限' ],
					area:['90%','90%'],
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
	} else {
		layer.alert('请选择一条数据', {
			icon : 5
		});
		return false;
	}
}