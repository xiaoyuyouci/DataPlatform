var selected = [];
$(document).ready(function() {
	var searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
	var table = $('#dataTables-unselectedRolePermission')
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
		"dom" : 'frt<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p><"clear">',
		ajax : {
			type : 'post',
			url : '/role/ajaxGetUnselectedRolePermission',
			data : function(d) {
				$($("#unselectedRolePermissionQueryForm").serializeArray()).each(function() {
					d[this.name] = this.value;
				});
			},
			"dataSrc" : "result"
		},
		columns : [ 
			{ data: "id" },
			{ data: "name" },
			{ data: "url" },
			{ data: "filter" },
		],
		"rowCallback": function( row, data ) {
            if ( $.inArray(data.id, selected) !== -1 ) {
                $(row).addClass('selected');
            }
        }
	});
	table.on( 'draw', function () {
	    //console.log( 'Redraw occurred at: '+new Date().getTime() );
	    if(searchLayerIndex){
	    	layer.close(searchLayerIndex);
	    	searchLayerIndex = null;
	    }
	} );
	
	$('#dataTables-unselectedRolePermission tbody').on('click', 'tr', function () {
        var id = $('#dataTables-unselectedRolePermission').DataTable().row($(this)).data().id;
        var index = $.inArray(id, selected);
 
        if ( index === -1 ) {
            selected.push( id );
        } else {
            selected.splice( index, 1 );
        }
 
        $(this).toggleClass('selected');
    } );
});

function search() {
	var searchLayerIndex = layer.load(0, {
		shade : [ 0.2, '#fff' ]
	});
	$('#dataTables-unselectedRolePermission').DataTable().ajax.reload(function(json) {
		layer.close(searchLayerIndex);
	});
	selected = [];
}

function addRolePermission() {
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
		layer.confirm("确定要添加这些权限吗？", 
				{icon: 3, title:'提示'}, 
				function(index){
					var aj = $.ajax( {  
					    url:'/role/ajaxAddRolePermission',// 跳转到 action  
					    data:{permissionIds: selected, roleId: $('#unselectedRolePermissionQueryForm #roleId').val()},
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
								//search();
								searchRolePermission();
								layer.close(addRolePermissionLayerIndex);
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