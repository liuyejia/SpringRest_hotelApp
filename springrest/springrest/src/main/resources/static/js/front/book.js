//检查空值
function checkEmpty(str) {
	if (str == null || str.replace(/^\s+,""/).replace(/^\s+$/, "") == "") {
		return true;
	} else {
		return false;
	}
}
$(document).ready(function() {
	$.get("/api/roomtype/",function(json) {
		var data=json._embedded.roomtype;
		if (data.length < 1){
			$('#mytbody').html("没有数据");
			return;
		}
		//$('#mytbody').html("");
		//showPageNO(pno+1,json.page.totalPages,$('#page-div'));
		
         for (var i = 0; i < data.length; i++) {
			var tda = "<tr><td>"+ data[i]['name']+ "</td>"+
				       "<td>"+ data[i]['facility']+ "</td>";
				      // "<td>下线</td>";
			var remain=data[i]['remain'];
			if(remain<=0)
				tda+='<td>已定完</td><td><a type="button" class="btn btn-primary" disabled="disabled">预定</a></td>';
			else{
				var number=(remain<10)?remain:10;
				var id=data[i]._links.self.href;
				var tid=id.substr(id.lastIndexOf('/') + 1)
				tda+='<td><select class="form-control">'
				for(var j=1;j<=number;j++)
					tda+="<option>"+j+"</option>"
				tda+='</select></td><td><a type="button" class="btn btn-primary" onclick="book('+tid+',this)">预定</a></td>';
			}
			 $('#mytbody').append(tda+"</tr>");
         }
        
	})
	
})

$( "#pretime" ).datepicker({
	regional  :"zh-CN",
    minDate:moment().format('YYYY-MM-DD'),
    dateFormat: "yy-mm-dd",
	onClose: function(selectedDate) {
						if(checkEmpty(selectedDate))
							 $( "#posttime" ).datepicker( "option", "minDate", moment().add(1, 'd').format('YYYY-MM-DD'));
			
						else
							 $( "#posttime" ).datepicker( "option", "minDate", moment(selectedDate).add(1, 'd').format('YYYY-MM-DD'));
						     if(!checkEmpty($( "#posttime" ).val())){
						    	 findMaxNumber();
						    }
				    }
		});
$( "#posttime" ).datepicker({
	regional  :"zh-CN",
    minDate:moment().add(1, 'd').format('YYYY-MM-DD'),
    dateFormat: "yy-mm-dd",
	onClose: function(selectedDate) {
		if(checkEmpty(selectedDate))
			 $( "#pretime" ).datepicker( "option", "maxDate", null);
		else{
			 $( "#pretime" ).datepicker( "option", "maxDate", moment(selectedDate).subtract(1, 'd').format('YYYY-MM-DD'));
			 if(!checkEmpty($( "#pretime" ).val())){
				 findMaxNumber();
			 }
		}
    }
})
function findMaxNumber(){
	$.ajax({
		url : "/hotel/find-maxnumber/",
		data : {
			pretime:parseInt(new Date($("#pretime").val())/1000),
			posttime:parseInt(new Date($("#posttime").val())/1000),
		},
		cache : false,
		//contentType:'application/json',
		dataType : 'json',
		type : 'post',
		success : function(json,textStatus,xhr) {
			if(xhr.status==200){
			for(var i=0;i<json.length;i++){
			
				var remain=json[i]['maxNumber'];
				if(remain<=0){
					var obj=$('#mytbody').find("tr").eq(i).find("td").eq(2);
					$(obj).html("已订完");
					$(obj).next().find('a:first').attr('disabled','disabled');
					continue;
				}
				var number=(remain<10)?remain:10;
			    tda+='<td><select class="form-control">'
				for(var j=1;j<=number;j++)
					tda+="<option>"+j+"</option>"
				tda+='</select>';
			    $('#mytbody').find("tr").eq(i).find("td").eq(2).html(tda)
			}
			}
			//else
			//	alert(xhr.status);
		},
		error : function() {
			alert("系统异常！");
		}
	});
}
//预定房间
function book(type,obj) {
	if(checkEmpty($("#pretime").val())){
		alert("入住时间不能为空！");
		return;
	}
	else if(checkEmpty($("#posttime").val())){
		alert("退房时间不能为空！");
		return;
	}
		
	var quantity=$(obj).parents("tr:first").find("select:eq(0)").val();
	//alert(quantity);
	$("#form-pretime").val(parseInt(new Date($("#pretime").val())/1000));
	$("#form-posttime").val(parseInt(new Date($("#posttime").val())/1000));
	$("#form-quantity").val(quantity);
	$("#form-typenumber").val(type);
	
	$("#bookform").submit();
	//var timestamp = parseInt(new Date()*1000);
//		$.ajax({
//			url : "/hotel/book-room/",
//			data : {
//				pretime:parseInt(new Date($("#pretime").val())*1000),
//				posttime:parseInt(new Date($("#posttime").val())*1000),
//				quantity:quantity,
//				typenumber:type
//			},
//			cache : false,
//			//contentType:'application/json',
//			dataType : 'json',
//			type : 'post',
//			success : function(json) {
//				//$(obj).parents('tr:first').remove();
//				alert(json);
//			},
//			error : function() {
//				alert("系统异常！");
//			}
//		});
	}
