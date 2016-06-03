$(function(){
                $( "#datepicker,#datepicker1" ).datepicker();
              });
jQuery(document).ready(function() {	
	//var url=window.location.search;
	var url=window.location.host;
	$(".book-page").attr("href","http://"+url+"/hotel/book-page/");
	$(".order-search").attr("href","http://"+url+"/hotel/order-search/");
	$(".about-us").attr("href","http://"+url+"/hotel/about-us/");
	$(".contact-us").attr("href","http://"+url+"/hotel/contact-us/");
	
//	 $( ".date" ).datepicker({
//         regional  :"zh-CN",
//         minDate:moment().add(1,'d').format('YYYY-MM-DD'),
//         dateFormat: "yy-mm-dd"
//     });

  });
//(function() {
//				JC.init({
//					domainKey: ''
//				});
//				})();	
$(function() {
			var pull 		= $('#pull');
				menu 		= $('nav ul');
				menuHeight	= menu.height();

			$(pull).on('click', function(e) {
				e.preventDefault();
				menu.slideToggle();
			});

			$(window).resize(function(){
        		var w = $(window).width();
        		if(w > 320 && menu.is(':hidden')) {
        			menu.removeAttr('style');
        		}
    		});
		});