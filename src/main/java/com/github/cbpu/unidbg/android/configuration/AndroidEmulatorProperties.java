package com.github.cbpu.unidbg.android.configuration;

import com.github.cbpu.unidbg.android.constant.BackendFactoryEnum;
import com.github.cbpu.unidbg.android.constant.ProcessorArchitectureEnum;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * <p>Android模拟器配置</p>
 *
 * @author pu_chaobo@163.com
 * @date 2021-12-24 18:57:54
 */
@Validated
@Data
@ConfigurationProperties(prefix = "unidbg.android.emulator")
public final class AndroidEmulatorProperties implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 处理器架构，32bit/64bit
     */
    @Value("${unidbg.android.emulator.processor-architecture:ARM32}")
    private ProcessorArchitectureEnum processorArchitecture;
    /**
     * CPU模拟器框架
     */
    @Value("${unidbg.android.emulator.backend-factory:DYNAMIC}")
    private BackendFactoryEnum backendFactory;
    /**
     * 进程名
     */
    @Value("${unidbg.android.emulator.process-name:com.github.cbpu.unidbg.android}")
    private String processName;
    /**
     * 根目录
     */
    @Value("${unidbg.android.emulator.root-dir:")
    private String rootDir;
}
