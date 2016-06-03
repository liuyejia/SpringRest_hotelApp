package rest.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

//这个例子用于交互式的应用
@Controller
public class GreetingController {

	// The @MessageMapping annotation ensures that if a message is sent to
	// destination "/hello",
	// then the greeting() method is called.
	// The return value is broadcast to all subscribers to "/topic/greetings" as
	// specified in the @SendTo annotation.
	// 广播给所有订阅了的相关主题的客户端
	// 服务端可以接收客户端通过主题“/app/hello”发送过来的消息，客户端需要在主题"/topic/greetings"上监听并接收服务端发回的消息
	@MessageMapping("/order")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(3000); // simulated delay
		return new Greeting("Hello, " + message.getName() + "!");
	}
	// @SendToUser，这就是发送给单一客户端的标志。本例中，客户端接收一对一消息的主题应该是“/user/” + 用户Id +
	// “/message” ,
	// 这里的用户id可以是一个普通的字符串，只要每个用户端都使用自己的id并且服务端知道每个用户的id就行
	// @MessageMapping("/message")
	// @SendToUser("/message")
	// public HelloMessage userMessage(HelloMessage userMessage) throws
	// Exception {
	// return userMessage;
	// }
}
