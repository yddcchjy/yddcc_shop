package com.ego.redis.dao;

public interface JedisDao {
	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	Boolean exists(String key);
	
	/**
	 * 删除
	 * @param key
	 * @return
	 */
	Long del(String key);
	
	/**
	 * 设置值
	 * @param key
	 * @param value
	 * @return
	 */
//	String set(String key,String value);
	
	/**
	 * 取值
	 * @param key
	 * @return
	 */
	String get(String key);
	/**
	 * 设置key的过期时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	Long expire(String key,int seconds);
	
	/**
	 * 获取list的大小
	 * @param key
	 * @return
	 */
	Long getkeylistsize(String key);
	
	/**
	 * pushlist
	 * @param key
	 * @param value
	 * @return
	 */
	Long pushlist(String key,String value);
	
	/**
	 * 返回值为boolean的set方法
	 *  如果count为零，则删除所有元素。 如果count是负数元素从尾部到头部移除，而不是从头到尾这是正常行为。
	 * @param key
	 * @param value
	 * @return
	 */
	boolean set(String key,String value);
	
	/**
	 * 删除list中的指定key
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	long removelist(String key,long count,String value);

	long incr(String string, int i);
}
