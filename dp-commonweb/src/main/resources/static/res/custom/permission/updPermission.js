$(document).ready(function() {
		$("#updPermissionForm button:submit").on('click', function(){
			$("#updPermissionForm").validate({
				submitHandler:function(form){
					var searchLayerIndex = null;
                    //ajaxæäº¤
                    $.ajax({
                        url:"/permission/ajaxUpdPermission",
                        data:$("#updPermissionForm").serialize(),
                        type:'post',
                        dataType:'json',
                        beforeSend:function () {
                            $("#updPermissionForm button:submit").attr({ disabled: "disabled" });
                            searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
                        },
                        complete:function(){
                            $("#updPermissionForm button:submit").removeAttr("disabled");
                            layer.close(searchLayerIndex);
                        },
                        success:function(result){
                        	if (!result.legal){
                        		layer.tips(result.message, '#updPermissionForm input[name=\'name\']', { time: 2000, tips: 3 });
        					} else {
        						search();
        						closeUpdPermissionLayer();
        					}
                        }

                    });
                },
                rules: {
                	name: {
                        required: true,
                        maxlength: 100
                    },
                    nameCn: {
                    	required: true,
                    	maxlength: 100
                    },
                    url: {
                        required: false,
                        maxlength: 100
                    },
                    filter:{
                    	required: false,
                        maxlength: 100                    }
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
	
	function closeUpdPermissionLayer(){
		layer.close(updPermissionLayerIndex);
	}