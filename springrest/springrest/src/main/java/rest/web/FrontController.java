package rest.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import rest.common.OrderStatus;
import rest.common.TypeDateDTO;
import rest.domain.Horder;
import rest.domain.Manager;
import rest.domain.RoomType;
import rest.domain.User;
import rest.repository.OrderRepository;
import rest.repository.RoomTypeRepository;
import rest.repository.TypeDateRepository;
import rest.service.OrderService;

@Controller
public class FrontController {

	@Autowired
	RoomTypeRepository typeRepository;
	
	@Autowired
	TypeDateRepository typedateRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderService orderService;
	/*
	 * 显示房间详情
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/hotel/room/{id}")
	public ModelAndView roomdatail(@PathVariable("id") Long id) {
		RoomType type=typeRepository.findOne(id);
		return  new ModelAndView("/front/room_details","room",type);
	}
	/*
	 * 显示预定界面
	 */
	@RequestMapping(value="/hotel/book-room/",method = RequestMethod.POST)
	public ModelAndView book(Horder order,@RequestParam(value = "typenumber")Long typenumber){
		RoomType type=typeRepository.findOne(typenumber);
		order.setRoomtype(type);
		return  new ModelAndView("/front/order","order",order);
	}
	/*
	 * 查询指定type的可预订最大值
	 */
	@RequestMapping(value="/hotel/find-onemax/",method = RequestMethod.POST)
	@ResponseBody
	public int findMaxNumber(Long pretime,Long posttime,@RequestParam(value = "typenumber")Long typenumber){
		RoomType type=typeRepository.findOne(typenumber);
		Integer max=typedateRepository.findMaxNumber(type, pretime, posttime);
		if(max==null)
			max=0;
		int result=type.getRemain()-max;
		return (result>0)?result:0;
	}
	/*
	 * 查询所有类型的可预订的最大值
	 */
	@RequestMapping(value="/hotel/find-maxnumber/",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<TypeDateDTO>> findMaxNumberByType(Long pretime,Long posttime){
		List<TypeDateDTO> typedate = typedateRepository.findMaxNumberByType(pretime, posttime);
		if (typedate.isEmpty()) {
			return new ResponseEntity<List<TypeDateDTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<TypeDateDTO>>(typedate, HttpStatus.OK);
	}
	/*
	 * 处理订单提交
	 */
	@RequestMapping(value="/hotel/submit-order/",method = RequestMethod.POST)
	public ModelAndView submitOrder(Horder order,@RequestParam(value = "typenumber")Long typenumber){

        RoomType type=typeRepository.findOne(typenumber);
		order.setRoomtype(type);
		order=orderService.saveOrder(order);
		
		return  new ModelAndView("/front/pay","order",order);
	}
	/*
	 * 支付订单
	 */
	@RequestMapping(value="/hotel/pay-order/",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> payOrder(@RequestParam(value = "ordernumber")Long number){

		Horder order = orderRepository.findOne(number);
		if(order==null)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
		order.setStatus(OrderStatus.pay.value());
		orderRepository.save(order);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	/*
	 *用户查询订单
	 */
	@RequestMapping(value="/hotel/search-order/",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Page<Horder>> searchOrder(@RequestParam(value = "phone")String number,int page,int size){

		Pageable pageable=new PageRequest(page, size);
		Page<Horder> order = orderRepository.findByPhone(number, pageable);
		
		return new ResponseEntity<Page<Horder>>(order, HttpStatus.OK);
	}
}
