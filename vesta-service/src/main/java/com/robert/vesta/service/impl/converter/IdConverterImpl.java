package com.robert.vesta.service.impl.converter;

import com.robert.vesta.service.bean.Id;
import com.robert.vesta.service.impl.bean.IdMeta;


public class IdConverterImpl implements IdConverter {

    public IdConverterImpl() {
    }

    //将元数据对象转换成长整型的ID
    public long convert(Id id, IdMeta idMeta) {
        return doConvert(id, idMeta);
    }

    //根据ID元数据的信息对象获取每个属性所在ID的位数，左移将各个属性拼接到一个长整型数字里
    protected long doConvert(Id id, IdMeta idMeta) {
        long ret = 0;
        ret |= id.getMachine();
        ret |= id.getSeq() << idMeta.getSeqBitsStartPos();
        ret |= id.getTime() << idMeta.getTimeBitsStartPos();
        ret |= id.getGenMethod() << idMeta.getGenMethodBitsStartPos();
        ret |= id.getType() << idMeta.getTypeBitsStartPos();
        ret |= id.getVersion() << idMeta.getVersionBitsStartPos();
        return ret;
    }

	/**
	 * 将长整型的ID转换成元数据对象
	 * 将长整型的ID解释成人可读的格式，可从中得到时间，序列号，版本，类型等
	 */
    public Id convert(long id, IdMeta idMeta) {
        return doConvert(id, idMeta);
    }

	/**
	 * 无符号右移操作
	 * 因为产生的ID包含的每一位二进制位都代表特殊的含义，所以没有数学上的正负意义
	 * 最左边的一位二进制也不是用来表示符号的。
	 * @param id
	 * @param idMeta
	 * @return
	 */
    protected Id doConvert(long id, IdMeta idMeta) {
        Id ret = new Id();
        ret.setMachine(id & idMeta.getMachineBitsMask());
        ret.setSeq((id >>> idMeta.getSeqBitsStartPos()) & idMeta.getSeqBitsMask());
        ret.setTime((id >>> idMeta.getTimeBitsStartPos()) & idMeta.getTimeBitsMask());
        ret.setGenMethod((id >>> idMeta.getGenMethodBitsStartPos()) & idMeta.getGenMethodBitsMask());
        ret.setType((id >>> idMeta.getTypeBitsStartPos()) & idMeta.getTypeBitsMask());
        ret.setVersion((id >>> idMeta.getVersionBitsStartPos()) & idMeta.getVersionBitsMask());
        return ret;
    }
}
