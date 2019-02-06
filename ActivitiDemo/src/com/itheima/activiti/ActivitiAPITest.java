package com.itheima.activiti;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ActivitiAPITest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义 方式一：加载单个的流程定义文件 方式二：加载zip文件
	 * @throws FileNotFoundException 
	 */
	@Test
	public void test1() throws FileNotFoundException {
		DeploymentBuilder deploymentBuilder = processEngine
				.getRepositoryService().createDeployment();

		// 方式一：加载单个的流程定义文件
		// deploymentBuilder.addClasspathResource("qjlc.bpmn");
		// deploymentBuilder.addClasspathResource("qjlc.png");
		// Deployment deployment = deploymentBuilder.deploy();

		/*ZipInputStream zipInputStream = new ZipInputStream(this.getClass()
				.getClassLoader().getResourceAsStream("process.zip"));*/
		
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(new File("d:\\process.zip")));
		// 方式二：加载zip文件
		deploymentBuilder.addZipInputStream(zipInputStream );
		deploymentBuilder.deploy();
	}
	
	/**
	 * 删除流程定义
	 */
	@Test
	public void test2(){
		String deploymentId = "1801";//部署id
		boolean cascade = true;//级联删除
		processEngine.getRepositoryService().deleteDeployment(deploymentId, cascade);
	}
	
	/**
	 * 查询流程定义
		processEngine.getRepositoryService().createDeploymentQuery().list();
		processEngine.getRuntimeService().createProcessInstanceQuery().list();
		processEngine.getTaskService().createTaskQuery().list();
		processEngine.getIdentityService().createUserQuery().list();
		processEngine.getHistoryService().createHistoricActivityInstanceQuery().list();
	 * 
	 */
	@Test
	public void test3(){
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		query.processDefinitionKey("qjlc");//根据key过滤
		query.orderByProcessDefinitionVersion().asc();
		List<ProcessDefinition> list = query.listPage(0, 10);
		for (ProcessDefinition processDefinition : list) {
			System.out.println(processDefinition.getId());
		}
	}
	
	/**
	 * 查询部署对应的流程定义文件名称和输入流
	 * @throws FileNotFoundException 
	 */
	@Test
	public void test4() throws Exception{
		String deploymentId = "1201";//部署id
		List<String> names = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
		for (String name : names) {
			InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId, name);
			/*byte[] b = new byte[1024];
			in.read(b);
			outputStream.write(b);*/
			FileUtils.copyInputStreamToFile(in, new File("d:\\"+name));
			in.close();
		}
	}
	
	/**
	 * 查询部署对应的流程定义输入流
	 * @throws FileNotFoundException 
	 */
	@Test
	public void test5() throws Exception{
		String processDefinitionId = "qjlc:1:1204";//流程定义id
		InputStream pngStream = processEngine.getRepositoryService().getProcessDiagram(processDefinitionId);
		FileUtils.copyInputStreamToFile(pngStream, new File("d:\\abc.png"));
		InputStream bpmnStream = processEngine.getRepositoryService().getProcessModel(processDefinitionId);
		FileUtils.copyInputStreamToFile(pngStream, new File("d:\\abc.bpmn"));
	}
	
	/**
	 * 根据流程定义启动流程实例
	 * 方式一：根据流程定义id启动流程实例
	 * 方式二：根据流程定义key启动流程实例（建议）-----可以根据当前最新版本的流程定义启动流程实例
	 */
	@Test
	public void test6() throws Exception{
		//方式一：根据流程定义id启动流程实例
		//String processDefinitionId = "";//流程定义id
		//processEngine.getRuntimeService().startProcessInstanceById(processDefinitionId);
		
		String processDefinitionKey = "qjlc";
		//方式二：根据流程定义key启动流程实例
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
		System.out.println(processInstance.getId() + " " + processInstance.getProcessDefinitionId());
	}
	
	/**
	 * 查询流程实例 
	 */
	@Test
	public void test7() throws Exception{
		List<ProcessInstance> list = processEngine.getRuntimeService().createProcessInstanceQuery().list();
		for (ProcessInstance processInstance : list) {
			System.out.println(processInstance.getId());
		}
	}
	
	/**
	 * 删除流程实例
	 */
	@Test
	public void test8() throws Exception{
		String processInstanceId = "2001";
		String deleteReason = "不请假了";
		processEngine.getRuntimeService().deleteProcessInstance(processInstanceId , deleteReason );
	}
	
	/**
	 * 查询个人任务
	 */
	@Test
	public void test9() throws Exception{
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.taskAssignee("李四");
		query.orderByTaskCreateTime().desc();
		List<Task> list = query.list();
		for (Task task : list) {
			System.out.println(task.getId());
		}
	}
	
	/**
	 * 办理个人任务
	 */
	@Test
	public void test10() throws Exception{
		String taskId = "2004";
		processEngine.getTaskService().complete(taskId);
	}
	
}
