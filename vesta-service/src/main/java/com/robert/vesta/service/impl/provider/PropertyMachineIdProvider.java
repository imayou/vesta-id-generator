package com.robert.vesta.service.impl.provider;

/**
 * 基于属性配置生成，用于测试环境
 * 使用这种方式，需要在部署的每台机器上配置不同的机器号
 * 这在生成环境中是不现实的
 */
public class PropertyMachineIdProvider implements MachineIdProvider {
    private long machineId;

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }
}
