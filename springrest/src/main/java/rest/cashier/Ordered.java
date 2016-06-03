package rest.cashier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rest.common.OrderStatus;
import rest.common.RoomStatus;
import rest.domain.Customer;
import rest.domain.Horder;
import rest.domain.RoomType;
import rest.repository.CustomerRepository;
import rest.repository.OrderRepository;
import rest.repository.RoomRepository;

@Controller
public class Ordered {

	@Autowired
	RoomRepository roomRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CustomerRepository cusRepository;
	
	@RequestMapping("/staff/ordered")
	@ResponseBody
	public String ordered(@RequestParam(value="roomnumber",required=true)String roomnumber,
			@RequestParam(value="orderid",required=true)String orderid){
		Long id = Long.parseLong(orderid);
		roomRepository.setRoomStatus(RoomStatus.used.value(), roomnumber);
		orderRepository.setStatus(OrderStatus.underway.value(), id);
		
		
		return null;
	}
	
	@RequestMapping("/staff/ordered/search")
	@ResponseBody
	public List<String> checkRoom(@RequestParam(value="type",required=true)String type){
		Long t = Long.parseLong(type);
		RoomType roomtype = new RoomType();
		roomtype.setId(t);
		List<String>list = roomRepository.getRoomList(roomtype);
		return list;
	}
}
