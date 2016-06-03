 var stompClient = null;
 var orderlist=new Array();
 var day=moment().format('YYYY-MM-DD');
 var today=moment(day).unix();
 $(document).ready(function() {
	 $.ajax({
			url : "/admin/index-find/",
			data : JSON.stringify({
				time : today,
			}),
			cache : false,
			dataType : 'json',
			type : 'post',
			contentType:"application/json",
			success : function(json) {
				 $('#cnumber').text(json.count);
				 $('#onumber').text(json.ordernumber);
				 $('#inumber').text(json.income);
				 
				 var data=json.orderlist;
				 orderlist=data;
				 if(data.length<1)
					 $('#order-list').html('今天还没有订单'); 
				 for (var i = 0; i < data.length; i++) {
					 newOrder(data[i],1);
				 }
				 connect();
			},
			error : function() {
				alert("系统异常！");
			}
		});
//	 $.get("/admin/index-find/",function(json) {
//		 //var data=json._embedded.order;
//		 $('#cnumber').text(json.count);
//		 $('#onumber').text(json.ordernumber);
//		 $('#inumber').text(json.income);
//		 
//		 var data=json.orderlist;
//		 orderlist=data;
//		 for (var i = 0; i < data.length; i++) {
//			 newOrder(data[i],1);
//		 }
//		 connect();
//	 })
	})

        function connect() {
            var socket = new SockJS('/order');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
                //开启websocket订阅
                stompClient.subscribe('/topic/newOrder', function(orders){
                	//这里的orders是一个数据报文，数据在body中
                	 console.log("new record!"+orders.id);
                	var data=JSON.parse(orders.body)
                	orderlist.push(data);
                    newOrder(data,2);
                });
                stompClient.subscribe('/topic/updateOrder', function(orders){
                    updateOrder(orders._embedded.order);
                });
                stompClient.subscribe('/topic/newCustomer', function(users){
                    showUser(users._embedded.customer);
                });
//                stompClient.subscribe('/topic/greetings', function(users){
//                    ///showUser(users._embedded.customer);
//                	console.log("new record!"+JSON.parse(users.body).content);
//                });
            });
        }
 
        function disconnect() {
            stompClient.disconnect();
            setConnected(false);
            console.log("Disconnected");
        }
 
//        function sendName() {
//            stompClient.send("/app/order", {}, JSON.stringify({ 'name':   'fu' }));
//        }
        function showUser(message) {
          
        }
        function newOrder(data,positon) {
        	 var tda='<li class="clearfix"><div class="pull-left">'+
		     '<p><h5><strong class="orderno">#'+data.id+'</strong>'+data.customername+
			 '</h5></p><p><i class="fa fa-clock-o"></i> <abbr class="timeago" title="Oct 11, 2013">'+
			 moment(data.ordertime).fromNow()+'</abbr>'+
			'</p></div><div class="text-right pull-right"><h4 class="cost">$'+data.cost+'</h4>'+
			'<p><span class="label label-warning arrow-in-right"><i class="fa fa-cog"></i> In Progress'+
			'</span></p></div><div class="clearfix"></div>'+
				'<div class="progress progress-sm"><div class="progress-bar progress-bar-warning"'+
				'role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 75%;">'+
				'<span class="sr-only">75% Complete</span></div></div></li>';
        	 if(positon==1)
        		 $('#order-list').append(tda);
        	 else
        		 $('#order-list').prepend(tda);
        }
        function showOrder(message) {
            for(var i=0;i<orderlist.length;i++){
            	if(orderlist[i]['id']==message['id']){
            		orderlist[i]=message;
            		break;
            	}
            }
            
        }