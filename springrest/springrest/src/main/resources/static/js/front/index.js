//检查空值
function checkEmpty(str) {
	if (str == null || str.replace(/^\s+,""/).replace(/^\s+$/, "") == "") {
		return true;
	} else {
		return false;
	}
}
$( "#pretime" ).datepicker({
		regional  :"zh-CN",
	    minDate:moment().format('YYYY-MM-DD'),
	    dateFormat: "yy-mm-dd",
		onClose: function(selectedDate) {
							if(checkEmpty(selectedDate))
								 $( "#posttime" ).datepicker( "option", "minDate", moment().add(1, 'd').format('YYYY-MM-DD'));
				
							else
								 $( "#posttime" ).datepicker( "option", "minDate", moment(selectedDate).add(1, 'd').format('YYYY-MM-DD'));
					    }
			});
	$( "#posttime" ).datepicker({
		regional  :"zh-CN",
	    minDate:moment().add(1, 'd').format('YYYY-MM-DD'),
	    dateFormat: "yy-mm-dd",
		onClose: function(selectedDate) {
			if(checkEmpty(selectedDate))
				 $( "#pretime" ).datepicker( "option", "maxDate", null);
			else
				 $( "#pretime" ).datepicker( "option", "maxDate", moment(selectedDate).subtract(1, 'd').format('YYYY-MM-DD'));
	    }
	})
//预定房间
$("#book-btn").click(function() {
	if(checkEmpty($("#pretime").val())){
		alert("入住时间不能为空！");
		return;
	}
	else if(checkEmpty($("#posttime").val())){
		alert("退房时间不能为空！");
		return;
	}

	$("#form-pretime").val(parseInt(new Date($("#pretime").val())/1000));
	$("#form-posttime").val(parseInt(new Date($("#posttime").val())/1000));
	$("#form-typenumber").val($('#type').val());
	
	$("#bookform").submit();
	})