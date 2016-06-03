package rest.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rest.common.IndexDTO;
import rest.common.OrderStatus;
import rest.common.RoomStatus;
import rest.domain.Customer;
import rest.domain.Horder;
import rest.domain.Room;
import rest.domain.RoomType;
import rest.domain.TypeDate;
import rest.repository.CustomerRepository;
import rest.repository.OrderRepository;
import rest.repository.RoomRepository;
import rest.repository.RoomTypeRepository;
import rest.repository.TypeDateRepository;
import rest.socket.OrderEventHandler;

@Service
public class OrderServiceImpl implements OrderService {

	private final long DAY=60 * 60 * 24;
	
	@Autowired
	RoomTypeRepository typeRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	TypeDateRepository typedateRepository;
	
	@Autowired
	OrderEventHandler ordereventhandler;
	
	@Override
	@Transactional
	public Horder saveOrder(Horder order) {
		
			//1.设置剩余量(这里需要设置同步访问)
//			RoomType type=order.getRoomtype();
//			type.setRemain(type.getRemain()-order.getQuantity());
//			typeRepository.save(type);
			
			//2.系统分配房间(需要同步操作)
//			Pageable pageable=new PageRequest(0, order.getQuantity());
//			List<Room> roomlist=roomRepository.findByRoomtypeAndStatus(type, RoomStatus.empty.value(), pageable);
//			//设置房间状态为已预订
//			for(Room room:roomlist){
//				room.setStatus(RoomStatus.booked.value());
//			}
//			roomRepository.save(roomlist);
//			order.setRoomlist(roomlist);
		
		
			//1.插入预定记录到typedate表
			//根据时间戳计算住宿的天数
			long diff=(order.getPosttime()-order.getPretime())/(DAY);
			//获取已经预定的日期
			Pageable pageable=new PageRequest(0, (int) diff+1);
		    List<Long> datelist=typedateRepository.findUseDate(order.getRoomtype(), 
		    		order.getPretime(), pageable);
		    //记录每一天增加的收入
		    double total=order.getCost()/order.getQuantity()/diff;
		    //批量更新预定记录
		    List<Long> updatedata=new ArrayList<Long>();
		    List<TypeDate> typedata=new ArrayList<TypeDate>();
		    for(Long data=order.getPretime();data<=order.getPosttime();data+=DAY){
		    	if(datelist.contains(data))
		    		updatedata.add(data);
		    	else{
		    		TypeDate newrecord=new TypeDate();
			    	newrecord.setIndate(data);
			    	newrecord.setNumber(order.getQuantity());
			    	newrecord.setRoomtype(order.getRoomtype());
			    	//记录这一天的收入
			    	newrecord.setTotal(total);
			    	typedata.add(newrecord);
		    	}
		    }
		    if(typedata.size()>0)
		    typedateRepository.save(typedata);
		    if(updatedata.size()>0)
		      typedateRepository.setBookNumber(order.getQuantity(),total,order.getRoomtype(), updatedata);
			//2.提交订单
			//计算总金额
			double cost=order.getRoomtype().getPrice()*order.getQuantity()*diff;
			order.setCost(cost);
			//设置订单状态
			order.setStatus(OrderStatus.booked.value());
			order.setOrdertime(new Date().getTime()/1000);
			order=orderRepository.save(order);
			
			//3.收集用户信息
			Customer customer=customerRepository.findByIdentity(order.getIndentity());
			if(customer==null){
				customer=new Customer();
				customer.setIdentity(order.getIndentity());
				customer.setName(order.getCustomername());
				customer.setBirthyear(parseBirthyear(customer.getIdentity()));
				customer.setGender(parseGender(customer.getIdentity()));
				
				customerRepository.save(customer);
			}
			ordereventhandler.newEmployee(order);
		    return order;
	}
	private int parseBirthyear(String indentity){
		return Integer.parseInt(indentity.substring(6, 10));
	}
	private String parseGender(String indentity){
		int gtag=Integer.parseInt(indentity.charAt(16)+"");
		if(gtag % 2 == 0)
			return "女";
		else 
			return "男";
	}
	@Override
	public IndexDTO findIndexContent(Long time) {
		IndexDTO data=new IndexDTO();
		data.setCount(customerRepository.count());
		TypeDate tdate=typedateRepository.findByIndate(time);
		if(tdate!=null){
			data.setIncome(tdate.getTotal());
			data.setOrdernumber(tdate.getNumber());
		}
		else{
			data.setIncome(0);
			data.setOrdernumber(0);
		}
		Calendar date = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		//date.set(Calendar.MILLISECOND, 0);
		
		Long pretime=date.getTime().getTime()/1000;
		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 59);
		date.set(Calendar.SECOND, 59);
		//date.set(Calendar.MILLISECOND, 999);
		Long posttime=date.getTime().getTime()/1000;
		data.setOrderlist(orderRepository.findByOrdertimeBetween(pretime,posttime));
		
		return data;
	}
}
