var purpose="休闲";

$(document).ready(function(){
	
})

$(document).on('click','#submit-order',function(e){
	
	
	$.ajax({
		url:"/staff/ruzhu_ok",
		data:{
			name:$("#name").val(),
			indentity:$("#indentity").val(),
			phone:$("#phone").val(),
			email:$("#email").val(),
			purpose:purpose,
			cost:$("#cost").html()
		},
		type:"POST",
		success:function(){
			alert("处理完成");
		}
	})
})

$(document).on('click',':radio',function(e){
	purpose=$(this).val();
	
})