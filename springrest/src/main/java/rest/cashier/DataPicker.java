package rest.cashier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rest.common.BQDTO;
import rest.common.GQDTO;
import rest.common.RNDTO;
import rest.domain.RoomType;
import rest.repository.OrderRepository;
import rest.repository.TypeDateRepository;

@Controller
public class DataPicker {

	@Autowired
	TypeDateRepository tdRepository;
	@Autowired
	OrderRepository orderRepository;
	
	
	
	@RequestMapping("/admin/charts-manage/getdefaultdata")
	@ResponseBody
	public Map getDataDefault(@RequestParam(value="pretime",required=true)Long pretime,
			@RequestParam(value="posttime",required=true) Long posttime,
			@RequestParam(value="index",required=true) int index){
		List<Double> list1 = time_income(pretime,posttime,index);
		List<Long> list2 = time_order(pretime,posttime,index);
		List<Double> temp = new ArrayList<Double>();
		
		List<Double> list3 = new ArrayList<Double>();
		
		for(int i = 0; i < list1.size(); i++){
			temp.add((double)list2.get(i));
			if(list2.get(i)==0)
				list3.add((double)0);
			else
				list3.add(list1.get(i)/list2.get(i));
		}
		
		Map<String,List<Double>> map = new HashMap<String,List<Double>>();
		map.put("income", list1);
		map.put("order", temp);
		map.put("price", list3);
		
		return map;
	}
	
	@RequestMapping("/admin/charts-manage/gettodata")
	@ResponseBody
	public List<RNDTO> getTOData(@RequestParam(value="pretime",required=true)Long pretime,
			@RequestParam(value="posttime",required=true) Long posttime){
		
//		RoomType rt1 = new RoomType();
//		RoomType rt2 = new RoomType();
//		RoomType rt3 = new RoomType();
//		
//		rt1.setId((long)1);
//		rt2.setId((long)2);
//		rt3.setId((long)3);
//		int []num = new int[3];
//		
//		num[0] = tdRepository.getOrderByTypeTime(rt1, pretime, posttime);
//		num[2] = tdRepository.getOrderByTypeTime(rt2, pretime, posttime);
//		num[3] = tdRepository.getOrderByTypeTime(rt3, pretime, posttime);
		
		List<RNDTO> list = tdRepository.getOrderByTypeTime(pretime, posttime);
		
		return list;
	}
	
	@RequestMapping("/admin/charts-manage/getgqdata")
	@ResponseBody
	public List<GQDTO> getGQData(@RequestParam(value="pretime",required=true)Long pretime,
			@RequestParam(value="posttime",required=true) Long posttime){
		List<GQDTO>list = orderRepository.getGenderOrder(pretime,posttime);
//		long[] temp = {0,0,0};
//		for(int i = 0; i < list.size(); i++){
//			temp[list.get(i).get]
//		}
		return list;
	}
	

	@RequestMapping("/admin/charts-manage/getbqdata")
	@ResponseBody
	public List<BQDTO> getBQData(@RequestParam(value="pretime",required=true)Long pretime,
			@RequestParam(value="posttime",required=true) Long posttime){
		List<BQDTO>list = orderRepository.getBirthOrder(pretime,posttime);
		
		return list;
	}
	
	
	
	
	public List<Double> time_income(Long pretime,Long posttime,int index){
		List<Double>list = new ArrayList<Double>();
		
		Long[] time = getTimeArray(pretime,posttime,index);
		
		Double temp = 0.0;
		for(int i = 0; i < index; i++){
			if(i == index-1)
				temp = tdRepository.findIncomeByTime(time[i],posttime);
			else
				temp = tdRepository.findIncomeByTime(time[i],time[i+1]);
			if(temp == null){
				temp=0.0;
			}
			list.add(temp);
		}
		
		return list;
	}
	
	public List<Long> time_order(Long pretime,Long posttime,int index){
		List<Long>list = new ArrayList<Long>();
		
		Long[] time = getTimeArray(pretime,posttime,index);
		Long temp = (long)0;
		for(int i = 0; i < index; i++){
			if(i == index-1)
				temp = tdRepository.findOrderByTime(time[i],posttime);
			else
				temp = tdRepository.findOrderByTime(time[i],time[i+1]);
			if(temp == null){
				temp=(long)0;
			}
			list.add(temp);
		}
		
		return list;
	}
	
	public Long[] getTimeArray(Long pretime,Long posttime,int index){
		Long[] time = new Long[index];
		Long gap = (posttime-pretime)/index;
		for(int i = 0; i < index; i++){
			time[i] = pretime+i*gap;
		}
		
		return time;
	}
}
