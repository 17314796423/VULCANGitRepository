package com.itheima.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class GenHtm {
	//生成静态页面的方法
	@Test
	public void genHtml() throws IOException, TemplateException{
//		第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration = new Configuration(Configuration.getVersion());
//		第二步：设置模板文件所在的路径。
		configuration.setDirectoryForTemplateLoading(new File("E:/黑马_就业班/文件资料/课件资料/淘淘商城_17天项目整合/预习资料/淘淘商城/01.教案/01.参考资料/freemarker/freemarker学习使用工程/itheima-freemarker/src/main/resources/template"));
//		第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("utf-8");
//		第四步：加载一个模板，创建一个模板对象。
		Template template = configuration.getTemplate("template.htm");
//		第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
		Map<String,Object> model = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put("hello", new Person(10055l, "奥巴马"));
		map.put("11",new Person(1002l, "特朗普"));
		model.put("map", map);
//		List<Person> list = new ArrayList<>();
//		list.add( new Person(10055l, "奥巴马"));
//		list.add(new Person(1002l, "特朗普"));
//		list.add(new Person(12366l, "邓矮子"));
//		第六步：创建一个Writer对象，一般创建一File奥巴马Writer对象，指定生成的文件名。
		FileWriter writer = new FileWriter(new File("G:/freemarker/result2.java"));
//		map.put("list", list);
//		第七步：调用模板对象的process方法输出文件。
		template.process(model, writer);
//		第八步：关闭流。
		writer.close();
	}
}
