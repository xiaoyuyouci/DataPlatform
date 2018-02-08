var addRolePermissionLayerIndex = null;
var selected = [];
$(document).ready(function() {
	$('#dataTables-rolePermission').DataTable({
		responsive : true,
		serverSide : true,
		searching : true,
		searchDelay : 1000,
		"dom" : 'frt<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p><"clear">',
		ajax : {
			type : 'post',
			url : '/role/ajaxGetRolePermission',
			data : function(d) {
				$($("#rolePermissionQueryForm").serializeArray()).each(function() {
					d[this.name] = this.value;
				});
			},
			"dataSrc" : "result"
		},
		columns : [ 
			{ data: "id" },
			{ data: "name" },
			{ data: "nameCn" },
		],
		"rowCallback": function( row, data ) {
            if ( $.inArray(data.id, selected) !== -1 ) {
                $(row).addClass('selected');
            }
        }
	});
	
	$('#dataTables-rolePermission tbody').on('click', 'tr', function () {
        var id = $('#dataTables-rolePermission').DataTable().row($(this)).data().id;
        var index = $.inArray(id, selected);
 
        if ( index === -1 ) {
            selected.push( id );
        } else {
            selected.splice( index, 1 );
        }
 
        $(this).toggleClass('selected');
    } );
});

function searchRolePermission() {
	var searchLayerIndex = layer.load(0, {
		shade : [ 0.2, '#fff' ]
	});
	$('#dataTables-rolePermission').DataTable().ajax.reload(function(json) {
		layer.close(searchLayerIndex);
	});
	selected = [];
}

function openAddRolePermissionLayer(){
	var url = "/role/listUnselectedRolePermission";
	$.ajax({
		url:url,
		data:{roleId:$('#rolePermissionQueryForm #roleId').val()},
		type:"GET",
		dataType:"html",
		success:function(data){
			addRolePermissionLayerIndex = layer.open({
				type:1,
				title:['新增权限'],
				area:['90%','90%'],
				scrollbar:false,
				maxmin:true,
				moveOut:true,
				content:data,
				end:function(){
				}
			});
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

function delRolePermission() {
	/**
	var array = [];
	$('#dataTables-rolePermission').DataTable().rows( '.selected' ).every( function ( rowIdx, tableLoop, rowLoop ) {
	    array.push(this.data().id);
		console.log(array);
	} );
	**/
	if(selected.length == 0){
		layer.alert('请选择一个或多个权限！', {icon: 6});
		return false;
	}
	else{
		//格式layer.confirm(content, options, yes, cancel)
		var searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
		layer.confirm("确定要删除这些权限吗？", 
				{icon: 3, title:'提示'}, 
				function(index){
					var aj = $.ajax( {  
					    url:'/role/ajaxDelRolePermission',// 跳转到 action  
					    data:{permissionIds: selected, roleId: $('#rolePermissionQueryForm #roleId').val()},
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
								searchRolePermission();
							}
							layer.close(searchLayerIndex);
						}, 
					    error : function() {  
					        layer.alert('异常！', {icon: 2});
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
}