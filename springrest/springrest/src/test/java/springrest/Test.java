package springrest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Test {
	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

	public static void main(String[] args) {
//		String src = "admin";
//		System.out.println(PASSWORD_ENCODER.encode(src));

//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//
//			Date d1 = df.parse("2012-11-05"); // 后的时间
//			Date d2 = df.parse("2012-11-04"); // 前的时间
//			Long diff = d1.getTime() - d2.getTime(); // 两时间差，精确到毫秒
//			System.out.println(d1.getTime());
//			Long day = diff / (1000 * 60 * 60 * 24);
//			System.out.println(day);
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//		String id="450881199309131970";
//		System.out.println(id.substring(6, 10));
//		System.out.println(id.charAt(16));
		Calendar date = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 59);
		date.set(Calendar.SECOND, 59);
		//date.set(Calendar.MILLISECOND, 0);
		
		Long pretime=date.getTime().getTime()/1000;
		System.out.println(pretime);
	}
}
