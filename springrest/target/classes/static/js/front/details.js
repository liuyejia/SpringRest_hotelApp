$("#book-btn").click(function() {
	$("#form-pretime").val(moment().unix());
	$("#form-posttime").val(moment().add(1, 'd').unix());
	
	$("#bookform").submit();
})