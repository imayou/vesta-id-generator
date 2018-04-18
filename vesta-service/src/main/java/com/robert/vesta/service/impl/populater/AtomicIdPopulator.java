package com.robert.vesta.service.impl.populater;

import com.robert.vesta.service.bean.Id;
import com.robert.vesta.service.impl.bean.IdMeta;
import com.robert.vesta.service.impl.timer.Timer;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 
 * CAS底层基础设施实现无锁，高并发下能高性能处理唯一ID的产生
 * 这里需要解决一个技术难题，就是并发修改2个变量--时间字段和序列号字段；
 * 这里通过使用原子变量引用来实现对时间和序列号2个字段的修改进行CAS保护，使其被高效，安全的修改。
 * 
 * 实现逻辑：
 * 1.取得并保存原来的变量，这个变量包含原来的时间和序列号字段
 * 2.基于原来的变量计算新的时间和序列号字段，计算逻辑和SyncIdPopulator，LockIdPopulator一致
 * 3.计算后，使用CAS操作更新原来的变量，在更新的过程中，需要传递保存的原来的变量
 * 4.如果保存的原来的变量被其他线程改变了，就需要在这里重新拿到最新的变量，并再次计算和尝试更新
 */
public class AtomicIdPopulator implements IdPopulator, ResetPopulator {
	//联合数据结构
    class Variant {
        private long sequence = 0;
        private long lastTimestamp = -1;
    }

    /**
     * 原子变量的引用,这个CAS引用操作可以保证实现联合的数据结构
     * Variant中的sequence和lastTimestamp中的任意一个被修改，
     * 都可以安全地得到更新
     */
    private AtomicReference<Variant> variant = new AtomicReference<Variant>(new Variant());

    public AtomicIdPopulator() {
        super();
    }

    public void populateId(Timer timer, Id id, IdMeta idMeta) {
        Variant varOld, varNew;
        long timestamp, sequence;

        while (true) {

            // Save the old variant
            varOld = variant.get();

            // populate the current variant
            timestamp = timer.genTime();
            timer.validateTimestamp(varOld.lastTimestamp, timestamp);

            sequence = varOld.sequence;

            if (timestamp == varOld.lastTimestamp) {
                sequence++;
                sequence &= idMeta.getSeqBitsMask();
                if (sequence == 0) {
                    timestamp = timer.tillNextTimeUnit(varOld.lastTimestamp);
                }
            } else {
                sequence = 0;
            }

            // Assign the current variant by the atomic tools
            varNew = new Variant();
            varNew.sequence = sequence;
            varNew.lastTimestamp = timestamp;

            if (variant.compareAndSet(varOld, varNew)) {
                id.setSeq(sequence);
                id.setTime(timestamp);

                break;
            }
        }
    }

    public void reset() {
        variant = new AtomicReference<Variant>(new Variant());
    }
}
