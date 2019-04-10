package cn.clims;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mysql.jdbc.Connection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class ReportTest {

	@SuppressWarnings("resource")
	@Test
	public void test() {
		File reportFile = new File("");//读取jasper文件
		
		if(reportFile!=null&&reportFile.exists()){
			try {
				FileOutputStream fos = new FileOutputStream("d:\\a.pdf");
			
				Connection conn = null;
				ApplicationContext ctx = 
						new ClassPathXmlApplicationContext("applicationContext-mybatis.xml");
				DataSource dataSource = ctx.getBean("dataSource",DataSource.class);
				conn = (Connection) dataSource.getConnection();
				byte[] bytes=JasperRunManager.runReportToPdf(reportFile.getAbsolutePath(), null, conn);
				fos.write(bytes);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
