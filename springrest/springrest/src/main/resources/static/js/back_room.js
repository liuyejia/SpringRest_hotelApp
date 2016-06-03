var type0 = 1;
var pretime0;
var posttime0;
var temp;
$(document).ready(function(){
	
	$("#xuzhu").hide();
	var date = moment().format("YYYY-MM-DD");
	pretime0 = date;
	posttime0 = date;
	$("#pretime").attr("value",date);
	$("#posttime").attr("value",date);
	
	$.ajax({
		url:"http://localhost:8080/staff/check",
		type:"POST",
		data:{type:type0,
			pretime:pretime0,
			posttime:posttime0},
			success:function(data){
				$("#p1").empty();
				var html = "";
				$.each(data.roomList,function(key,value){
					if(value.status == 1)
						html += '<a href="#" class="eClick btn btn-sq btn-default"><br/>'+value.roomnumber+'<br/></a>';
					else if(value.status == 2)
						html += '<a href="#" class="eClick btn btn-sq btn-info"><br/>'+value.roomnumber+'<br/></a>';
					else if(value.status == 3)
						html += '<a href="#" class="eClick btn btn-sq btn-warning"><br/>'+value.roomnumber+'<br/></a>';
					
				});
				$("#p1").html(html);
				
				$("#span1").html(data.emptyNum);
				$("#span2").html(data.occupyNum);
				$("#span3").html(data.orderNum);
				$("#span4").html(data.faultNum);
			}
	});
})


function preChange(){
	pretime0 = $("#pretime").val();
	posttime0 = $("#posttime").val();
	var p1 = new Date(pretime0).getTime();
	var p2 = new Date(posttime0).getTime();
	if(p1>p2) alert("请选择合法日期！");
	else{
	$.ajax({
		url:"http://localhost:8080/staff/check",
		type:"POST",
		data:{type:type0,
			pretime:pretime0,
			posttime:posttime0},
			success:function(data){
				if(type == 1)
					$("#p1").empty();
				else if(type == 2)
					$("#p2").empty();
				else if(type == 3)
					$("#p3").empty();
				var html = "";
				$.each(data.roomList,function(key,value){
					if(value.status == 1)
						html += '<a href="#" class="eClick btn btn-sq btn-default"><br/>'+value.roomnumber+'<br/></a>';
					else if(value.status == 2)
						html += '<a href="#" class="eClick btn btn-sq btn-info"><br/>'+value.roomnumber+'<br/></a>';
					else if(value.status == 3)
						html += '<a href="#" class="eClick btn btn-sq btn-warning"><br/>'+value.roomnumber+'<br/></a>';
					
				});
				if(type == 1)
					$("#p1").html(html);
				else if(type == 2)
					$("#p2").html(html);
				else if(type == 3)
					$("#p3").html(html);
				
				$("#span1").html(data.emptyNum);
				$("#span2").html(data.occupyNum);
				$("#span3").html(data.orderNum);
				$("#span4").html(data.faultNum);
			}
	});
	}
}

function postChange(){
	pretime0 = $("#pretime").val();
	posttime0 = $("#posttime").val();
	var p1 = new Date(pretime0).getTime();
	var p2 = new Date(posttime0).getTime();
	if(p1>p2) alert("请选择合法日期！");
	else{
	$.ajax({
		url:"http://localhost:8080/staff/check",
		type:"POST",
		data:{type:type0,
			pretime:pretime0,
			posttime:posttime0},
			success:function(data){
				if(type == 1)
					$("#p1").empty();
				else if(type == 2)
					$("#p2").empty();
				else if(type == 3)
					$("#p3").empty();
				var html = "";
				$.each(data.roomList,function(key,value){
					if(value.status == 1)
						html += '<a href="#" class="eClick btn btn-sq btn-default"><br/>'+value.roomnumber+'<br/></a>';
					else if(value.status == 2)
						html += '<a href="#" class="eClick btn btn-sq btn-info"><br/>'+value.roomnumber+'<br/></a>';
					else if(value.status == 3)
						html += '<a href="#" class="eClick btn btn-sq btn-warning"><br/>'+value.roomnumber+'<br/></a>';
					
				});
				if(type == 1)
					$("#p1").html(html);
				else if(type == 2)
					$("#p2").html(html);
				else if(type == 3)
					$("#p3").html(html);
				
				$("#span1").html(data.emptyNum);
				$("#span2").html(data.occupyNum);
				$("#span3").html(data.orderNum);
				$("#span4").html(data.faultNum);
			}
	});
	}
}

