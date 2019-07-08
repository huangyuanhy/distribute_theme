package com.taotao.web.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtils;
import com.taotao.common.utils.IDutil;
/**
 * 1、使用java代码访问ftp服务器
使用apache的FTPClient工具访问ftp服务器。
	<groupId>commons-net</groupId>
	<artifactId>commons-net</artifactId>

 * @author pc
 *
 */
public class FTPClientTest {
	@Test
	public void test1(){
		//1、连接ftp服务器
		FTPClient ftpClient = null;
		
		try {
			ftpClient=new FTPClient();
			ftpClient.connect("47.106.39.238", 21);
			//2、登录ftp服务器
			ftpClient.login("ftpuser", "925925");//登陆完成后就在家目录下/home/ftpuser/

			ftpClient.setConnectTimeout(50000);
			//设置被动模式 随机选择一个端口 难道是由于云服务器没有开放随机端口造成的？？？？总之这句话不加 就成功了
			//ftpClient.enterLocalPassiveMode();
			 int reply = ftpClient.getReplyCode();
			 /**
			 220：表示服务就绪，但此时并没有登陆成功
				230：连接且登陆成功 
				530：未登录，登陆错误（用户名或密码错误）
			  */
		        System.out.println(reply);//230 连接且登陆成功
			//3、读取本地文件
			FileInputStream inputStream = new FileInputStream(new File("G:\\图片\\1111.jpg"));
			//4、上传文件
			//1）指定上传目录  不能创建多级目录
			//ftpClient.changeWorkingDirectory("/home/ftpuser");
			//2）指定文件类型
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			//第一个参数：文件在远程服务器的名称
			//第二个参数：文件流
			boolean storeFile = ftpClient.storeFile("hello2.jpg", inputStream);
			System.out.println(storeFile);
			//5、退出登录
			ftpClient.logout();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 public static void main(String[] args)throws FileNotFoundException {
		FtpUtils ftp = new FtpUtils();
		InputStream in=new FileInputStream(new File("G:\\图片\\1111.jpg"));
		boolean uploadFile = ftp.uploadFile("47.106.39.238", 21, "ftpuser","925925", "/2019/07/09", "1.jpg", in );
		System.out.println(uploadFile);
	}
}
