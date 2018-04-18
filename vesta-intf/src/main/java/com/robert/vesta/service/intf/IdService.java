package com.robert.vesta.service.intf;

import com.robert.vesta.service.bean.Id;

import java.util.Date;

public interface IdService {

	/**
	 * 发号器主要API，用来产生唯一ID
	 * @return long
	 */
	public long genId();

	/**
	 * 产生唯一ID的反向操作，可以对一个ID内包含的信息进行解读，用人可读的形式来表达
	 * @param id
	 * @return Id
	 */
	public Id expId(long id);

	/**
	 * 伪造某一时间的ID
	 * @param time
	 * @param seq
	 * @return long
	 */
	public long makeId(long time, long seq);

	/**
	 * 伪造某一时间的ID
	 * @param time
	 * @param seq
	 * @param machine
	 * @return
	 */
	public long makeId(long time, long seq, long machine);

	/**
	 * 伪造某一时间的ID
	 * @param genMethod
	 * @param time
	 * @param seq
	 * @param machine
	 * @return
	 */
	public long makeId(long genMethod, long time, long seq, long machine);

	/**
	 * 伪造某一时间的ID
	 * @param type
	 * @param genMethod
	 * @param time
	 * @param seq
	 * @param machine
	 * @return
	 */
	public long makeId(long type, long genMethod, long time, long seq, long machine);
	
	/**
	 * 伪造某一时间的ID
	 * @param version
	 * @param type
	 * @param genMethod
	 * @param time
	 * @param seq
	 * @param machine
	 * @return
	 */
	public long makeId(long version, long type, long genMethod, long time, long seq, long machine);

	/**
	 * 将整型时间翻译成格式化时间
	 * @param time
	 * @return
	 */
	public Date transTime(long time);
}
