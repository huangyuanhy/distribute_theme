package com.taotao.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpUtils {
	 
	   private  FTPClient ftpClient = new FTPClient();;

	   /**
	    * 初始化ftp服务器
	 * @return 
	 * @throws IOException 
	    */
	   private  void initFtpClient(String hostname,Integer port,String username,String password) throws IOException {
	       try {
	    	   if (ftpClient!=null) {
	    		   ftpClient.connect(hostname, port); //连接ftp服务器
	    		   ftpClient.login(username, password);//登录ftp服务器
	    		   ftpClient.setControlEncoding("utf-8");
	    		   ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			}
	       } catch (MalformedURLException e) {
	           e.printStackTrace();
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
	   }

	   /**
	    * 上传文件
	    *
	    * @param pathname       ftp服务保存地址
	    * @param fileName       上传到ftp的文件名
	    * @param originfilename 待上传文件的名称（绝对地址） *
	    * @return
	    */
	   public  boolean  uploadFile(String hostname,Integer port,String username,String password,
			   String pathname, String fileName, String originfilename) {
	       boolean flag = false;
	       InputStream inputStream = null;
	       try {
	           inputStream = new FileInputStream(new File(originfilename));
	           initFtpClient(hostname, port, username, password);
	           ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	           if (!existFile(pathname)) {
	               createDirecroty(pathname);
	           }
	           ftpClient.changeWorkingDirectory(pathname);
	           flag = ftpClient.storeFile(fileName, inputStream);
	       } catch (Exception e) {
	           e.printStackTrace();
	       } finally {
	           try {
	               ftpClient.logout();
	           } catch (IOException e) {
	               e.printStackTrace();
	           }
	           if (ftpClient.isConnected()) {
	               try {
	                   ftpClient.disconnect();
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	           if (null != inputStream) {
	               try {
	                   inputStream.close();
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	       }
	       return flag;
	   }

	   /**
	    * 上传文件
	    *
	    * @param pathname    ftp服务保存地址
	    * @param fileName    上传到ftp的文件名
	    * @param inputStream 输入文件流
	    * @return
	    */
	   public  boolean uploadFile(String hostname,Integer port,String username,String password,
			   String pathname, String fileName, InputStream inputStream) {
	       try {
	    	   
	           initFtpClient(hostname, port, username, password);
	           if (!existFile(pathname)) {
	               createDirecroty(pathname);
	           }
	           ftpClient.changeWorkingDirectory(pathname);
	           ftpClient.storeFile(fileName, inputStream);
	           inputStream.close();
	           ftpClient.logout();
	       } catch (Exception e) {
	           e.printStackTrace();
	           return false;
	       } finally {
	           if (ftpClient.isConnected()) {
	               try {
	                   ftpClient.disconnect();
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	           if (null != inputStream) {
	               try {
	                   inputStream.close();
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	       }
	       return true;
	   }

	   //改变目录路径
	   public  boolean changeWorkingDirectory(String directory) {
	       boolean flag = true;
	       try {
	           flag = ftpClient.changeWorkingDirectory(directory);
	           if (flag) {
	               //System.out.println("进入文件夹" + directory + " 成功！");

	           } else {
	               //System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");
	           }
	       } catch (IOException ioe) {
	           ioe.printStackTrace();
	       }
	       return flag;
	   }

	   //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
	   public   boolean createDirecroty(String remote) throws IOException {
	       String directory = remote + "/";
	       // 如果远程目录不存在，则递归创建远程服务器目录
	       if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(directory)) {
	           int start = 0;
	           int end = 0;
	           if (directory.startsWith("/")) {
	               start = 1;
	           }
	           end = directory.indexOf("/", start);
	           String path = "";
	           StringBuilder paths = new StringBuilder();
	           while (true) {
	               String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
	               path = path + "/" + subDirectory;
	               if (!existFile(path)) {
	                   if (makeDirectory(subDirectory)) {
	                       changeWorkingDirectory(subDirectory);
	                   } else {
	                       changeWorkingDirectory(subDirectory);
	                   }
	               } else {
	                   changeWorkingDirectory(subDirectory);
	               }
	               paths.append("/").append(subDirectory);
	               start = end + 1;
	               end = directory.indexOf("/", start);
	               // 检查所有目录是否创建完毕
	               if (end <= start) {
	                   break;
	               }
	           }
	       }
	       return true;
	   }

	   //判断ftp服务器文件是否存在
	   public  boolean  existFile(String path) throws IOException {
	       boolean flag = false;
	       /*FTPFile[] ftpFileArr = ftpClient.listFiles(path);
	       if (ftpFileArr.length > 0) {
	           flag = true;
	       }*/
	       return true;
	   }

	   //创建目录
	   public  boolean  makeDirectory(String dir) {
	       boolean flag = true;
	       try {
	           flag = ftpClient.makeDirectory(dir);
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return flag;
	   }

	   /**
	    * 下载文件 *
	    *
	    * @param pathname  FTP服务器文件目录 *
	    * @param filename  文件名称 *
	    * @param localpath 下载后的文件路径 *
	    * @return
	    */
	   public boolean downloadFile(String hostname,Integer port,String username,String password,
			   String pathname, String filename, String localpath) {
	       boolean flag = false;
	       OutputStream os = null;
	       try {
	           initFtpClient(hostname, port, username, password);
	           //切换FTP目录
	           ftpClient.changeWorkingDirectory(pathname);
	           FTPFile[] ftpFiles = ftpClient.listFiles();
	           for (FTPFile file : ftpFiles) {
	               if (filename.equalsIgnoreCase(file.getName())) {
	                   File localFile = new File(localpath + "/" + file.getName());
	                   os = new FileOutputStream(localFile);
	                   ftpClient.retrieveFile(file.getName(), os);
	                   os.close();
	               }
	           }
	           ftpClient.logout();
	           flag = true;
	       } catch (Exception e) {
	           e.printStackTrace();
	       } finally {
	           if (ftpClient.isConnected()) {
	               try {
	                   ftpClient.disconnect();
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	           if (null != os) {
	               try {
	                   os.close();
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	       }
	       return flag;
	   }

	   /**
	    * 删除文件 *
	    *
	    * @param pathname FTP服务器保存目录 *
	    * @param filename 要删除的文件名称 *
	    * @return
	    */
	   public boolean deleteFile(String hostname,Integer port,String username,String password,String pathname, String filename) {
	       boolean flag = false;
	       try {
	           initFtpClient(hostname, port, username, password);
	           //切换FTP目录
	           ftpClient.changeWorkingDirectory(pathname);
	           ftpClient.dele(filename);
	           ftpClient.logout();
	           flag = true;
	       } catch (Exception e) {
	           e.printStackTrace();
	       } finally {
	           if (ftpClient.isConnected()) {
	               try {
	                   ftpClient.disconnect();
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	       }
	       return flag;
	   }
}
