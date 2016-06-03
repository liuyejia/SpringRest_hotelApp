package rest.cashier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rest.common.OrderStatus;
import rest.domain.Horder;
import rest.domain.Room;
import rest.domain.RoomType;
import rest.domain.TypeDate;
import rest.repository.OrderRepository;
import rest.repository.RoomRepository;
import rest.repository.TypeDateRepository;

@Controller
public class Quit {

	@Autowired
	RoomRepository roomRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	TypeDateRepository tdRepository;
	
	@RequestMapping("/staff/quit")
	@ResponseBody
	public String quit(@RequestParam(value="roomnumber",required=true)String roomnumber){
		roomRepository.quitRoom(roomnumber);
		List<Room> list = roomRepository.findByRoomnumber(roomnumber);
		Horder order = list.get(0).getOrderid();
		
		if(order != null) orderRepository.setStatus(OrderStatus.complete.value(), order.getId());
		return "SUCCESS";
	}
	
	@RequestMapping("/staff/continue")
	@ResponseBody
	public String ruzhu(@RequestParam(value="roomnumber",required=true)String roomnumber,@RequestParam(value="day",required=true)int day){
		List<Room>list = roomRepository.findByRoomnumber(roomnumber);
		
		Horder order = list.get(0).getOrderid();
		Long timetemp = order.getPosttime();
		
		Long temp = (long)86400;
		for(int i = 0; i < day; i++){
			List<TypeDate>list1 = tdRepository.findByRoomtypeAndIndate(list.get(0).getRoomtype(), timetemp+temp*(i+1));
			if(list1.size()>0){
				tdRepository.setIndate(timetemp+temp*(i+1), list.get(0).getRoomtype());
			}else{
				TypeDate td = new TypeDate();
				td.setIndate(timetemp+temp*(i+1));
				td.setNumber(1);
				td.setRoomtype(list.get(0).getRoomtype());
				tdRepository.save(td);
			}
		}
		return null;
	}
	
}
