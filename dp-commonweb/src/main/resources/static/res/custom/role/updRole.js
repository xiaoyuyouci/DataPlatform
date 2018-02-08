$(document).ready(function() {
		$("#updRoleForm button:submit").on('click', function(){
			$("#updRoleForm").validate({
				submitHandler:function(form){
					var searchLayerIndex = null;
                    //ajaxæäº¤
                    $.ajax({
                        url:"/role/ajaxUpdRole",
                        data:$("#updRoleForm").serialize(),
                        type:'post',
                        dataType:'json',
                        beforeSend:function () {
                            $("#updRoleForm button:submit").attr({ disabled: "disabled" });
                            searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
                        },
                        complete:function(){
                            $("#updRoleForm button:submit").removeAttr("disabled");
                            layer.close(searchLayerIndex);
                        },
                        success:function(result){
                        	if (!result.legal){
                        		layer.tips(result.message, '#updRoleForm input[name=\'name\']', { time: 2000, tips: 3 });
        					} else {
        						search();
        						closeUpdRoleLayer();
        					}
                        }

                    });
                },
                rules: {
                	name: {
                        required: true,
                        maxlength: 30
                    }                 }
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
	
	function closeUpdRoleLayer(){
		layer.close(updRoleLayerIndex);
	}