package com.taotao.test;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import com.taotao.managerweb.util.FastDFSClient;

public class FastDFSTest {
	
	//@Test
	public void testFileUpload() throws IOException, MyException {
		// 1、加载配置文件，配置文件中的内容就是tracker服务的地址。
		ClientGlobal.init(this.getClass().getClassLoader().getResource("resources/fdfs_client.conf").getPath());
		// 2、创建一个TrackerClient对象。直接new一个。
		TrackerClient tClient = new TrackerClient();
		// 3、使用TrackerClient对象创建连接，获得一个TrackerServer对象。
		TrackerServer tServer = tClient.getConnection();
		// 4、创建一个StorageServer的引用，值为null
		StorageServer sServer = null;
		// 5、创建一个StorageClient对象，需要两个参数TrackerServer对象、StorageServer的引用
		StorageClient sClient = new StorageClient(tServer, sServer);
		// 6、使用StorageClient对象上传图片。
				//扩展名不带“.”
		String[] strings = sClient.upload_file("C:\\IMAGES\\1imcy4kktsf.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	//@Test
	public void testFastDFSClient() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient("classpath:fdfs_client.conf");
		String uploadFile = fastDFSClient.uploadFile("C:\\IMAGES\\timg.jpg");
		System.out.println(uploadFile);
	}
	
}
