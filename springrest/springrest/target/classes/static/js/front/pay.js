//点击支付按钮
$("#pay-btn").click(function(){
	swal({   
		title: "确定付款?",    
		type: "warning",  
		showCancelButton: true,   
		confirmButtonColor: "#DD6B55", 
		cancelButtonText: "取消",
		confirmButtonText: "确定支付",   
		closeOnConfirm: true }, 
		function(){ 
			$.ajax({
				type : "POST",
		        url  : "/hotel/pay-order/",
		        data: {
		        	//token:$("#vtoken").val(),
		        	ordernumber: $("#oid").val()
		        },
		        dataType:"json",
		        success: function(json,textStatus,xhr){
		        	if(xhr.status==200){
		        		$("#msg").text("订单已经支付成功！")
		        		$("#pay-btn").remove();
		        		
		        		swal("支付成功!", "你的订单已经支付，你可以在订单详情中查看详细信息！", "success");
		        	}
		        	else if(xhr.status==404){
		        		$("#msg").text("订单不存在！");
		        		$("#pay-btn").remove();
		        	}
		        },
		        error: function(json){
		        	alert("json=" + json);
		        } 
			});
	});
	
})
