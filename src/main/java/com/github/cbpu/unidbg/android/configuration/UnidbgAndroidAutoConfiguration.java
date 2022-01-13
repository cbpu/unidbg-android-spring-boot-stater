package com.github.cbpu.unidbg.android.configuration;

import com.github.cbpu.unidbg.android.constant.ProcessorArchitectureEnum;
import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.linux.android.AndroidEmulatorBuilder;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.VM;
import com.github.unidbg.memory.Memory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * <p>description</p>
 *
 * @author pu_chaobo@163.com
 * @date 2021-12-24 18:12:29
 */
@ConditionalOnClass(value = {AndroidEmulator.class, AndroidEmulatorBuilder.class, Memory.class, VM.class})
@EnableConfigurationProperties({AndroidEmulatorProperties.class})
@Configuration
public class UnidbgAndroidAutoConfiguration {
    private final AndroidEmulatorProperties androidEmulatorProperties;

    public UnidbgAndroidAutoConfiguration(AndroidEmulatorProperties androidEmulatorProperties) {
        this.androidEmulatorProperties = androidEmulatorProperties;
    }

    /**
     * <p>创建安卓模拟器</p>
     *
     * @author pu_chaobo@163.com
     * @date 2022-01-13 17:44:46
     */
    @ConditionalOnMissingBean(AndroidEmulator.class)
    @Bean
    AndroidEmulator androidEmulator() {
        final AndroidEmulator androidEmulator;
        if (ProcessorArchitectureEnum.ARM64.equals(this.androidEmulatorProperties.getProcessorArchitecture())) {
            androidEmulator = AndroidEmulatorBuilder.for64Bit()
                    .addBackendFactory(this.androidEmulatorProperties.getBackendFactory().getBackendFactory())
                    .setProcessName(this.androidEmulatorProperties.getProcessName())
                    .setRootDir(new File(this.androidEmulatorProperties.getRootDir()))
                    .build();
        } else {
            androidEmulator = AndroidEmulatorBuilder.for32Bit()
                    .addBackendFactory(this.androidEmulatorProperties.getBackendFactory().getBackendFactory())
                    .setProcessName(this.androidEmulatorProperties.getProcessName())
                    .setRootDir(new File(this.androidEmulatorProperties.getRootDir()))
                    .build();
        }

        return androidEmulator;
    }

    /**
     * <p>创建安卓模拟器内存</p>
     *
     * @author pu_chaobo@163.com
     * @date 2022-01-13 17:51:13
     */
    @ConditionalOnMissingBean(Memory.class)
    @Bean
    Memory memory() {
        final Memory memory = androidEmulator().getMemory();
        memory.setLibraryResolver(new AndroidResolver(23));
        return memory;
    }

    /**
     * <p>创建Dalvik安装虚拟机</p>
     *
     * @author pu_chaobo@163.com
     * @date 2022-01-13 17:52:25
     */
    @ConditionalOnMissingBean(VM.class)
    @Bean
    VM vm() {
        VM vm = androidEmulator().createDalvikVM();
        vm.setVerbose(true);
        return vm;
    }
}
