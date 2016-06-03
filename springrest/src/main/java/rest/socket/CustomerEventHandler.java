package rest.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import rest.Application;
import rest.domain.Customer;
import rest.domain.Horder;

@Component
@RepositoryEventHandler(Customer.class)
public class CustomerEventHandler {

	private final SimpMessagingTemplate websocket;

	private final EntityLinks entityLinks;
	
	@Autowired
	public CustomerEventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
		this.websocket = websocket;
		this.entityLinks = entityLinks;
	}
	
	@HandleAfterCreate
	public void newEmployee(Customer employee) {
		this.websocket.convertAndSend(
				Application.MESSAGE_PREFIX + "/newCustomer", getPath(employee));
	}
	private String getPath(Customer employee) {
		return this.entityLinks.linkForSingleResource(employee.getClass(),
				employee.getId()).toUri().getPath();
	}
}
