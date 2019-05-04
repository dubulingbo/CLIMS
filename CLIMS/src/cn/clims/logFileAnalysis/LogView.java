package cn.clims.logFileAnalysis;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

 
/**
 * 根据文件大小实时读取数据
 * @author DubLBo
 *
 */
public class LogView {
 
	private long pointer = 0; //上次文件大小
	private static final Logger logger = Logger.getLogger(LogView.class);
	private SimpleDateFormat  dateFormat = new SimpleDateFormat("yyy-MM-dd,HH:mm:ss");
	private String regEx = "Caused by:(.*)";  //需要捕捉的正则表达式
	
	ScheduledExecutorService  exec = Executors.newScheduledThreadPool(1);
	
	public void realtimeShowLog(final File logFile) throws Exception{
		
		if(logFile == null) {
			throw new IllegalStateException("logFile can not be null");
		}
		
		//启动一个线程每2秒读取新增的日志信息
		exec.scheduleWithFixedDelay(new Runnable(){
			
			@Override
			public void run() {
				
				//获得变化部分
				try {
					
					long len = logFile.length();
					if(len < pointer){
						logger.info("Log file was reset. Restarting logging from start of file.");
						pointer = 0;
					}else{
					
						//指定文件可读可写
						RandomAccessFile  randomFile= new RandomAccessFile(logFile,"rw");
						
						//获取RandomAccessFile对象文件指针的位置，初始位置是0
						System.out.println("RandomAccessFile文件指针的初始位置:"+pointer); 
						
						randomFile.seek(pointer);//移动文件指针位置 
						
						String tmp = "";
						while((tmp = randomFile.readLine()) != null) {
							if(tmp!=null||tmp!=""){
								Pattern pat = Pattern.compile(regEx);
							    Matcher mat = pat.matcher(new String(tmp.getBytes("utf-8")));
							    while(mat.find()){
//								    System.out.println(mat.group(1));
							    	System.out.println("此文件中有 "+mat.groupCount()+" 处异常记录：");
							    	for(int i=1;i<=mat.groupCount();i++){
//									    System.out.println("found: " + mat.group(i));
								    	String[] messageArray = mat.group(i).split(":", 2);
								    	for(String m : messageArray)
								    		System.out.print(dateFormat.format(new Date())+" : "+m+"\t");
								    }
							    	System.out.println();
							    }
							}
							pointer = randomFile.getFilePointer();
						}
						randomFile.close();
					}
					
				} catch (Exception e) {
					//实时读取日志异常，需要记录时间和lastTimeFileSize 以便后期手动补充
					logger.error(dateFormat.format(new Date())  + " File read error, pointer: "+pointer);
				} finally {
					//将pointer 落地以便下次启动的时候，直接从指定位置获取
				}
			}
		}, 0, 80, TimeUnit.SECONDS);
	}

	
	public void stop(){
		if(exec != null){
			exec.shutdown();
			logger.info("file read stop ！");
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		LogView view = new LogView();
		File tmpLogFile = new File("E:/logs/log.log2019-04-12");
		view.pointer = 0;
		view.realtimeShowLog(tmpLogFile);
		
	}
	
}