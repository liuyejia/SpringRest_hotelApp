package springrest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rest.Application;
import rest.common.TypeDateDTO;
import rest.domain.Room;
import rest.domain.RoomType;
import rest.repository.RoomRepository;
import rest.repository.RoomTypeRepository;
import rest.repository.TypeDateRepository;
import rest.service.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class TypeDateRepositoryTest {

	@Autowired
    TypeDateRepository typeRepository;
	
	@Autowired
    RoomTypeRepository roomtypeRepository;
	
	@Autowired
    RoomRepository roomRepository;
	
	@Autowired
    OrderService orderService;
	
//	@Test
//	public void testMax(){
//		//获取最大订单数
//		RoomType type=roomtypeRepository.findOne(1L);
//		int max=typeRepository.findMaxNumber(type, 1L, 15L);
//		assertEquals(max,12);
//	}
//	@Test
//	public void testDate(){
//		//获取指定数量上限的已用日期
//		RoomType type=roomtypeRepository.findOne(2L);
//		//int min=typeRepository.findMinNumber(type, 1L, 15L);
//		Pageable pageable=new PageRequest(0, 10);
//		List<Long> date=typeRepository.findUseDate(type, 1461196800L, pageable);
//		for(int i=0;i<date.size();i++){
//			System.out.println(date.get(i));
//		}
//		assertEquals(date.size(),5);
//	}
//	@Test
//	public void testUpdate(){
//		//批量更新指定日期的订单数(订单+1)
//		RoomType type=roomtypeRepository.findOne(1L);
//		List<Long> date=new ArrayList<Long>();
//		date.add(1461456000L);
//		date.add(1461283200L);
//		//date.add(14L);
//		int result=typeRepository.setBookNumber(2,type, date);
//		//assertEquals(result,1);
//		assertTrue(result > 0);
//	}
//	
//	@Test
//	public void testSelect(){
//		//获取所有类型房间的最大订单数
//		List<TypeDateDTO> list=typeRepository.findMaxNumberByType(1461196800L,1461369600L);
//		System.out.println(list.get(0).getMaxNumber());
//		assertEquals(list.size(),3);
//	}
	@Test
	public void testInsert(){
		List<RoomType> type=(List<RoomType>) roomtypeRepository.findAll();
		List<Room> room1=new ArrayList<Room>();
		
		for(int i=1;i<=type.size();i++){
			room1.clear();
			for(int j=2;j<=50;j++){
				Room room=new Room();
				String number="F"+i+"-"+j;
				room.setFloor(i);
				room.setRoomnumber(number);
				room.setRoomtype(type.get(i-1));
				room.setStatus(1);
				room1.add(room);
			}
			roomRepository.save(room1);
		}
	}
}
