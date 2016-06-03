$("#insert-btn").click(function() {
	
//$('#insertform').submit();
	// 上传文件
	$.ajaxFileUpload({
		url : '/admin/add-roomtype/',// 处理图片脚本
		secureuri : false,
		fileElementId : 'file',// file控件id
		dataType : 'jsonp',
		data : {
			name:$('#name').val(),
			total:$('#total').val(),
			remain:$('#remain').val(),
			facility:$('#facility').val(),
		    introduction:$('#introduction').val(),
			price:$('#price').val(),
			discount:$('#discount').val()
		},
		success : function(data, status) {
			alert('添加成功！')
		},
		error : function(data, status, e) {
			alert("123系统异常！");
		}
	});
})
//图片上传预览
function readURL(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#type-img').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}
$("#file").change(function(){
    readURL(this);
});