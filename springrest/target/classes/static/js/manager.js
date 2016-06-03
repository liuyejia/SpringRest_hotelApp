var rowindex;

$(document).ready(function() {
	showAccount(1);
})
function showAccount(pno){
	pno-=1;
	$.get("/api/manager/?sort=id,desc&page="+pno+"&size=10",function(json) {
		var data=json._embedded.manager;
			if (data.length < 1){
				$('#mytbody').html("没有数据");
				return;
			}
			 $('#mytbody').html("");
			showPageNO(pno+1,json.page.totalPages,$('#page-div'));
			
             for (var i = 0; i < data.length; i++) {
				var tda = "<tr><td>"+ data[i]['account']+ "</td>"+
					       "<td>"+ data[i]['role']+ "</td>"+
					       "<td>下线</td>";
			    var url = data[i]._links.self.href
//			    var tempArr = [];
//			    tempArr.push('toremove(');
//			    tempArr.push(url);
//			    tempArr.push(')');
//			    url = tempArr.join('');
			    var del='toremove(\'' + url + '\',this)';
			    var edit='edit(\'' + url + '\',this)';
			    $('#mytbody').append(tda+ "<td><a onclick="+edit+
			    		" class='btn btn-info btn-xs'>编辑</a><a onclick="+del+
			    		" class='btn btn-danger btn-xs'>删除</a></td></tr>");

				}
		});
}
//点击分页
$('.page').click(function(){
	showAccount($(this).text());
})
	//更新账号信息
	$(document).on('click','#edit-btn',function(e){
		var rvalue="";
		if($("#edit-role").val()==2)
			rvalue="管理员";
		else
			rvalue="接待人员";
//		alert(rowindex);
//		$('#mytbody').find('tr').eq(rowindex).find('td').eq(1).text(rvalue);
		$.ajax({
			url : $('#edit-url').val(),
			data : JSON.stringify({
				role : $('#edit-role').val(),
			}),
			cache : false,
			dataType : 'json',
			type : 'PUT',
			contentType:"application/json",
			success : function(json) {
				//alert(json);
				//alert(rowindex);
				$('#mytbody').find('tr').eq(rowindex).find('td').eq(1).text(rvalue);
				$('#editModal').modal('hide');
			},
			error : function() {
				alert("系统异常！");
			}
		});
	});
//重置密码
$(document).on('click','#reset-btn',function(e){
	$.ajax({
		url : $('#edit-url').val(),
		data : JSON.stringify({
			password : "password"
		}),
		cache : false,
		dataType : 'json',
		type : 'PUT',
		contentType:"application/json",
		success : function(json) {
			//alert(json);
			$('#editModal').modal('hide');
		},
		error : function() {
			alert("系统异常！");
		}
	});
});
	//删除用户
	//$("#remove_btn").click(function() {
	function toremove(href,obj) {
		//alert(href);
		$.ajax({
			url : href,
			data : {},
			cache : false,
			contentType:'application/json',
			dataType : 'json',
			type : 'delete',
			success : function(json) {
				$(obj).parents('tr:first').remove();
				//alert(json);

			},
			error : function() {
				alert("系统异常！");
			}
		});
	}
	//});
	function edit(eID,obj) {
//		alert($(obj).parents("tr:first").find('td').eq(1).text());
		rowindex = $(obj).parents('tr:first').index();
	    $('#edit-account').val($(obj).parents('tr:first').find('td').eq(0).text());
	    var role=$(obj).parents('tr:first').find('td').eq(1).text();
		//$('#edit-role').val($(obj).parents('tr:first').find('td').eq(2).text());
		if(role=="管理员"){
			$('#edit-role').html('<option value="2">管理员</option>'+
			'<option value="1">接待人员</option>');
		}
		else{
			$('#edit-role').html('<option value="1">接待人员</option>'+
			'<option value="2">管理员</option>');
		}
//		$('#edit-salary').val($(obj).parents('tr:first').find('td').eq(3).text());
		$('#edit-url').val(eID);
		$('#editModal').modal();
	}
	
	//增加用户
	$(document).on('click','#add-btn',function(e){
		$.ajax({
			url : '/api/manager/',
			data :JSON.stringify( {
				password : $('#password').val(),
				account : $('#account').val(),
				role: $('#role').val()
			}),
			cache : false,
			contentType:'application/json',
			dataType : 'json',
			type : 'POST',
			success : function(json) {
				location.reload()
			},
			error : function() {
				alert("系统异常！");
			}
		});
	});
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