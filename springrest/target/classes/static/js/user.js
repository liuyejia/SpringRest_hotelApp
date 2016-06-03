$(document).ready(function() {
	$.get("/users/",function(json) {
			if (json.length < 1)
				$('#mytbody').html("没有数据");
             for (var i = 0; i < json.length; i++) {
					var tda = "<tr>";
				for ( var key in json[i]) {
					tda += "<td>"+ json[i][key]+ "</td>";
				}
			    var id = json[i]["id"];
			    $('#mytbody').append(tda+ "<td><a onclick='edit("+ id+ ",this)' class='btn btn-primary'>编辑</a>"
							+ "<a onclick='remove("+ id+ ")' class='btn btn-danger'>删除</a></td></tr>");
				}
		});

	})
	//更新用户
	$(document).on('click','#edit-btn',function(e){
		var tourl='user/' + $('#edit-id').val();
		$.ajax({
			url : tourl,
			data : {
				name : $('#edit-name').val(),
				age : $('#edit-age').val(),
				salary : $('#edit-salary').val()
			},
			cache : false,
			dataType : 'json',
			type : 'PUT',
//			contentType:"application/json",
			success : function(json) {
				alert(json);

			},
			error : function() {
				alert("系统异常！");
			}
		});
	});
	//删除用户
	//$("#remove_btn").click(function() {
	function remove(userID) {
		$.ajax({
			url : '/user/' + userID,
			data : {},
			cache : false,
			dataType : 'json',
			type : 'delete',
			success : function(json) {
				alert(json);

			},
			error : function() {
				alert("系统异常！");
			}
		});
	}
	//});
	function edit(userID,obj) {
//		alert($(obj).parents("tr:first").find('td').eq(1).text());
	    $('#edit-name').val($(obj).parents('tr:first').find('td').eq(1).text());
		$('#edit-age').val($(obj).parents('tr:first').find('td').eq(2).text());
		$('#edit-salary').val($(obj).parents('tr:first').find('td').eq(3).text());
		$('#edit-id').val(userID);
		$('#editModal').modal();
	}
	
	//增加用户
	$(document).on('click','#add-btn',function(e){
		$.ajax({
			url : '/user/',
			data : {
				name : $('#name').val(),
				age : $('#age').val(),
				salary : $('#salary').val()
			},
			cache : false,
			dataType : 'json',
			type : 'POST',
			success : function(json) {
				//alert(json);
				$('#mytbody').append("<tr><td></td><td>"+$('#name').val()+
						"</td><td>"+$('#age').val()+"</td><td>"+$('#salary').val()+"</td><td><a href='edit("+ id+ ")' class='btn btn-primary'>编辑</a>"+
						"<a href='remove("+ id+ ")' class='btn btn-danger'>删除</a></td></tr>");
				$('#myModal').modal('hide');
			},
			error : function() {
				alert("系统异常！");
			}
		});
	});