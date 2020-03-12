package xpu.ctrl.docker.service;

import xpu.ctrl.docker.entity.HostLicense;
import java.util.List;

/**
 * (HostLicense)表服务接口
 *
 * @author makejava
 * @since 2020-03-12 12:26:40
 */
public interface HostLicenseService {

    /**
     * 通过ID查询单条数据
     *
     * @param licenseId 主键
     * @return 实例对象
     */
    HostLicense queryById(Integer licenseId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<HostLicense> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param hostLicense 实例对象
     * @return 实例对象
     */
    HostLicense insert(HostLicense hostLicense);

    /**
     * 修改数据
     *
     * @param hostLicense 实例对象
     * @return 实例对象
     */
    HostLicense update(HostLicense hostLicense);

    /**
     * 通过主键删除数据
     *
     * @param licenseId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer licenseId);

}