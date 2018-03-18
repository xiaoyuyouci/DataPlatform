$(document).ready(function() {
	changeSpringIdOrClassName();
	
		$("#addScheduleJobForm button:submit").on('click', function(){
			$("#addScheduleJobForm").validate({
				submitHandler:function(form){
					var searchLayerIndex = null;
                    //ajaxæäº¤
                    $.ajax({
                        url:"/scheduleJob/ajaxAddScheduleJob",
                        data:$("#addScheduleJobForm").serialize(),
                        type:'post',
                        dataType:'json',
                        beforeSend:function () {
                            $("#addScheduleJobForm button:submit").attr({ disabled: "disabled" });
                            searchLayerIndex = layer.load(0, {shade: [0.2,'#fff']});
                        },
                        complete:function(){
                            $("#addScheduleJobForm button:submit").removeAttr("disabled");
                            layer.close(searchLayerIndex);
                        },
                        success:function(result){
                        	if (!result.legal){
                        		//console.log(result);
                        		$.each($.parseJSON(result.message), function (i, v) {
                                    layer.tips(v.msg, $('#'+v.element), { time: 2000, tips: 3 });
                                    return false;
                                });
        					} else {
        						searchScheduleJob();
        						closeAddScheduleJobLayer();
        					}
                        }

                    });
                },
                rules: {
                	jobGroup: {
                        required: true,
                        maxlength: 40
                    },
                    jobName: {
                        required: true,
                        maxlength: 40
                    },
                    description:{
                    	maxlength: 40
                    },
                    cronExpression: {
                    	required: true,
                    	maxlength: 40
                    },
                    /*beanClass: {
                    	required: true,
                    	maxlength: 40
                    },
                    springId: {
                    	required: true,
                    	maxlength: 40
                    },*/
                    methodName: {
                    	required: true,
                    	maxlength: 40
                    },
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
	
	function closeAddScheduleJobLayer(){
		layer.close(addScheduleJobLayerIndex);
	}

	function changeSpringIdOrClassName(){
		$("#springId").on('keyup', function(){
			if($("#springId").val().length > 0){
				$("#beanClass").attr("readonly","readonly");
				//$("#beanClass").rules("remove");
			}
			else{
				$("#beanClass").removeAttr("readonly","readonly");
				//$("#beanClass").rules("add",{required:true,maxlength:40,messages:{maxlength:"最大长度为40字节"}});
			}
		});
		$("#beanClass").on('keyup', function(){
			if($("#beanClass").val().length > 0){
				$("#springId").attr("readonly","readonly");
				//$("#springId").rules("remove");
			}
			else{
				$("#springId").removeAttr("readonly","readonly");
				//$("#springId").rules("add",{required:true,maxlength:40,messages:{maxlength:"最大长度为40字节"}});
			}
		});
	}
