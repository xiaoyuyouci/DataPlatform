$(document).ready(function() {
		$("#addRoleForm button:submit").on('click', function(){
			$("#addRoleForm").validate({
				submitHandler:function(form){
					var searchLayerIndex = null;
                    $.ajax({
                        url:"/role/ajaxAddRole",
                        data:$("#addRoleForm").serialize(),
                        type:'post',
                        dataType:'json',
                        beforeSend:function () {
                            $("#addRoleForm button:submit").attr({ disabled: "disabled" });
                            searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
                        },
                        complete:function(){
                            $("#addRoleForm button:submit").removeAttr("disabled");
                            layer.close(searchLayerIndex);
                        },
                        success:function(result){
                        	if (!result.legal){
                        		layer.tips(result.message, '#addRoleForm input[name=\'name\']', { time: 2000, tips: 3 });
        					} else {
        						search();
        						closeAddRoleLayer();
        					}
                        }

                    });
                },
                rules: {
                	name: {
                        required: true,
                        maxlength: 30
                    }
                },
                showErrors: function (errorMap, errorList) {
                    var msg = "";
                    $.each(errorList, function (i, v) {
                        layer.tips(v.message, v.element, { time: 2000, tips: 3 });
                        return false;
                    });  
                },
                onfocusout: false
            });
		});
	});
	
	function closeAddRoleLayer(){
		layer.close(addRoleLayerIndex);
	}