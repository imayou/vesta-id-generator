package com.robert.vesta.service.impl.converter;

import com.robert.vesta.service.bean.Id;
import com.robert.vesta.service.impl.bean.IdMeta;

/**
 * 将ID元数据对象转换成长整型的ID，或将长整型的ID转换成ID元数据对象，
 * 并且定义了清晰的转换接口，用于将来扩展能够实现其他类型的转换
 */
public interface IdConverter {
    long convert(Id id, IdMeta idMeta);
    Id convert(long id, IdMeta idMeta);
}
