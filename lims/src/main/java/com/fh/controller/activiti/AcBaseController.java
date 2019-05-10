package com.fh.controller.activiti;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fh.controller.base.BaseController;
import com.fh.util.Const;
import com.fh.util.DelAllFile;
import com.fh.util.FileUpload;
import com.fh.util.PathUtil;


/** 
 * 类名称：流程管家总类
 * @author ：FH Admin fh313596790qq(青苔)
 * @date：2018年1月31日
 * @version 1.0
 */
public class AcBaseController extends BaseController{
	// 流程引擎对象
	@Autowired
	private ProcessEngine processEngine;
	// 管理流程定义  与流程定义和部署对象相关的Service
	@Autowired
	private RepositoryService repositoryService;
	// 与正在执行的流程实例和执行对象相关的Service(执行管理，包括启动、推进、删除流程实例等操作)
	@Autowired
	private RuntimeService runtimeService;
	// 历史管理(执行完的数据的管理)
	@Autowired
	private HistoryService historyService;
	
	/**添加流程模型并返回modelId
	 * @param process_id 		流程唯一标识key
	 * @param process_author 	流程作者
	 * @param name 				流程名称
	 * @param modelname 		模型名称
	 * @param description 		模型描述
	 * @param category 			模型分类
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected String createModel(String process_id,String process_author,String name,String modelname,String description,String category) throws UnsupportedEncodingException{
		
		ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvs");
        editorNode.put("resourceId", "canvs");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        // 命名空间(禁止修改)
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		// 流程节点作者
        stencilSetNode.put("author", "");
        editorNode.set("stencilset", stencilSetNode);
        ObjectNode propertiesNode = objectMapper.createObjectNode();
		// 流程唯一标识
        propertiesNode.put("process_id",process_id);
		// 流程作者
        propertiesNode.put("process_author",process_author);
		// 流程名称
        propertiesNode.put("name",name);
        editorNode.set("properties", propertiesNode);
		
        ObjectNode modelObjectNode = objectMapper.createObjectNode();
		// 模型名称
        modelObjectNode.put("name", modelname);
		// 模型版本
        modelObjectNode.put("revision", 1);
		// 模型描述
        modelObjectNode.put("description", description);
		Model modelData = repositoryService.newModel();
		// 模型分类
		modelData.setCategory(category);
		modelData.setDeploymentId(null);
		modelData.setKey(null);
		modelData.setMetaInfo(modelObjectNode.toString());
		// 模型名称
		modelData.setName(modelname);
		modelData.setTenantId("");
		modelData.setVersion(1);
		// 保存模型,存储数据到表：act_re_model 流程设计模型部署表
		repositoryService.saveModel(modelData);
		// 保存资源,存储数据到表：act_ge_bytearray 二进制数据表
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString()
				.getBytes("utf-8"));
        
        return modelData.getId();
	}
	
	/**从流程定义映射模型
	 * @param processDefinitionId //流程定义ID
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
	protected Model saveModelFromPro(String processDefinitionId) throws Exception {
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(
				processDefinitionId).singleResult();
		InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
		processDefinition.getResourceName());
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
	
		BpmnJsonConverter converter = new BpmnJsonConverter();
		ObjectNode modelNode = converter.convertToJson(bpmnModel);
		Model modelData = repositoryService.newModel();
		// 唯一标识
		modelData.setKey(processDefinition.getKey());
		// 名称
		modelData.setName(processDefinition.getName()+"(反射)");
		// 分类，默认行政审批分类
		modelData.setCategory("00102");
		modelData.setDeploymentId(processDefinition.getDeploymentId());
		// 版本
		modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(
				modelData.getKey()).count()+1)));
	
		ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
		modelData.setMetaInfo(modelObjectNode.toString());
		// 保存模型
		repositoryService.saveModel(modelData);
		// 保存资源
		repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
		bpmnStream.close();
		in.close();
		return modelData;
	}
	
	/**通过模型ID获取流程基本信息
	 * @param modelId	//流程ID
	 * @throws JsonProcessingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @return
	 */
	protected Map<String,String> getProcessProperties(String modelId) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode editorJsonNode = (ObjectNode)objectMapper.readTree(new String(repositoryService.getModelEditorSource(modelId), "utf-8")).get("properties");
		Map<String,String> map = new HashMap<String,String>(16);
		// 流程唯一标识(KEY)
		map.put("process_id",editorJsonNode.get("process_id").toString());
		// 流程作者
		map.put("process_author",editorJsonNode.get("process_author").toString());
		// 流程名称
		map.put("name",editorJsonNode.get("name").toString());
		return map;
	}
	
	/**删除模型
	 * @param modelId //模型ID
	 */
	protected void deleteModel(String modelId){
		// act_re_model 和  act_ge_bytearray 两张表中相关数据都删除
		repositoryService.deleteModel(modelId);
	}
	
	/**根据模型ID导出xml文件
	 * @param response
	 * @param modelId	//模型ID
	 * @throws Exception
	 */
	protected void exportXmlFromModelId(HttpServletResponse response, String modelId) throws Exception{
		Model modelData = repositoryService.getModel(modelId);  
		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();  
		ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
		BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(modelNode);  
		BpmnXMLConverter xmlConverter = new BpmnXMLConverter();  
		byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);  
		ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);  
		IOUtils.copy(in, response.getOutputStream());  
		String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";  
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);  
		response.flushBuffer(); 
		in.close();
	}
	
	/**根据模型ID预览xml文件
	 * @param modelId	模型ID
	 * @throws Exception
	 */
	protected String viewXmlFromModelId(String modelId) throws Exception{
		Model modelData = repositoryService.getModel(modelId);  
		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();  
		ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(
				repositoryService.getModelEditorSource(modelData.getId()));
		BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(modelNode);  
		BpmnXMLConverter xmlConverter = new BpmnXMLConverter();  
		byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);  
		ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);  
		InputStreamReader isr = new InputStreamReader(in,"utf-8");
		BufferedReader bufferedReader = new BufferedReader(isr);
		StringBuffer xmlContent = new StringBuffer(); 
		String lineTxt = null;
		while (null != (lineTxt = bufferedReader.readLine())) {
			xmlContent.append(lineTxt);
			xmlContent.append("\n");
		}
		isr.close();
		return xmlContent.toString();
	}
	
	/**判断能否正常根据模型ID导出xml文件(当没有画流程图的时候会报异常)
	 * @param response
	 * @param modelId	//模型ID
	 * @throws Exception
	 */
	protected void isCanexportXmlFromModelId(HttpServletResponse response, String modelId) throws Exception{
		Model modelData = repositoryService.getModel(modelId);  
		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();  
		ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(
				repositoryService.getModelEditorSource(modelData.getId()));
		BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(modelNode);  
		BpmnXMLConverter xmlConverter = new BpmnXMLConverter();  
		xmlConverter.convertToXML(bpmnModel);  
	}
	
	/**部署流程定义(根据modelId部署)
	 * @param modelId	//模型ID
	 * @return 部署ID
	 */
	protected String deploymentProcessDefinitionFromModelId(String modelId) throws Exception{
		Model modelData = repositoryService.getModel(modelId);
        ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(
        		repositoryService.getModelEditorSource(modelData.getId()));
        byte[] bpmnBytes = null;
		BpmnJsonConverter BpmnJsonConverter = new BpmnJsonConverter();
		BpmnModel model = BpmnJsonConverter.convertToBpmnModel(modelNode);
        bpmnBytes = new BpmnXMLConverter().convertToXML(model);
        String processName = modelData.getName() + ".bpmn20.xml";
		// 部署名称
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(modelData.getName());
		// 完成部署
        Deployment deployment = deploymentBuilder.addString(
        		processName, new String(bpmnBytes,"utf-8")).deploy();
		// 部署ID
        return deployment.getId();
	}
	
	/**部署流程定义(从Classpath)
	 * @param name		//部署名称
	 * @param xmlpath	//xml文件路径
	 * @param pngpath	//png文件路径
	 * @return 部署ID
	 */
	protected String deploymentProcessDefinitionFromClasspath(String name, String xmlpath, String pngpath){
		// 创建部署对象
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		// 部署名称
		deploymentBuilder.name(name);
		// 从文件中读取xml资源
		deploymentBuilder.addClasspathResource(xmlpath);
		// 从文件中读取png资源
		deploymentBuilder.addClasspathResource(pngpath);
		// 完成部署
		Deployment deployment = deploymentBuilder.deploy();
		// 部署ID
		return deployment.getId();
	}
	
	/**部署流程定义(从zip压缩包)
	 * @param name		//部署名称
	 * @param zippath	//zip文件路径
	 * @return 部署ID
	 * @throws FileNotFoundException 
	 */
	protected String deploymentProcessDefinitionFromZip(String name, String zippath) throws Exception{
		File outfile = new File(zippath);
		FileInputStream inputStream = new FileInputStream(outfile);
		ZipInputStream ipInputStream = new ZipInputStream(inputStream);
		// 创建部署对象
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		// 部署名称
		deploymentBuilder.name(name);
		deploymentBuilder.addZipInputStream(ipInputStream);
		// 完成部署
		Deployment deployment = deploymentBuilder.deploy();
		ipInputStream.close();
		inputStream.close();
		// 部署ID
		return deployment.getId();
	}
	
	/**根据流程定义的部署ID生成XML和PNG
	 * @param DEPLOYMENT_ID_ //部署ID
	 * @throws IOException 
	 */
	protected void createXmlAndPng(String DEPLOYMENT_ID_) throws IOException{
		// 生成先清空之前生成的文件
		DelAllFile.delFolder(PathUtil.getClasspath()+"uploadFiles/activitiFile");
		List<String> names = repositoryService.getDeploymentResourceNames(DEPLOYMENT_ID_);
        for (String name : names) {
        	if(name.indexOf("zip")!=-1)continue;
            InputStream in = repositoryService.getResourceAsStream(DEPLOYMENT_ID_, name);
			// 把文件上传到文件目录里面
            FileUpload.copyFile(in,PathUtil.getClasspath()+Const.FILEACTIVITI,name);
            in.close();  
        }  
	}
	
	/**删除部署的流程
	 * @param DEPLOYMENT_ID_ //部署ID
	 * @throws IOException 
	 */
	protected void deleteDeployment(String DEPLOYMENT_ID_) throws Exception{
		/*
		 *repositoryService.deleteDeployment(DEPLOYMENT_ID_);
		 *不带级联的删除，此删除只能删除没有启动的流程，否则抛出异常 .
		 *act_re_deployment，act_re_procdef 和  act_ge_bytearray 三张表中相关数据都删除
		 * 级联删除，不管流程是否启动，都可以删除
		 */
		repositoryService.deleteDeployment(DEPLOYMENT_ID_, true);
	}
	
	/**激活流程定义
	 * @param DEPLOYMENT_ID_ //流程定义ID
	 * @throws IOException
	 */
	protected void activateProcessDefinitionById(String DEPLOYMENT_ID_) throws IOException{
		repositoryService.activateProcessDefinitionById(DEPLOYMENT_ID_, true, null);
	}
	
	/**挂起流程定义
	 * @param DEPLOYMENT_ID_ //流程定义ID
	 * @throws IOException
	 */
	protected void suspendProcessDefinitionById(String DEPLOYMENT_ID_) throws IOException{
		repositoryService.suspendProcessDefinitionById(DEPLOYMENT_ID_, true, null);
	}

}



