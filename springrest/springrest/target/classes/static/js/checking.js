var roomtype;
var pretime="";
var posttime;
var orderid;

$(document).ready(function(){
	$("#hideDiv").hide();
})

$(document).on('click','#checkings',function(e){
	var value = $("#names").val();
	
	if((!isPhoneNo(value) && !isCardNo(value)) ||value == null)
		alert("请输入正确的手机号或身份证号！");
	else{
		$.ajax({
			url:"/staff/checking/search",
			type:"POST",
			data:{
				identity:value,
			},
			success:function(data){
				$("#orderList").empty();
				var html = "";
				//alert(data[0].indentity);
				$.each(data,function(key,value){
					var predate = new Date(value.pretime*1000).toDateString();
					var postdate = new Date(value.posttime*1000).toDateString();
					
					
					
					
					html += '<div class="changeState"><a href="#" class="btn btn-sq btn-info"><br/>'+value.indentity
					+'<br/>'+predate+'<br/>'+postdate+'<br/>'+value.roomtype.id+'<br/>'+value.id+'<br/></a></div>';
				});
				$("#orderList").html(html);
			}
		});
	}
})

$(document).on('click','.changeState',function(e){
	var str = $(this).html();
	var list = new Array();
	list = str.split("<br>");
	pretime = Date.parse(new Date(list[2]))/1000;
	posttime = Date.parse(new Date(list[3]))/1000;
	roomtype = list[4];
	orderid = list[5];
	
	
})


$(document).on('click','#checked',function(e){
	if(pretime=="")
		alert("请选择订单！");
	else{
		
		$.ajax({
			url:"/staff/ordered/search",
			data:{type:roomtype},
			success:function(data){
				$("#selectRoom").empty();
				var html="";
				$.each(data,function(key,value){
					html+='<option value='+value+'>'+value+'</option>';
				})
				$("#selectRoom").html(html);
				$("#hideDiv").show();
			}
		});
		
		
	}
})

$(document).on('click','#selectOK',function(e){
	//console.log($("#selectSex").val());
	var roomnumber = $("#selectRoom").val();
	$.ajax({
		url:"/staff/ordered",
		data:{
			roomnumber:roomnumber,
			orderid:orderid,
			
			},
		type:"POST",
		success:function(){
			alert("处理订单完成！");
		}
	});
})













//手机号正则匹配
function isPhoneNo(phone){ 
    //var phone = document.getElementById('phone').value;
    if(!(/^1[3|4|5|7|8]\d{9}$/.test(phone))){ 
        //alert("手机号码有误，请重填");  
        return false; 
    } 
    return true;
}
//省份证正则匹配
function isCardNo(card)
{
   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
   var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
   if(reg.test(card) === false)
   {
       //alert("身份证有误，请重填");
       return false;
   }
   return true;
}