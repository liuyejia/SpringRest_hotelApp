var time1;
var time2;
var inde;
var yyyy = moment().year();
var choice = 0;

$(document).ready(function(){
	$.ajax({
		url:"/admin/charts-manage/getdefaultdata",
		data:{
			posttime:moment(new Date()).add(1,"day").hours(0).minutes(0).seconds(0).unix(),
			pretime:moment(new Date()).subtract(30,"day").hours(0).minutes(0).seconds(0).unix(),
			index:30
		},
		success:function(data){
			var len = data.income.length;
			var data1 = [];
			var data2 = [];
			var data3 = [];
			for(var i = 0; i < len; i++){
				data1.push([i-len+1,data.income[i]]);
				data2.push([i-len+1,data.order[i]]);
				data3.push(i-len+1,data.price[i]);
				//console.log(data.income[i]+"-"+data.order[i]+"-"+data.price[i]);
			}
			
			
			$.plot($("#chart_1"),[{
				label:"income",
				data:data1
			},{
				label:"order",
				data:data2
			},{
				label:"price",
				data:data3
			},
			],{
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                label:{show:true},
                xaxis: {
                    
                },
                yaxis: {
                    ticks: 20,
                    min: 0,
                    max: 100,
                },
                grid: {
					borderWidth: 1
                },
			colors: ["#70AFC4", "#D9534F", "#A8BC7B", "#F0AD4E"]
            }
            );
		}
	});
})


$(document).on('click','#href1',function(e){
	time2 = moment().add(1,"day").hours(0).minutes(0).seconds(0).unix();
	time1 = moment().subtract(30,"day").hours(0).minutes(0).seconds(0).unix();
	inde = 30;
})

$(document).on('click','#href2',function(e){
	time2 = moment().add(1,"month").hours(0).minutes(0).seconds(0).unix();
	time1 = moment().subtract(12,"month").hours(0).minutes(0).seconds(0).unix();
	inde = 12;
})

$(document).on('click','#href3',function(e){
	time2 = moment().add(1,"year").hours(0).minutes(0).seconds(0).unix();
	time1 = moment().subtract(6,"year").hours(0).minutes(0).seconds(0).unix();
	inde = 6;
})

$(document).on('click','.calcu',function(e){
	$.ajax({
		url:"/admin/charts-manage/getdefaultdata",
		data:{
			posttime:time2,
			pretime:time1,
			index:inde
		},
		success:function(data){
			var len = data.income.length;
			var data1 = [];
			var data2 = [];
			var data3 = [];
			for(var i = 0; i < len; i++){
				data1.push([i-len+1,data.income[i]]);
				data2.push([i-len+1,data.order[i]]);
				data3.push(i-len+1,data.price[i]);
				//console.log(data.income[i]+"-"+data.order[i]+"-"+data.price[i]);
			}
			
			
			$.plot($("#chart_1"),[{
				label:"income",
				data:data1
			},{
				label:"order",
				data:data2
			},{
				label:"price",
				data:data3
			},
			],{
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    
                },
                yaxis: {
                    ticks: 20,
                    min: 0,
                    max: 100
                },
                grid: {
					borderWidth: 1
                },
			colors: ["#70AFC4", "#D9534F", "#A8BC7B", "#F0AD4E"]
            }
            );
		}
	});
})




















$(document).on('click',"#typebutton",function(e){
	var start = moment().year();
	var html = "";
	for(var i = 0; i < 6; i++){
		html+='<option>'+(start-i)+'</option>';
	}
	$("#quantity").html(html);
	
	sendRequest(start);
})

$(document).on('change',"#quantity",function(e){
	var temp = $("#quantity").val();
	var ye = parseInt(temp);
	sendRequest(ye);
})

