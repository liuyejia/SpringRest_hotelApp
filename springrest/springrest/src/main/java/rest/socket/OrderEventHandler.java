/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rest.socket;

//import static com.greglturnquist.payroll.WebSocketConfiguration.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import rest.Application;
import rest.domain.Horder;


@Component
@RepositoryEventHandler(Horder.class)
public class OrderEventHandler extends AbstractRepositoryEventListener {

	private final SimpMessagingTemplate websocket;

	private final EntityLinks entityLinks;

	@Autowired
	public OrderEventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
		this.websocket = websocket;
		this.entityLinks = entityLinks;
	}

	@HandleAfterCreate
	public void newEmployee(Horder employee) {
		System.out.println("new order:websocket");
		this.websocket.convertAndSend(
				Application.MESSAGE_PREFIX + "/newOrder", employee);
	}

	@HandleAfterDelete
	public void deleteEmployee(Horder employee) {
		System.out.println("delete order:websocket");
		this.websocket.convertAndSend(
				Application.MESSAGE_PREFIX + "/deleteOrder", employee);
	}

	@HandleAfterSave
	public void updateEmployee(Horder employee) {
		System.out.println("update order:websocket");
		this.websocket.convertAndSend(
				Application.MESSAGE_PREFIX + "/updateOrder", employee);
	}

	/**
	 * Take an {@link Employee} and get the URI using Spring Data REST's {@link EntityLinks}.
	 *
	 * @param employee
	 */
	private String getPath(Horder employee) {
		return this.entityLinks.linkForSingleResource(employee.getClass(),
				employee.getId()).toUri().getPath();
	}

}
