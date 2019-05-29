

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.clims.pojo.InstAssign;
import cn.clims.service.instrument.InstrumentService;
import cn.clims.tools.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;


public class Test {

	@org.junit.Test
	public void test() {
		try {
			String cjson = "";
			@SuppressWarnings("resource")
			ApplicationContext ctx = 
					new ClassPathXmlApplicationContext("applicationContext-*.xml");
			InstrumentService instrumentService = (InstrumentService) ctx.getBean("instrumentService");
			
			InstAssign assign = new InstAssign();
			assign.setStartPageNo(4);
			assign.setPageSize(2);
			List<InstAssign> instAssignList = instrumentService.getInstAssignList_p1(assign);
			//所有有日期的属性，都要按照此日期格式进行json转换（对象转json）
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSONArray jo = JSONArray.fromObject(instAssignList,jsonConfig);
			cjson = jo.toString();
			System.out.println(cjson);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void test2(){
		String str = ",1,3,7,10,,,,,,,";
		System.out.println("length="+str.split(",").length+" start");
		for(String s : str.split(","))
			System.out.println(s);
		System.out.print("end");
	}

}
