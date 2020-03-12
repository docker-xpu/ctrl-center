package xpu.ctrl.docker.service.impl;

import xpu.ctrl.docker.entity.HostLicense;
import xpu.ctrl.docker.dao.HostLicenseDao;
import xpu.ctrl.docker.service.HostLicenseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (HostLicense)表服务实现类
 *
 * @author makejava
 * @since 2020-03-12 12:26:41
 */
@Service
public class HostLicenseServiceImpl implements HostLicenseService {
    @Resource
    private HostLicenseDao hostLicenseDao;

    /**
     * 通过ID查询单条数据
     *
     * @param licenseId 主键
     * @return 实例对象
     */
    @Override
    public HostLicense queryById(Integer licenseId) {
        return this.hostLicenseDao.queryById(licenseId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<HostLicense> queryAllByLimit(int offset, int limit) {
        return this.hostLicenseDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param hostLicense 实例对象
     * @return 实例对象
     */
    @Override
    public HostLicense insert(HostLicense hostLicense) {
        this.hostLicenseDao.insert(hostLicense);
        return hostLicense;
    }

    /**
     * 修改数据
     *
     * @param hostLicense 实例对象
     * @return 实例对象
     */
    @Override
    public HostLicense update(HostLicense hostLicense) {
        this.hostLicenseDao.update(hostLicense);
        return this.queryById(hostLicense.getLicenseId());
    }

    /**
     * 通过主键删除数据
     *
     * @param licenseId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer licenseId) {
        return this.hostLicenseDao.deleteById(licenseId) > 0;
    }
}