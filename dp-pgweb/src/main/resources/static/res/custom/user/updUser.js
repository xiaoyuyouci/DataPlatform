$(document).ready(function() {
		$("#updUserForm button:submit").on('click', function(){
			$("#updUserForm").validate({
				submitHandler:function(form){
					var searchLayerIndex = null;
                    $.ajax({
                        url:"/user/ajaxUpdUser",
                        data:$("#updUserForm").serialize(),
                        type:'post',
                        dataType:'json',
                        beforeSend:function () {
                            $("#updUserForm button:submit").attr({ disabled: "disabled" });
                            searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
                        },
                        complete:function(){
                            $("#updUserForm button:submit").removeAttr("disabled");
                            layer.close(searchLayerIndex);
                        },
                        success:function(result){
                        	if (!result.legal){
                        		layer.tips(result.message, '#updUserForm input[name=\'loginName\']', { time: 2000 });
        					} else {
        						search();
        						closeUpdUserLayer();
        					}
                        }

                    });
                },
                rules: {
                	loginName: {
                        required: true,
                        maxlength: 100
                    }
                },
                showErrors: function (errorMap, errorList) {
                    var msg = "";
                    $.each(errorList, function (i, v) {
                        layer.tips(v.message, v.element, { time: 2000 });
                        return false;
                    });  
                },
                onfocusout: false
            });
		});
	});
	
	function closeUpdUserLayer(){
		console.log(updUserLayerIndex);
		layer.close(updUserLayerIndex);
	}
	