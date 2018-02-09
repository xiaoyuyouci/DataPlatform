$(document).ready(
	function(){
		initTable();
	}
);

function initTable(){
	var table = $('#table_fcRealtimeGrid').DataTable();
	$.getJSON("/report/ajaxGetFcRealtimeGrid", function(result){
		table.destroy();  
        $('#table_fcRealtimeGrid').empty(); // 动态列
        table = $('#table_fcRealtimeGrid').DataTable( {
        	responsive : true,
			//serverSide : true,
			searching : false,
			searchDelay : 1000,
			select : true,
        	dom : 'frt<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p><"clear">',
            columns: result.columns,  
            data:    result.rows
        } );
	});
}