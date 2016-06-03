package rest.cashier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import rest.common.RoomStatus;
import rest.domain.Customer;
import rest.domain.Horder;
import rest.domain.Room;
import rest.repository.CustomerRepository;
import rest.repository.OrderRepository;
import rest.repository.RoomRepository;




@Controller
public class NewGuest {

	@Autowired
	RoomRepository roomRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CustomerRepository cusRepository;
	
	Room room;
	Long pret;
	Long postt;
	
	@RequestMapping("/staff/ruzhu")
	public ModelAndView ruzhu(@RequestParam(value="roomnumber",required=true)String roomnumber,
			@RequestParam(value="pretime",required=true)String pretime,
			@RequestParam(value="posttime",required=true)String posttime) throws ParseException{
		
		List<Room>list = roomRepository.findByRoomnumber(roomnumber);
		room = list.get(0);
		
		System.out.println(pretime);
		String test="2016-04-01";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = format.parse(pretime);
		Date date2 = format.parse(posttime);
		Date date3 = format.parse(test);
		System.out.println(date2+"-"+date1+"-"+date3);
		Long time = date2.getTime()-date1.getTime();
		System.out.println(time+"time");
		
		
		pret = date1.getTime()/1000;
		postt = date2.getTime()/1000;
		
		return new ModelAndView("/ruzhu").addObject("room", list.get(0))
				.addObject("pretime", pretime)
				.addObject("posttime", posttime)
				.addObject("day", time/1000/24/3600);
	}
	
	@RequestMapping("/staff/ruzhu_ok")
	@ResponseBody
	public String ruzhu_ok(@RequestParam(value="name",required=false)String name,
			@RequestParam(value="indentity",required=false)String indentity,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="purpose",required=false)String purpose,
			@RequestParam(value="cost",required=false)String cost){
		
		System.out.println(cost);
		double cost1 = Double.parseDouble(cost);
		
		room.setStatus(RoomStatus.used.value());
		roomRepository.save(room);
		
		Horder order = new Horder();
		order.setCustomername(name);
		order.setEmail(email);
		order.setIndentity(indentity);
		order.setPhone(phone);
		order.setPretime(pret);
		order.setPosttime(postt);
		order.setPurpose(purpose);
		order.setCost(cost1);
		order.setRoomtype(room.getRoomtype());
		
		orderRepository.save(order);
		
		Customer c;
		if(cusRepository.findByIdentity(indentity) == null){
			c = new Customer();
		}else
			c = cusRepository.findByIdentity(indentity);
		c.setIdentity(indentity);
		c.setName(name);
		int sex = Integer.parseInt(indentity.substring(16, 17));
		String sexStr = "";
		if(sex%2 == 0) sexStr = "female";
		else sexStr = "male";
		int year = Integer.parseInt(indentity.substring(6, 10));
		c.setBirthyear(year);
		c.setGender(sexStr);
		//System.out.println(indentity.substring(16, 17)+"---"+indentity.substring(6, 10));
		cusRepository.save(c);
		
		return null;
	}
}
