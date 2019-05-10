package com.fh.dao.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;


/** 
 * 说明： redis
 * @author FH Q313596790
 * @date 2016-05-2
 * @version 1.0
 */
public interface RedisDao {
	
	/**新增(存储字符串)
	 * @param key
	 * @param value
	 * @return
	 */
	boolean addString(String key, String value);
	
	/**拼接字符串
	 * @param key
	 * @param value
	 * @return
	 */
	boolean appendString(String key, String value);
	
	/**新增(存储Map)
	 * @param key
	 * @param map
	 * @return
	 */
	String addMap(String key, Map<String, String> map);
	
	/**获取map
	 * @param key
	 * @return
	 */
	Map<String,String> getMap(String key);
	
	/**新增(存储List)
	 * @param key
	 * @param list
	 * @return
	 */
	void addList(String key, List<String> list);
	
	/**获取List
	 * @param key
	 * @return
	 */
	List<String> getList(String key);
	
	/**新增(存储set)
	 * @param key
	 * @param set
	 */
	void addSet(String key, Set<String> set);
	
	/**获取Set
	 * @param key
	 * @return
	 */
	Set<String> getSet(String key);
	
	/**删除
	 * @param key
	 */
	boolean delete(String key);
	
	/**删除多个 
	 * @param keys
	 */
	void delete(List<String> keys);
	
	/**修改
	 * @param pd
	 * @return
	 */
	boolean eidt(String key, String value);
	
	/**通过ket获取数据
	 * @param keyId
	 * @return
	 */
	String get(String keyId);

}
