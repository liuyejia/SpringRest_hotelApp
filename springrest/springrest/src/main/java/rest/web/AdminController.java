package rest.web;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import rest.Application;
import rest.common.IndexDTO;
import rest.common.SearchCriteria;
import rest.common.TypeDateDTO;
import rest.domain.Horder;
import rest.domain.Room;
import rest.domain.RoomType;
import rest.repository.CustomerRepository;
import rest.repository.OrderPredicate;
import rest.repository.OrderRepository;
import rest.repository.RoomRepository;
import rest.repository.RoomTypeRepository;
import rest.service.OrderService;

@Controller
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	RoomTypeRepository typeRepository;

	@Autowired
	RoomRepository roomRepository;


	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin/getorders/")
	@ResponseBody
	public ResponseEntity<Page<Horder>> getOrders(SearchCriteria criteria,
			Long typenumber, int page, int size) {

		Pageable pageable = new PageRequest(page, size);
		if (typenumber != null) {
			RoomType type = typeRepository.findOne(typenumber);
			criteria.setRoomtype(type);
		}
		Page<Horder> order = orderRepository.findAll(
				OrderPredicate.getPredicate(criteria), pageable);

		return new ResponseEntity<Page<Horder>>(order, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/admin/getrooms/")
	@ResponseBody
	public ResponseEntity<List<Room>> getRooms(Long typenumber) {

		RoomType type = typeRepository.findOne(typenumber);
		List<Room> roomlist = roomRepository.findByRoomtype(type);
		if (roomlist.isEmpty()) {
			return new ResponseEntity<List<Room>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Room>>(roomlist, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/admin/checkroomno/")
	@ResponseBody
	public ResponseEntity<String> checkno(String roomnumber) {

		Long rid = roomRepository.checkNumber(roomnumber);
		if (rid==null) {
			return new ResponseEntity<String>("new",HttpStatus.OK);
		}
		return new ResponseEntity<String>("used", HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/admin/gettypes/")
	@ResponseBody
	public ResponseEntity<List<RoomType>> getTypes() {

		//RoomType type = typeRepository.findOne(typenumber);
		List<RoomType> roomlist = (List<RoomType>) typeRepository.findAll();
		if (roomlist.isEmpty()) {
			return new ResponseEntity<List<RoomType>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<RoomType>>(roomlist, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/admin/addroom/")
	@ResponseBody
	public ResponseEntity<Room> addRooms(Long typenumber,Room room) {

		RoomType type = typeRepository.findOne(typenumber);
		room.setRoomtype(type);
	
		Room newRoom =roomRepository.save(room);
		return new ResponseEntity<Room>(newRoom,HttpStatus.OK);
	}
//	@RequestMapping(method = RequestMethod.POST, value = "/admin/editroom/")
//	@ResponseBody
//	public ResponseEntity<Void> editRooms(Long typenumber,Room room) {
//
//		RoomType type = typeRepository.findOne(typenumber);
//		room.setRoomtype(type);
//	
//		
//		roomRepository.save(room);
//		return new ResponseEntity<Void>(HttpStatus.OK);
//	}
	@RequestMapping(method = RequestMethod.POST, value = "/admin/index-find/")
	@ResponseBody
	public ResponseEntity<IndexDTO> indexfind(Long today) {
		//customerRepository.count();
		IndexDTO data=orderService.findIndexContent(today);
		return new ResponseEntity<IndexDTO>(data,HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/admin/add-roomtype/")
	@ResponseBody
	public ResponseEntity<RoomType> addType(HttpServletRequest request,RoomType type,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		// 处理文件上传
		if (file != null && !file.isEmpty()) {
		type.setPicture(upload(file));
		}
		
		RoomType newType=typeRepository.save(type);
		return new ResponseEntity<RoomType>(newType,HttpStatus.OK);
	}
//上传文件操作
	private String upload(MultipartFile file) {
		//if (file != null && !file.isEmpty()) {
			File path = new File(Application.PICTURE_PATH);
			if (!path.exists())
				path.mkdirs();
			long timetemp = Calendar.getInstance().getTimeInMillis();
			int x = (int) (Math.random() * 1000);

			String origin = file.getOriginalFilename();
			String prefix = origin.substring(origin.lastIndexOf("."));
			String filename = ((Long) timetemp).toString() + x + prefix;
			
			File targetFile = new File(path, filename);
			try {
				if (!targetFile.exists())
					targetFile.createNewFile();

				file.transferTo(targetFile);
			} catch (IOException e) {
				logger.error("上传文件出现IO异常" + e.getMessage());
			}

			return filename;
		//}
		//return "";
	}
}
