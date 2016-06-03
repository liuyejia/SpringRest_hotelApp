//手机号正则匹配
function isPhoneNo(phone){ 
    //var phone = document.getElementById('phone').value;
    if(!(/^1[3|4|5|7|8]\d{9}$/.test(phone))){ 
        //alert("手机号码有误，请重填");  
        return false; 
    } 
    return true;
}
//提交订单
$("#search-btn").click(function() {
	showOrder(1)
})
function showOrder(pno){
	if(!isPhoneNo($("#phone").val())){
		alert("手机号格式不正确！");
		return;
	}
	pno-=1;
	$.ajax({
		url : "/hotel/search-order/",
		data : {
			phone:$("#phone").val(),
			page:pno,
			size:10
		},
		cache : false,
		//contentType:'application/json',
		dataType : 'json',
		type : 'post',
		success : function(json) {
			 $('#order-info').html("");
			showPageNO(pno+1,json.totalPages,$('#page-div'));
			//alert(json);
			var data=json.content;
			 for (var i = 0; i < data.length; i++) {
				var tda='<div class="col-md-offset-2 col-md-8" style="margin-bottom: 20px">'+
				 '<div class="col-md-12" style="background-color: #d9edf7;">'+
				 '<span class="col-md-3">订单号：'+data[i]['id']+'</span>'+
				 '<span class="col-md-3">下单时间：'+data[i]['ordertime']+'</span>'+
				 '<span class="col-md-3">用户：'+data[i]['customername']+'</span>'+
				 '<span class="col-md-3">手机：'+data[i]['phone']+'</span>'+
				 '<span class="col-md-3">入住时间：'+data[i]['pretime']+'</span>'+
				 '<span class="col-md-3">退房时间：'+data[i]['posttime']+'</span>'+
				 '<span class="col-md-3">email：'+data[i]['email']+'</span>'+
				 '<span class="col-md-3"> </span>'+
				 '<span class="col-md-12">特殊要求：'+data[i]['demand']+'</span>'+
				 '</div><div class="col-md-12" style="height:50px;line-height: 50px;border-style:solid;border-color:#ddd;">'+
				 	'<span class="col-md-3">'+data[i]['roomtype']['name']+'</span>'+
				 	'<span class="col-md-3">X '+data[i]['quantity']+'</span>'+
				 	'<span class="col-md-3">已支付</span>'+
				     '<span class="col-md-3">￥'+data[i]['cost']+'</span></div></div>';
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