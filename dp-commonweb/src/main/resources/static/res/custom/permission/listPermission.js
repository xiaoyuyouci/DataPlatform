var addPermissionLayerIndex = null;
var updPermissionLayerIndex = null;
	
$(document).ready(function() {
	$('#dataTables-example').DataTable({
    	responsive: true,
    	serverSide: true,
    	searching: true,
     	searchDelay: 1000,
     	select: true,
    	"dom": 'frt<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p><"clear">',
    	ajax: {
        	type: 'post',
        	url: '/permission/ajaxGetPermission',
         	data: function (d) {
         		$($("#permissionQueryForm").serializeArray()).each(function(){
    	        	d[this.name]=this.value;
         		});
        	},
        	"dataSrc": "result"
      	},
     	columns: [
			{ data: "id" },
         	{ data: "name" },
         	{ data: "nameCn" },
          	{ data: "url" },
          	{ data: "filter" },
      	]
        });
});
    
    function search()
    {  
    	var searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
    	$('#dataTables-example').DataTable().ajax.reload( function ( json ) {
    		layer.close(searchLayerIndex);
    	});
    }
    
    function openAddPermissionLayer() {
    	var url = "/permission/addPermission";
    	$.ajax({
			url:url,
			type:"GET",
			dataType:"html",
			success:function(data){
				addPermissionLayerIndex = layer.open({
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
    
    function openUpdPermissionLayer(){
    	var row = $('#dataTables-example').DataTable().row( '.selected' ).data();
    	if(row){
    		var url = '/permission/updPermission';
    		$.ajax({
				url : url,
				type : "GET",
				data : {"id": row.id},
				dataType : "html",
				success : function(data) {
					updPermissionLayerIndex = layer.open({
						type : 1,
						title : [ '编辑权限' ],
						area : [ '90%', '90%' ],
						scrollbar : false,
						maxmin : true,
						moveOut : false,
						content : data
					});
				}
			});
    	}
    	else{
    		layer.alert('Please select a record', {icon: 5});
    		return false;
    	}
    }
    
	function delPermission() {
		var row = $('#dataTables-example').DataTable().row('.selected').data();
		if (row) {
			if(row.deletable == null || row.deletable == 0){
				//边缘弹出
				var thisIndex = layer.open({
					type : 1,
					offset : 'rb' //具体配置参考：offset参数项
					,
					content : '<div style="padding: 20px 80px;">' + '当前权限无法删除' + '</div>',
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
				return;
			}
			
			layer.confirm(
				"Delete the selected option?",
				{icon : 3, title : 'Confirm'},
					function(index) {
						var searchLayerIndex = layer.load(0, {
							shade : [ 0.2, '#fff' ]
						});
						var aj = $.ajax({
							url : '/permission/ajaxDelPermission',// è·³è½¬å° action  
							data : {
								id : row.id
							},
							type : 'post',
							cache : false,
							dataType : 'json',
							success : function(result) {
								if (!result.legal) {
									layer.tips(result.message,'#addPermissionForm input[name=\'name\']', {time : 2000});
								} else {
									//å·æ°Datatable
									search();
								}
							},
							error : function() {
								layer.alert('å¼å¸¸ï¼', {icon : 2});
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
			layer.alert('Please select a record', {icon: 5});
			return false;
		}
	}