function singleClick(){
	type0 = 1;
	$.ajax({
		url:"http://localhost:8080/staff/check",
		type:"POST",
		data:{type:type0,
			pretime:pretime0,
			posttime:posttime0},
			success:function(data){
				$("#p1").empty();
				var html = "";
				$.each(data.roomList,function(key,value){
					if(value.status == 1)
						html += '<a href="#" class="eClick btn btn-sq btn-default"><br/>'+value.roomnumber+'<br/></a>';
					else if(value.status == 2)
						html += '<a href="#" class="eClick btn btn-sq btn-info"><br/>'+value.roomnumber+'<br/></a>';
					else if(value.status == 3)
						html += '<a href="#" class="eClick btn btn-sq btn-warning"><br/>'+value.roomnumber+'<br/></a>';
					
				});
				$("#p1").html(html);
				
				$("#span1").html(data.emptyNum);
				$("#span2").html(data.occupyNum);
				$("#span3").html(data.orderNum);
				$("#span4").html(data.faultNum);
			}
	});
}
function doubleClick(){
	type0 = 2;
	$.ajax({
		url:"http://localhost:8080/staff/check",
		type:"POST",
		data:{type:type0,
			pretime:pretime0,
			posttime:posttime0},
		dataType: "json",
		success:function(data){
			$("#p2").empty();
			var html = "";
			$.each(data.roomList,function(key,value){
				if(value.status == 1)
					html += '<a href="#" class="eClick btn btn-sq btn-default"><br/>'+value.roomnumber+'<br/></a>';
				else if(value.status == 2)
					html += '<a href="#" class="eClick btn btn-sq btn-info"><br/>'+value.roomnumber+'<br/></a>';
				else if(value.status == 3)
					html += '<a href="#" class="eClick btn btn-sq btn-warning"><br/>'+value.roomnumber+'<br/></a>';
				
			});
			$("#p2").html(html);
			
			$("#span1").html(data.emptyNum);
			$("#span2").html(data.occupyNum);
			$("#span3").html(data.orderNum);
			$("#span4").html(data.faultNum);
		}
	});
}
function suitClick(){
	type0 = 3;
	$.ajax({
		url:"http://localhost:8080/staff/check",
		type:"POST",
		data:{type:type0,
			pretime:pretime0,
			posttime:posttime0},
		success:function(data){
			$("#p3").empty();
			var html = "";
			$.each(data.roomList,function(key,value){
				if(value.status == 1)
					html += '<a href="#" class="eClick btn btn-sq btn-default"><br/>'+value.roomnumber+'<br/></a>';
				else if(value.status == 2)
					html += '<a href="#" class="eClick btn btn-sq btn-info"><br/>'+value.roomnumber+'<br/></a>';
				else if(value.status == 3)
					html += '<a href="#" class="eClick btn btn-sq btn-warning"><br/>'+value.roomnumber+'<br/></a>';
				
			});
			$("#p3").html(html);
			
			$("#span1").html(data.emptyNum);
			$("#span2").html(data.occupyNum);
			$("#span3").html(data.orderNum);
			$("#span4").html(data.faultNum);
		}
	});
}
$(document).on('click','.eClick',function(e){

	$("#cloneA").html($(this).clone());
	
})

$(document).on('click','#checking',function(e){
	window.location.href = 'http://localhost:8080/staff/checking';
})

$(document).on('click','#ruzhu',function(e){
	if($("#cloneA").html() != null && $("#cloneA").html().indexOf("btn btn-sq btn-default")>0){
		var list=new Array();
		list = $("#cloneA").html().split("<br>");
		window.location.href = "http://localhost:8080/staff/ruzhu?roomnumber="+list[1]+"&pretime="+pretime0+"&posttime="+posttime0;
		
	}
})

$(document).on('click','#quitRoom',function(e){
	if($("#cloneA").html() != null && $("#cloneA").html().indexOf("btn btn-sq btn-info")>0){
		var list = new Array();
		list = $("#cloneA").html().split("<br>");
		
		
		$.ajax({
			url:"http://localhost:8080/staff/quit",
			type:"POST",
			data:{
				roomnumber:list[1]
			},
			success:function(data){
				alert("退房成功！");
			}
		});
	}
})

$(document).on('click','#continue',function(e){
	if($("#cloneA").html() != null && $("#cloneA").html().indexOf("btn btn-sq btn-info")>0){
		var list = new Array();
		list = $("#cloneA").html().split("<br>");
		
		var roomnumber = list[1];
		temp = roomnumber;
		$("#xuzhu").show();
		
		
	}
})

$(document).on('click','#xuzhubtn',function(e){
	var day = $("#xuzhunum").val();
	if(day<1||day>7) alert("只能续住1-7天！");
	else{
		$.ajax({
			url:"/staff/continue",
			data:{
				day:day,
				roomnumber:temp
				},
			success:function(){
				alert("续住成功？");
			}
		});
	}
})
/*
$(document).on('click','.href1',function(e){
	$(".eClick btn btn-sq btn-info").hide();
	$(".eClick btn btn-sq btn-warning").hide();
	$(".eClick btn btn-sq btn-default").show();
	
})
$(document).on('click','.href2',function(e){
	$(".eClick btn btn-sq btn-info").show();
	$(".eClick btn btn-sq btn-warning").hide();
	$(".eClick btn btn-sq btn-default").hide();
	
})
$(document).on('click','.href3',function(e){
	$(".eClick btn btn-sq btn-info").show();
	$(".eClick btn btn-sq btn-warning").show();
	$(".eClick btn btn-sq btn-default").show();
	
})
$(document).on('click','.href4',function(e){
	$(".eClick btn btn-sq btn-info").hide();
	$(".eClick btn btn-sq btn-warning").show();
	$(".eClick btn btn-sq btn-default").hide();
	
})
*/
