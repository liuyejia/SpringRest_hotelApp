package rest.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import rest.domain.RoomType;
import rest.repository.RoomRepository;
import rest.repository.RoomTypeRepository;

@Controller
public class CommonContoller {
	@Autowired
	RoomTypeRepository typeRepository;
	@Autowired
	RoomRepository roomRepository;

	// -------------------------用户预定的url---------------
	@RequestMapping("/hotel/")
	public ModelAndView userindex() {
		return new ModelAndView("/front/index", "type",
				typeRepository.findAll());
		// return "/front/index";
	}

	@RequestMapping("/hotel/about-us/")
	public String aboutus() {
		return "/front/about_us";
	}

	@RequestMapping("/hotel/book-page")
	public String bookpage() {
		return "/front/book_page";
	}

	@RequestMapping("/hotel/contact-us")
	public String contact() {
		return "/front/contact_us";
	}

	@RequestMapping("/hotel/order-search")
	public String ordersearch() {
		return "/front/order_search";
	}

	@RequestMapping("/hotel/order")
	public String order() {
		return "/front/order";
	}

	@RequestMapping("/hotel/room-details")
	public String details() {
		return "/front/room_details";
	}

	// -------------------------接待人员的url---------------
	@RequestMapping("/staff/")
	public String staffindex() {
		return "/back_room";
	}

	// -------------------------管理员的url---------------
	@RequestMapping("/admin/")
	public String adminindex() {
		return "/index";
	}

	@RequestMapping("/admin/charts-manage/")
	public String chart() {
		return "/charts_manage";
	}

	@RequestMapping("/admin/insert-type/")
	public String insertType() {
		return "/style_insert";
	}

	@RequestMapping("/admin/room-manage/")
	public ModelAndView room() {
		//return "/room_manage";
		return new ModelAndView("/room_manage");
//		Iterable<RoomType> type = typeRepository.findAll();
//
//		if (type == null) {
//			return new ModelAndView("/room_manage", "type", type);
//		}
//
//		return new ModelAndView("/room_manage", "types", type).addObject(
//				"rooms", roomRepository.findByRoomtype(type.iterator().next()));
	}

	@RequestMapping("/admin/order-manage/")
	public ModelAndView adminorder() {
		// List<RoomType> type=(List<RoomType>) typeRepository.findAll();
		return new ModelAndView("/order_manage", "type",
				typeRepository.findAll());
	}

	@RequestMapping("/admin/account-manage/")
	public String accountManagey() {
		return "/account_manage";
	}

	// 管理员登录
	@RequestMapping("/backlogin/")
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			HttpSession session) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			// 获取登录 的错误信息
			// Exception ex=(Exception)
			// session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
			// if(ex!=null)
			// System.out.println(ex.getMessage());
			model.addObject("error", "账号或密码错误!");
		}
		// return "/back/login.html";
		model.setViewName("/login");

		return model;
	}

	// 管理员退出
	@RequestMapping("/backlogout/")
	public String logout(HttpSession session) {
		// 退出时使session失效
		session.invalidate();
		return "redirect:/backlogin/";
	}
}
