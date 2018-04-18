package com.robert.vesta.service.impl.provider;

import com.robert.vesta.util.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 这种方法适合应用于线上的生产环境
 * 通过所有IP的机器列表为每个机器生成一个唯一ID号，主要适合服务节点比较少的情况
 * 事实上，生成ID是轻量级的服务，不会需要太大的服务池，因此这也是一种最常用，最简单的方式
 * 
 * 实现：
 * 发布前配置所有服务节点IP的映射，每个服务节点必须具有相同的映射，
 * 运行时每个服务节点根据本机IP取得在IP映射中的位置，作为自己的机器号
 */
public class IpConfigurableMachineIdProvider implements MachineIdProvider {
    private static final Logger log = LoggerFactory
            .getLogger(IpConfigurableMachineIdProvider.class);

    private long machineId;

    private Map<String, Long> ipsMap = new HashMap<String, Long>();

    public IpConfigurableMachineIdProvider() {
        log.debug("IpConfigurableMachineIdProvider constructed.");
    }

    public IpConfigurableMachineIdProvider(String ips) {
        setIps(ips);
        init();
    }

    public void init() {
        String ip = IpUtils.getHostIp();

        if (StringUtils.isEmpty(ip)) {
            String msg = "Fail to get host IP address. Stop to initialize the IpConfigurableMachineIdProvider provider.";

            log.error(msg);
            throw new IllegalStateException(msg);
        }

        if (!ipsMap.containsKey(ip)) {
            String msg = String
                    .format("Fail to configure ID for host IP address %s. Stop to initialize the IpConfigurableMachineIdProvider provider.",
                            ip);

            log.error(msg);
            throw new IllegalStateException(msg);
        }

        machineId = ipsMap.get(ip);

        log.info("IpConfigurableMachineIdProvider.init ip {} id {}", ip,
                machineId);
    }

    public void setIps(String ips) {
        log.debug("IpConfigurableMachineIdProvider ips {}", ips);
        if (!StringUtils.isEmpty(ips)) {
            String[] ipArray = ips.split(",");

            for (int i = 0; i < ipArray.length; i++) {
                ipsMap.put(ipArray[i], (long) i);
            }
        }
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }
}
