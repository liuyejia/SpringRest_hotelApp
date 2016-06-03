package rest.cashier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import rest.common.OrderStatus;
import rest.domain.Horder;
import rest.repository.OrderRepository;

@Controller
public class Checking {
	
	@Autowired
	OrderRepository orderRepository;

	@RequestMapping("staff/checking")
	public ModelAndView lookFor(){
		return new ModelAndView("/checkings");
	}
	
	@RequestMapping("/staff/checking/search")
	@ResponseBody
	public List<Horder> search(@RequestParam(value="identity",required=false)String id){
		
		List<Horder> list1 = orderRepository.findByPhoneOrIndentity(id,id);
		//System.out.println(list1.size()+"hugh");
		for(int i = 0;i<list1.size();i++){
			if(list1.get(i).getStatus()!=OrderStatus.booked.value())
				list1.remove(i);
		}
		return list1;
	}
}
