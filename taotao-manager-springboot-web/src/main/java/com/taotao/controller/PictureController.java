package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.util.JsonUtils;
import com.taotao.managerweb.util.FastDFSClient;

@Controller
public class PictureController {
	
	@Value("${TAOTAO_IMAGE_SERVER_URL}")
	private String TAOTAO_IMAGE_SERVER_URL;

	/**
	 * 上传图片
	 * 
	 * Content-Type:application/json;charset=UTF-8  responseBody默认的返回类型
	 * 可以通过produces 设置返回类型
	返回JSON格式字符串时：
	Content-Type:text/plain;charset=UTF-8  火狐浏览器也支持

	 * @param uploadFile
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody//注意，上面首先设置MIME类型来满足火狐浏览器兼容性，其次，因为你没有用response对象的printwriter来写回数据，所以这里一定要用responsebody注解来让需要返回的数据返回到页面，但是用这个注解如果返回的是map类型，他一定会期望得到回MIME类型是json的数据，而这里采用了上面的text的MIME类型，所以会报错，如何解决报错呢，就必须让返回值类型为string，因为是string，所以必须自己手动先将这个对象转化为json格式的字符串，这样期望值MIME类型就可以是text，不会报错又满足兼容性
	public String fileUpload(MultipartFile uploadFile) {
		try {
			//1、取文件的扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			//2、创建一个FastDFS的客户端
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resources/fdfs_client.conf");
			//3、执行上传处理
			String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			//4、拼接返回的url和ip地址，拼装成完整的url
			String url = TAOTAO_IMAGE_SERVER_URL + path;
			//5、返回map
			Map result = new HashMap();
			result.put("error", 0);
			result.put("url", url);
			System.out.println(result);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			//5、返回map
			Map result = new HashMap();
			result.put("error", 1);
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
		}
	}
}
