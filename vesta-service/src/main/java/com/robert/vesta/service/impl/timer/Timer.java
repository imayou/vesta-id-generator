package com.robert.vesta.service.impl.timer;

import com.robert.vesta.service.impl.bean.IdMeta;
import com.robert.vesta.service.impl.bean.IdType;

import java.util.Date;

public interface Timer {
	long EPOCH = 1420041600000L;
	void init(IdMeta idMeta, IdType idType);
	Date transTime(long time);
	/**
	 * 校验机器时间
	 * 如果时间被回调了，服务就会产生重复的ID
	 * @param lastTimestamp
	 * @param timestamp
	 */
	void validateTimestamp(long lastTimestamp, long timestamp);
	/**
	 * 使用自旋锁实现，减少线程切换而导致的性能损耗
	 * @param lastTimestamp
	 * @return
	 */
	long tillNextTimeUnit(long lastTimestamp);
	/**
	 * 在产生时间字段时，需要通过唯一ID类型来确定产生的时间单位，
	 * 并对时间进行编码，通过TimeUtils.EPOCH来对时间进行压缩
	 * @return
	 */
	long genTime();
}
