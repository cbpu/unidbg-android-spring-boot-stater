package com.github.cbpu.unidbg.android.constant;

import com.github.unidbg.arm.backend.*;
import lombok.Getter;

/**
 * <p>CPU模拟器框架</p>
 *
 * @author pu_chaobo@163.com
 * @date 2022-01-13 17:05:08
 */
@Getter
public enum BackendFactoryEnum {
    /**
     * ARM动态重新编译器
     *
     * @see <a href="https://github.com/merryhime/dynarmic">ARM动态重新编译器</a>
     */
    DYNAMIC(new DynarmicFactory(true)),
    /**
     * Kvm
     */
    KVM(new KvmFactory(true)),
    /**
     * Hypervisor
     */
    HYPERVISOR(new HypervisorFactory(true)),
    /**
     * Unicorn2
     */
    UNICORN2(new Unicorn2Factory(true));
    /**
     * CPU模拟器框架
     */
    private final BackendFactory backendFactory;

    BackendFactoryEnum(BackendFactory backendFactory) {
        this.backendFactory = backendFactory;
    }
}
