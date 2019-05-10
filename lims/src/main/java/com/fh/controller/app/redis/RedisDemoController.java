package com.fh.controller.app.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.dao.redis.RedisDao;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

/** RedisDemo
 * @author FH Q313596790
 * @date :2016.5.8
 * @version 1.0
 */
@Controller
@RequestMapping(value="/appRedisDemo")
public class RedisDemoController extends BaseController{
	
	@Resource(name = "redisDaoImpl")
	private RedisDao redisDaoImpl;
	
	/**
	 * 请讲接口 http://127.0.0.1:8080/项目名称/appRedisDemo/redisDemo.do
	 * demo展示的在redis存储读取数据的方式，本系统暂时用不到redis，此redis接口可根据实际业务需求选择使用
	 * 具体redis的应用场景->百度下即可
	 */
	@RequestMapping(value="/redisDemo")
	@ResponseBody
	public Object redis(){
		
		Map<String, Object> map = new HashMap<String, Object>(16);
		String result = "";
		// 删除
		redisDaoImpl.delete("fh0");
		// 删除
		redisDaoImpl.delete("fh");
		// 删除
		redisDaoImpl.delete("fh1");
		// 删除
		redisDaoImpl.delete("fh2");
		//存储字符串
		System.out.println(redisDaoImpl.addString("fh0","opopopo"));
		//获取字符串
		System.out.println("获取字符串:"+redisDaoImpl.get("fh0"));
		
		result += "获取字符串:"+redisDaoImpl.get("fh0")+",";
		
		Map<String, String> jmap = new HashMap<String, String>();
    	jmap.put("name", "fhadmin");
    	jmap.put("age", "22");
    	jmap.put("qq", "313596790");
		// 存储Map
		System.out.println(redisDaoImpl.addMap("fh", jmap));
		// 获取Map
		System.out.println("获取Map:"+redisDaoImpl.getMap("fh"));
		
		result += "获取Map:"+redisDaoImpl.getMap("fh")+",";
		
		List<String> list = new ArrayList<String>();
		list.add("ssss");
		list.add("bbbb");
		list.add("cccc");
		// 存储List
		redisDaoImpl.addList("fh1", list);
		// 获取List
		System.out.println("获取List:"+redisDaoImpl.getList("fh1"));
		
		result += "获取List:"+redisDaoImpl.getList("fh1")+",";
		
		Set<String> set = new HashSet<String>(16);
		set.add("wwww");
		set.add("eeee");
		set.add("rrrr");
		// 存储Set
		redisDaoImpl.addSet("fh2", set);
		// 获取Set
		System.out.println("获取Set:"+redisDaoImpl.getSet("fh2"));
		
		result += "获取Set:"+redisDaoImpl.getSet("fh2")+",";
		
		map.put("result", result);
		
		return AppUtil.returnObject(new PageData(), map);
	}

}
