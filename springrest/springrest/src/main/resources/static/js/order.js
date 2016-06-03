$( "#pretime" ).datepicker({
	regional  :"zh-CN",
    //minDate:moment().format('YYYY-MM-DD'),
    dateFormat: "yy-mm-dd",
	onClose: function(selectedDate) {
						if(checkEmpty(selectedDate))
							 $( "#posttime" ).datepicker( "option", "minDate", null);
			
						else
							 $( "#posttime" ).datepicker( "option", "minDate", moment(selectedDate).add(1, 'd').format('YYYY-MM-DD'));
						
				    }
		});
$( "#posttime" ).datepicker({
	regional  :"zh-CN",
    //minDate:moment().add(1, 'd').format('YYYY-MM-DD'),
    dateFormat: "yy-mm-dd",
	onClose: function(selectedDate) {
		if(checkEmpty(selectedDate))
			 $( "#pretime" ).datepicker( "option", "maxDate", null);
		else{
			 $( "#pretime" ).datepicker( "option", "maxDate", moment(selectedDate).subtract(1, 'd').format('YYYY-MM-DD'));
		}
    }
})

//检查空值
function checkEmpty(str) {
	if (str == null || str.replace(/^\s+,""/).replace(/^\s+$/, "") == "") {
		return true;
	} else {
		return false;
	}
}
//点击查询
$("#search-btn").click(function() {
	showOrder(1)
})
function showOrder(pno){
	if(checkEmpty($("#pretime").val())){
		alert("开始时间不能为空！");
		return;
	}
	if(checkEmpty($("#posttime").val())){
		alert("结束时间不能为空！");
		return;
	}
	pno-=1;
	var datastring = 'page=' + pno + '&size=10&pretime='+new Date($("#pretime").val())/1000+
	                 '&posttime='+new Date($("#posttime").val())/1000;
	if($("#status").val()>0)
		datastring+='&status='+$("#status").val();
	if($("#type").val()>0)
		datastring+='&type='+$("#type").val();
	$.ajax({
		url : "/admin/getorders/",
		data : datastring,
		cache : false,
		//contentType:'application/json',
		dataType : 'json',
		type : 'POST',
		success : function(json) {
			 $('#order-info').html("");
			showPageNO(pno+1,json.totalPages,$('#page-div'));
			//alert(json);
			var data=json.content;
			if(data.length<1)
				 $('#order-info').html("没有数据!");
			
			 for (var i = 0; i < data.length; i++) {
				var tda='<tr><td>'+data[i]['id']+'</td><td>'+data[i]['customername']+'</td>'+
		            '<td>'+data[i]['roomtype']['name']+'</td><td>'+data[i]['quantity']+'</td>'+
		            '<td>'+data[i]['cost']+'</td><td>'+data[i]['ordertime']+'</td><td>'+data[i]['status']+'</td></tr>';
				 $('#order-info').append(tda);
			 }
		},
		error : function() {
			alert("系统异常！");
		}
	});
}
//点击分页
$('.page').click(function(){
	showOrder($(this).text());
})
	//显示分页
	function showPageNO(current,total,obj) {
		//alert(total);
		if(total!=null&&total!=0){
		$(obj).html('<li><a href="#" class="page">1</a></li>');
		
		if(current==5)
			$(obj).append('<li><a href="#" class="page">2</a></li>');
		else if(current>5)
			$(obj).append('<li class="disabled"><a href="#">...</a></li>');
		
		for(var i=current-2;i<=current+2&&i<total;i++){
			if(i>1){
					$(obj).append('<li><a href="#" class="page">'+i+'</a></li>');
			}
		}
		
		if(current+5<=total)
			$(obj).append('<li class="disabled"><a href="#">...</a></li>');
			//System.out.print("..."+total);
		if(total==current+4){
			$(obj).append('<li><a href="#" class="page">'+(total-1)+'</a></li>');
			//$(obj).append('<li><a href="#" class="page">'+total+'</a></li>');
		}
		if(total!=1)
			$(obj).append('<li><a href="#" class="page">'+total+'</a></li>');
			//System.out.print(total);
		 $(obj).find('li').each(function(){
			 if($(this).text()==current)
				 $(this).attr("class","active");
		 });
		}
	}