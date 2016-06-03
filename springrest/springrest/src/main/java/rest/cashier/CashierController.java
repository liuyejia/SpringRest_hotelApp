package rest.cashier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import rest.common.RoomStatus;
import rest.domain.Room;
import rest.domain.RoomType;
import rest.repository.RoomRepository;
import rest.repository.TypeDateRepository;

@Controller
public class CashierController {
	
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	TypeDateRepository typeDateRepository;
	
	List<Room> roomList;
	

	@RequestMapping(value="/staff/check",method=RequestMethod.POST)
	@ResponseBody
	public Object showPage(@RequestParam(value="type",required=false) Long type,
			@RequestParam(value="pretime",required=false) String pretime,
			@RequestParam(value="posttime",required=false) String posttime) throws ParseException{
		
		
		//System.out.println(type+"--"+pretime);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(new Date());
		
		long timetemppre,timetemppost;
		RoomType roomType = new RoomType();
		
		
		
		if(pretime == null){
			Date date = format.parse(dateStr);
			timetemppre = timetemppost = date.getTime()/1000;
			
			roomType.setId((long)1);
		}else{
			roomType.setId(type);
			timetemppre = format.parse(pretime).getTime()/1000;
			timetemppost = format.parse(posttime).getTime()/1000;
			
		}
		
		
		roomList = roomRepository.findByRoomtype(roomType);
		for(int i = 0; i < roomList.size(); i++){
			if(roomList.get(i).getStatus() == RoomStatus.used.value()){
				if(timetemppre>roomList.get(i).getOrderid().getPosttime())
					roomList.get(i).setStatus(RoomStatus.empty.value());
			}
		}
		
		
		Integer num = typeDateRepository.findMaxNumber(roomType, timetemppre, timetemppost);
		if(num == null) num = 0;
		
		Long emptyNum = roomRepository.getNumByStatus(roomType, RoomStatus.empty.value()).get(0);
		Long occupyNum = roomRepository.getNumByStatus(roomType, RoomStatus.used.value()).get(0);
		Long faultNum = roomRepository.getNumByStatus(roomType, RoomStatus.fault.value()).get(0);
		
		//System.out.println(roomList.get(0).getRoomnumber());
		
		Map<String, Object> map1 = new HashMap<String,Object>();
		map1.put("roomList", roomList);
		map1.put("orderNum", num);
		map1.put("emptyNum", emptyNum);
		map1.put("occupyNum", occupyNum);
		map1.put("faultNum", faultNum);
		
		return map1;
	}
	
}
