jQuery(document).ready(function() {	
	//var url=window.location.search;
	var url=window.location.host;
	$("a[class=account-manage]").attr("href","http://"+url+"/admin/account-manage/");
	$("a[class=order-manage]").attr("href","http://"+url+"/admin/order-manage/");
	$("a[class=charts-manage]").attr("href","http://"+url+"/admin/charts-manage/");
	$("a[class=room-manage]").attr("href","http://"+url+"/admin/room-manage/");
	$("a[class=logout]").attr("href","http://"+url+"/backlogout/");
	
	
  });