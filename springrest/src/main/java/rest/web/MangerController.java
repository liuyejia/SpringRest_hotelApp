package rest.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import rest.common.SearchCriteria;
import rest.domain.Horder;
import rest.domain.Manager;
import rest.domain.RoomType;
import rest.repository.ManagerRepository;
import rest.repository.OrderPredicate;
import rest.repository.OrderRepository;
import rest.repository.RoomTypeRepository;

@RepositoryRestController
public class MangerController {

	@Autowired
	ManagerRepository managerRepository;

	@Autowired
	RoomTypeRepository typeRepository;

	@Autowired
	OrderRepository orderRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/manager/")
	public @ResponseBody ResponseEntity<?> saveManager(
			@RequestBody Manager manager) {
		// 不允许相同的账号
		if (managerRepository.findByAccount(manager.getAccount()) != null) {
			System.out.println("A manager with account " + manager.getAccount()
					+ " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		Manager newOne = managerRepository.save(manager);
		return new ResponseEntity<Manager>(newOne, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/manager/{id}")
	public @ResponseBody ResponseEntity<?> updateManager(
			@PathVariable("id") long id, @RequestBody Manager manager) {
		// 不允许相同的账号
		Manager one = managerRepository.findOne(id);
		if (one == null) {
			System.out.println("A manager with id =" + id + " not exist");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		// Manager newOne=managerRepository.save(manager);
		if (manager.getPassword() != null)
			one.setPassword("password");
		if (manager.getNumRole() != 0)
			one.setRole(manager.getNumRole());
		managerRepository.save(one);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}


}
