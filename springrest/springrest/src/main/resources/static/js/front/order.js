$(document).ready(function() {
	//显示日期
	var pre_time = moment($("#form-pretime").val()*1000).format('YYYY-MM-DD');
	var post_time = moment($("#form-posttime").val()*1000).format('YYYY-MM-DD');
	$("#pretime").text(pre_time);
	$("#posttime").text(post_time);
	$("#new-pre").val(pre_time);
	$("#new-post").val(post_time);
	//显示住的时间
	var pre2=moment($("#form-pretime").val()*1000);
	var post2=moment($("#form-posttime").val()*1000);
	var duration=post2.diff(pre2, 'days');
	$("#duration").text(duration);//住的时间跨度
	//
	var remain=$("#remain").val();
	var number=(remain<10)?remain:10;
	var tda="";
	//var id=data[i]._links.self.href;
	//var tid=id.substr(id.lastIndexOf('/') + 1)
	//tda+='<td><select class="form-control">'
	for(var j=1;j<=number;j++)
		tda+="<option>"+j+"</option>"
	$("#quantity").html(tda);
	$("#quantity").val($("#form-quantity").val());
	
	//显示单价
	var price=$("#room-price").val()*$("#room-discount").val();
	$("#price").text(price);
	//显示总价
	var cost=$("#quantity").val()*price*duration;
	$("#cost").text(cost+"元");
	
	//showdate();
})

//时间选择
//function showdate(){
$( "#new-pre" ).datepicker({
	regional  :"zh-CN",
    minDate:moment().format('YYYY-MM-DD'),
    dateFormat: "yy-mm-dd",
	onClose: function(selectedDate) {
						if(checkEmpty(selectedDate))
							 $( "#new-post" ).datepicker( "option", "minDate", moment().add(1, 'd').format('YYYY-MM-DD'));
			
						else
							 $( "#new-post" ).datepicker( "option", "minDate", moment(selectedDate).add(1, 'd').format('YYYY-MM-DD'));
//						     if(!checkEmpty($( "#new-post" ).val())){
//							     findMaxNumber();
//						    }
					}
		});
$( "#new-post" ).datepicker({
	regional  :"zh-CN",
    minDate:moment().add(1, 'd').format('YYYY-MM-DD'),
    dateFormat: "yy-mm-dd",
	onClose: function(selectedDate) {
		if(checkEmpty(selectedDate))
			 $( "#new-pre" ).datepicker( "option", "maxDate", null);
		else
			 $( "#new-pre" ).datepicker( "option", "maxDate", moment(selectedDate).subtract(1, 'd').format('YYYY-MM-DD'));
//		     if(!checkEmpty($( "#new-pre" ).val())){
//		         findMaxNumber();
//	         }
	}
})

function findMaxNumber(){
	$("#book-alert").attr('style','display:none');
	$.ajax({
		url : "/hotel/find-onemax/",
		data : {
			pretime:parseInt(new Date($("#new-pre").val())*1000),
			posttime:parseInt(new Date($("#new-post").val())*1000),
			typenumber:$('#form-typenumber').val()
		},
		cache : false,
		//contentType:'application/json',
		dataType : 'json',
		type : 'post',
		success : function(json) {
			var book=$("#quantity").val();
			var tda="";
			
			if(json==0){
				$("#quantity").html('<option>0</option>');
				$("#book-alert").attr('style','');
			}
			else if(json>=book){
				var number=(json<10)?json:10;
				for(var j=1;j<=number;j++)
					tda+="<option>"+j+"</option>"
				$("#quantity").html(tda);
				$("#quantity").val(book);
			}
			else{
				for(var j=1;j<=json;j++)
					tda+="<option>"+j+"</option>"
				$("#quantity").html(tda);
				$("#quantity").val(json);
			}
		},
		error : function() {
			alert("系统异常！");
		}
	});
}
//}
//$(document).on('change','#pretime',function() {
//	
//	if(checkEmpty($( "#pretime" ).val()))
//		 $( "#posttime" ).datepicker( "option", "minDate", moment().add(1, 'd').format('YYYY-MM-DD'));
//
//	else
//		 $( "#posttime" ).datepicker( "option", "minDate", moment($( "#pretime" ).val()).add(1, 'd').format('YYYY-MM-DD'));
//})
//检查空值
function checkEmpty(str) {
	if (str == null || str.replace(/^\s+,""/).replace(/^\s+$/, "") == "") {
		return true;
	} else {
		return false;
	}
}
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

//计算总价
function doCost(){
	var pre2=moment($("#pretime").text());
	var post2=moment($("#posttime").text());
	var duration=post2.diff(pre2, 'days');
	$("#duration").text(duration);//住的时间跨度
	
	var price=$("#room-price").val()*$("#room-discount").val();
	var cost=$("#quantity").val()*price*duration;
	$("#cost").text(cost+"元");
}
//修改时间
$(document).on('click','#edit-time',function() {
	$("#pretime").text($("#new-pre").val());
	$("#posttime").text($("#new-post").val());
	
	jQuery.noConflict();
	$('#editModal').modal('hide');
	
	findMaxNumber();
	doCost();
	
})
//改变数量
$(document).on('change','#quantity',function(e){
	$("#choose-number").text($("#quantity").val());
	doCost();
})
//提交订单
$("#submit-order").click(function() {
	
	if(checkEmpty($("#name").val())){
		alert("姓名不能为空！");
		return;
	}
	if(!isPhoneNo($("#phone").val())){
		alert("手机信息不能为空！");
		return;
	}
	if(!isCardNo($("#indentity").val())){
		alert("身份证信息不能为空！");
		return;
	}
	
	$("#form-pretime").val(parseInt(new Date($("#pretime").text())/1000));
	$("#form-posttime").val(parseInt(new Date($("#posttime").text())/1000));
	$("#form-quantity").val($("#quantity").val());
	$("#form-email").val($("#email").val());
	$("#form-phone").val($("#phone").val());
	$("#form-customername").val($("#name").val());
	$("#form-indentity").val($("#indentity").val());
	$("#form-purpose").val($('input[name="purpose"]:checked').val());
	$("#form-demand").val($("#demand").val());
	
	
	$("#orderform").submit();
});