function sendRequest(y){
	var pre = moment().year(y).months(0).dates(1).hours(0).minutes(0).seconds(0).unix();
	var post = moment().year(y+1).months(0).dates(1).hours(0).minutes(0).seconds(0).unix();
	console.log(post);
	
	$.ajax({
		url:"/admin/charts-manage/gettodata",
		data:{
			pretime:pre,
			posttime:post
			
		},
		success:function(data){
			//console.log(data[0].roomtype.name);
			var data1 = [];
			var data2 = [];
			for(var i = 0; i < data.length; i++){
				data1.push([i,data[i].number]);
				data2.push([i,data[i].roomtype.name]);
			}
			console.log(data.length);
			$.plot($("#chart_2"),[{
				label:"order",
				data:data1
			}
			],{
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    ticks:[0,data2[0],data2[1],data2[2]
                           ]
                },
                yaxis: {
                    ticks: 10,
                    min: 0,
                    max: 10
                },
                grid: {
					borderWidth: 0
                },
			colors: ["#70AFC4", "#D9534F", "#A8BC7B", "#F0AD4E"]
            }
            );
			
			var pd = [];
			for(var i = 0; i < data.length; i++){
				pd[i] = {
						label:data[i].roomtype.name,
						data:data[i].number
				}
			}
			$.plot($("#chart_2_pie"), pd, {
                series: {
                    pie: {
                        show: true
                    }
                },
				colors: ["#D9534F", "#A8BC7B", "#F0AD4E", "#70AFC4", "#DB5E8C", "#FCD76A", "#A696CE"]
            });
			
			
		}
	});
}













$(document).on('click',"#customerbutton",function(e){
	var start = moment().year();
	var html = "";
	for(var i = 0; i < 6; i++){
		html+='<option>'+(start-i)+'</option>';
	}
	$("#quantity3").html(html);
	$("#pie_con_3").hide();
	sendRequest2(start);
})
$(document).on('change',"#quantity3",function(e){
	var temp = $("#quantity3").val();
	var ye = parseInt(temp);
	
	if(choice == 0){
		
		sendRequest2(ye);
	}
		
	else{
		
		sendRequest3(ye);
	}
		
})

$(document).on('click',"#a1",function(e){
	choice = 0;
	$("#pie_con_3").hide();
	sendRequest2(yyyy);
})

$(document).on('click',"#a2",function(e){
	choice = 1;
	$("#pie_con_3").show();
	sendRequest3(yyyy);
})


function sendRequest2(y){
	var pre = moment().year(y).months(0).dates(1).hours(0).minutes(0).seconds(0).unix();
	var post = moment().year(y+1).months(0).dates(1).hours(0).minutes(0).seconds(0).unix();
	
	
	$.ajax({
		url:"/admin/charts-manage/getbqdata",
		data:{
			pretime:pre,
			posttime:post
			
		},
		success:function(data){
			//console.log(data[0].roomtype.name);
			var data1 = [];
			var data2 = [];
			for(var i = 0; i < data.length; i++){
				data1.push([i,data[i].quantity]);
				data2.push([i,yyyy-data[i].year]);
			}
			console.log(data.length);
			$.plot($("#chart_3"),[{
				label:"order",
				data:data1
			}
			],{
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    
                },
                yaxis: {
                    ticks: 10,
                    min: 0,
                    max: 10
                },
                grid: {
					borderWidth: 0
                },
			colors: ["#70AFC4", "#D9534F", "#A8BC7B", "#F0AD4E"]
            }
            );
		}
	});
}

function sendRequest3(y){
	var pre = moment().year(y).months(0).dates(1).hours(0).minutes(0).seconds(0).unix();
	var post = moment().year(y+1).months(0).dates(1).hours(0).minutes(0).seconds(0).unix();
	
	
	$.ajax({
		url:"/admin/charts-manage/getgqdata",
		data:{
			pretime:pre,
			posttime:post
			
		},
		success:function(data){
			//console.log(data[0].roomtype.name);
			var data1 = [];
			var data2 = [];
			for(var i = 0; i < data.length; i++){
				data1.push([i,data[i].quantity]);
				data2.push([i,data[i].gender]);
			}
			console.log(data.length);
			$.plot($("#chart_3"),[{
				label:"order",
				data:data1
			}
			],{
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    ticks:[0,data2[0],data2[1]]
                },
                yaxis: {
                    ticks: 10,
                    min: 0,
                    max: 10
                },
                grid: {
					borderWidth: 0
                },
			colors: ["#70AFC4", "#D9534F", "#A8BC7B", "#F0AD4E"]
            }
            );
			
			var pd = [];
			for(var i = 0; i < data.length; i++){
				pd[i] = {label:data[i].gender,data:data[i].quantity};
			}
			
			//pd[1] = {label:data[1].gender,data:data[1].quantity};
			
			$.plot($("#chart_3_pie"), pd, {
                series: {
                    pie: {
                        show: true
                    }
                },
				colors: ["#D9534F", "#A8BC7B", "#F0AD4E", "#70AFC4", "#DB5E8C", "#FCD76A", "#A696CE"]
            });
			
		}
	});
}

