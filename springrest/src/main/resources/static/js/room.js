var nowtype=1;
var typelist=new Array();
var roomlist=new Array();
var host = window.location.host; 
jQuery(document).ready(function() {		
	        App.setPage("gallery");   //Set current page
			App.init(); //Initialise plugins and elements
			
			$("#room-li").attr("class","has-sub active");
			$("#room-li").find("a:first").html('<i class="fa fa-bar-chart-o fa-fw"></i> <span class="menu-text">房间管理</span>'+
					'<span class="arrow open"></span><span class="selected"></span>')
//			$("#room-li").find('li:first').attr("class","current");
//			nowtype=$("#room-li").find('li:first').find('input:first').val();
			getTypeList();
			//alert('line:13'+typelist.length);
			//if(typelist)
			//roomClick(typelist[],obj);
			build();
				
		});
function getTypeList(){
	$.get("/admin/gettypes/",function(json) {
		//var data=json._embedded.roomtype;
		var data=json;
			//typelist=data;	
			typelist = data;
			
			
			for(var i=0;i<typelist.length;i++){
				var tda='<li><button class="btn btn-default btn-xs treebtn" onclick="editType(this,'+i+')">修改</button>'+
					'<a href="#" onclick="roomClick('+typelist[i]['id']+',this)">'+
						'<span class="sub-menu-text">'+typelist[i]['name']+'</span></a></li>';
				$("#room-ul").append(tda);
				
			}
			$("#room-ul").append('<li><a href="../insert-type/"><span class="sub-menu-text">增加房间类型</span></a></li>');
			if(typelist.length>0)
				   roomClick(typelist[0].id,$("#room-ul").find('li:first').find('a:first'));
	})
}
//修改房间类型
function editType(obj,typeid){
	var data=typelist[typeid];
	//var path=window.document.location.href;
	//var url=host+"/picture/"+data['picture'];
	$('#type-id').val(typeid);
	$('#type-img').attr('src',"/picture/"+data['picture']);
	$('#name').val(data['name']);
	$('#total').val(data['total']);
	$('#remain').val(data['remain']);
	$('#facility').val(data['facility']);
    $('#introduction').val(data['introduction']);
	$('#price').val(data['price']);
	$('#discount').val(data['discount']);
	
	$('#typeModal').modal('show');
}
function roomClick(typeid,obj){
	$("li[class=current]").attr("class","");
	$(obj).parents('li:first').attr("class","current");
	
	nowtype=typeid;
	$.ajax({
		url : "/admin/getrooms/",
		data : {
			typenumber:typeid
		},
		cache : false,
		//contentType:'application/json',
		dataType : 'json',
		type : 'POST',
		success : function(json) {
			 $('#filter-items').html("");
			//showPageNO(pno+1,json.totalPages,$('#page-div'));
			//alert(json);
			var data=json;
			roomlist=data;
			if(data.length<1)
				 $('#filter-items').html("没有数据!");
			
			 for (var i = 0; i < data.length; i++) {
				 var tda="";
				 if(data[i].status==1){
					 console.log('status:1');
				tda='<div class="col-md-2 item"><div class="filter-content"><a href="#" class="btn btn-sq btn-default col-md-12" '
					+'style="height: 120px"> <br /> <span>'+data[i]['roomnumber']+'</span> <br />'+
				'</a><div class="hover-content animated fadeOut"><h4></h4><a class="btn btn-success hover-link" onclick="edit('+i+',this)"> <i class="fa fa-edit fa-1x"></i></a></div></div></div>';
				 }
				 else if(data[i].status==2){
					 console.log('status:2');
					tda='<div class="col-md-2 item"><div class="filter-content"><a href="#" class="btn btn-sq btn-primary col-md-12" '
							+'style="height: 120px"> <br /> <span>'+data[i]['roomnumber']+'</span> <br />'+
						'</a><div class="hover-content animated fadeOut"><h4></h4><a class="btn btn-success hover-link" onclick="edit('+i+',this)"> <i class="fa fa-edit fa-1x"></i></a></div></div></div>';
				 }
				 else{
					 console.log('status:3');
					 tda='<div class="col-md-2 3 item"><div class="filter-content"><a href="#" class="btn btn-sq btn-danger col-md-12" '
							+'style="height: 120px"> <br /> <span>'+data[i]['roomnumber']+'</span> <br />'+
						'</a><div class="hover-content animated fadeOut"><h4></h4><a class="btn btn-success hover-link" onclick="edit('+i+',this)"> <i class="fa fa-edit fa-1x"></i></a></div></div></div>';
				 }
				$('#filter-items').append(tda);
			 }
			 build();
		},
		error : function() {
			alert("系统异常！");
		}
	})
}
//点击增加房间
function addroom(){
	$('#aroom-alert').hide();
	$('#add-type').html('');
	for(var i=0;i<typelist.length;i++){
		$('#add-type').append('<option value="'+typelist[i].id+'">'+typelist[i].name+'</option>');
	}
	
	$('#add-status').html('<option value="1">空房间</option>'+
	'<option value="2">居住中</option><option value="3">故障</option>');
	$('#addModal').modal('show');
}
//点击修改房间信息
function edit(id,obj){
	var data=roomlist[id]
	
	$('#room-alert').hide();
	$('#edit-id').val(id);
	
	$('#o-roomnumber').val(data.roomnumber);
	$('#edit-roomnumber').val(data.roomnumber);//需要检查
	$('#edit-floor').val(data.floor);
	
	$('#edit-type').html('');
	for(var i=0;i<typelist.length;i++){
		$('#edit-type').append('<option value="'+typelist[i].id+'">'+typelist[i].name+'</option>');
	}
	
	$('#edit-status').html('<option value="1">空房间</option>'+
	'<option value="2">居住中</option><option value="3">故障</option>');
	
	$('#edit-type').val(data.roomtype.id)//
	$('#edit-status').val(data.status);//
	$('#editModal').modal('show');
}
//检查增加房间号的合法性
$(document).on('blur','#add-roomnumber',function(e){
	$.ajax({
		url : "/admin/checkroomno/",
		data : {
			roomnumber:$('#add-roomnumber').val(),
		},
		cache : false,
		dataType : 'text',
		type : 'POST',
		success : function(json) {
			if(json=='used'){
				$('#aroom-alert').show();
				$('#add-btn').attr('disabled',"true");
			}
			$('#add-btn').attr("disabled",'false'); 
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("系统异常！"+textStatus+":"+errorThrown);
		}
		})
})
//检查房间号的合法性
$(document).on('blur','#edit-roomnumber',function(e){
	if($('#edit-roomnumber').val()==$('#o-roomnumber').val()){
		$('#edit-btn').removeattr("disabled"); 
		return;
	}
	$.ajax({
		url : "/admin/checkroomno/",
		data : {
			roomnumber:$('#edit-roomnumber').val(),
		},
		cache : false,
		dataType : 'json',
		type : 'POST',
		success : function(json) {
			if(json=='used'){
				$('#room-alert').show();
				$('#edit-btn').attr('disabled',"true");
			}
			$('#edit-btn').attr("disabled",'false'); 
		},
		error : function() {
			alert("系统异常！");
		}
		})
})
//更新房间信息
$(document).on('click','#edit-btn',function(e){
	var index=$('#edit-id').val();
	$.ajax({
		url : "/admin/addroom/",
		data : {
			id:roomlist[index].id,
			typenumber:$('#edit-type').val(),
			roomnumber:$('#edit-roomnumber').val(),
			floor:$('#edit-floor').val(),
			status:$('#edit-status').val()
		},
		cache : false,
		//contentType:'application/json',
		dataType : 'json',
		type : 'POST',
		success : function(json) {
			$('#editModal').modal('hide');
			roomlist[index]=json;
			
			 var tda="";
			 if(json.status==1){
			tda='<div class="col-md-2 1 item"><div class="filter-content"><a href="#" class="btn btn-sq btn-default col-md-12" '
				+'style="height: 120px"> <br /> <span>'+json.roomnumber+'</span> <br />'+
			'</a><div class="hover-content animated fadeOut"><h4></h4><a class="btn btn-success hover-link" onclick="edit('+roomlist.length+',this)"> <i class="fa fa-edit fa-1x"></i></a></div></div></div>';
			 }
			 else if(json.status==2){
				tda='<div class="col-md-2 2 item"><div class="filter-content"><a href="#" class="btn btn-sq btn-primary col-md-12" '
						+'style="height: 120px"> <br /> <span>'+json.roomnumber+'</span> <br />'+
					'</a><div class="hover-content animated fadeOut"><h4></h4><a class="btn btn-success hover-link" onclick="edit('+roomlist.length+',this)"> <i class="fa fa-edit fa-1x"></i></a></div></div></div>';
			 }
			 else{
				 tda='<div class="col-md-2 3 item"><div class="filter-content"><a href="#" class="btn btn-sq btn-danger col-md-12" '
						+'style="height: 120px"> <br /> <span>'+json.roomnumber+'</span> <br />'+
					'</a><div class="hover-content animated fadeOut"><h4></h4><a class="btn btn-success hover-link" onclick="edit('+roomlist.length+',this)"> <i class="fa fa-edit fa-1x"></i></a></div></div></div>';
			 }
			$("#filter-items").find('.item').eq(index).html(tda);
			
			alert("更新成功！");
		},
		error : function() {
			alert("系统异常！");
		}
		})
})
//每次增加div都要调用这个函数来达到hover效果
function build(){
	$('.filter-content').hover(function() {
			var hoverContent = $(this).children('.hover-content');
			hoverContent.removeClass('fadeOut').addClass('animated fadeIn').show();
		}, function() {
			var hoverContent = $(this).children('.hover-content');
			hoverContent.removeClass('fadeIn').addClass('fadeOut');
		});
}
//修改房间类型
$(document).on('click','#type-btn',function(e){

	var index=$('#type-id').val();
	$.ajaxFileUpload({
		url : '/admin/add-roomtype/',// 处理图片脚本
		secureuri : false,
		fileElementId : 'file',// file控件id
		dataType : 'jsonp',
		data : {
			id:type[index].id,
			name:$('#name').val(),
			total:$('#total').val(),
			remain:$('#remain').val(),
			facility:$('#facility').val(),
		    introduction:$('#introduction').val(),
			price:$('#price').val(),
			discount:$('#discount').val()
		},
		success : function(data, status) {
			alert('修改成功！')
			typelist[index]=data;
			$('#typeModal').modal('hide');
			var tda='<li><button class="btn btn-default btn-xs treebtn" onclick="editType(this,'+index+')">修改</button>'+
			'<a href="#" onclick="roomClick('+data.id+',this)">'+
				'<span class="sub-menu-text">'+datd.name+'</span></a></li>';
			$("#room-ul").find('li').eq(index).html(tda);
		},
		error : function(data, status, e) {
			alert("123系统异常！");
		}
	});
})
//添加房间
$(document).on('click','#add-btn',function(e){
//	var tda='<div class="col-md-2 isotope-item" style="position: absolute; left: 0px; top: 0px; transform: translate3d(183px, 1260px, 0px);"><div class="filter-content"><a href="#" class="btn btn-sq btn-primary col-md-12" '
//		+'style="height: 120px"> <br /> <span>fnew</span> <br />'+
//	'</a><div class="hover-content animated fadeOut" style="display: block;"><h4></h4><a class="btn btn-success hover-link" onclick="edit(1,this)"> <i class="fa fa-edit fa-1x"></i></a></div></div></div>';
//	 $('#filter-items').append(tda);
//	 $('#addModal').modal('hide');
//	 build();
//	 <div class="col-md-2">
//		<div class="filter-content">
//			<a href="#" class="btn btn-sq btn-primary col-md-12" style="height: 120px"> <br /> <span>F1-2</span> <br />李三
//			</a>
//			<div class="hover-content">
//				<h4></h4>
//				<a class="btn btn-success hover-link" onclick="edit(4,this)"> <i class="fa fa-edit fa-1x"></i>
//				</a>
//			</div>
//		</div>
//	</div>
	$.ajax({
		url : "/admin/addroom/",
		data : {
			typenumber:$('#add-type').val(),
			roomnumber:$('#add-roomnumber').val(),
			floor:$('#add-floor').val(),
			status:$('#add-status').val()
		},
		cache : false,
		//contentType:'application/json',
		dataType : 'json',
		type : 'POST',
		success : function(json) {
			//var data=json;
			 var tda="";
			 if(json.status==1){
			tda='<div class="col-md-2 1 item"><div class="filter-content"><a href="#" class="btn btn-sq btn-default col-md-12" '
				+'style="height: 120px"> <br /> <span>'+json.roomnumber+'</span> <br />'+
			'</a><div class="hover-content animated fadeOut"><h4></h4><a class="btn btn-success hover-link" onclick="edit('+roomlist.length+',this)"> <i class="fa fa-edit fa-1x"></i></a></div></div></div>';
			 }
			 else if(json.status==2){
				tda='<div class="col-md-2 2 item"><div class="filter-content"><a href="#" class="btn btn-sq btn-primary col-md-12" '
						+'style="height: 120px"> <br /> <span>'+json.roomnumber+'</span> <br />'+
					'</a><div class="hover-content animated fadeOut"><h4></h4><a class="btn btn-success hover-link" onclick="edit('+roomlist.length+',this)"> <i class="fa fa-edit fa-1x"></i></a></div></div></div>';
			 }
			 else{
				 tda='<div class="col-md-2 3 item"><div class="filter-content"><a href="#" class="btn btn-sq btn-danger col-md-12" '
						+'style="height: 120px"> <br /> <span>'+json.roomnumber+'</span> <br />'+
					'</a><div class="hover-content animated fadeOut"><h4></h4><a class="btn btn-success hover-link" onclick="edit('+roomlist.length+',this)"> <i class="fa fa-edit fa-1x"></i></a></div></div></div>';
			 }
//			var tda='<div class="col-md-2 isotope-item item"><div class="filter-content"><a href="#" class="btn btn-sq btn-primary col-md-12" '
//				+'style="height: 120px"> <br /> <span>'+json['roomnumber']+'</span> <br />'+
//			'</a><div class="hover-content"><h4></h4><a class="btn btn-success hover-link" onclick="edit('+roomlist.length+',this)"> <i class="fa fa-edit fa-1x"></i></a></div></div></div>';
			$('#filter-items').append(tda);
			$('#addModal').modal('hide');
			roomlist.push(json);
			build();
			alert("添加成功！");
		},
		error : function() {
			alert("系统异常！");
		}
		})
})