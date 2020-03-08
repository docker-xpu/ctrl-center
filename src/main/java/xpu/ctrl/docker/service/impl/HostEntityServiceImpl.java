package xpu.ctrl.docker.service.impl;

import xpu.ctrl.docker.entity.HostEntity;
import xpu.ctrl.docker.dao.HostEntityDao;
import xpu.ctrl.docker.service.HostEntityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (HostEntity)表服务实现类
 *
 * @author makejava
 * @since 2020-03-08 14:24:19
 */
@Service("hostEntityService")
public class HostEntityServiceImpl implements HostEntityService {
    @Resource
    private HostEntityDao hostEntityDao;

    /**
     * 通过ID查询单条数据
     *
     * @param hostId 主键
     * @return 实例对象
     */
    @Override
    public HostEntity queryById(Integer hostId) {
        return this.hostEntityDao.queryById(hostId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<HostEntity> queryAllByLimit(int offset, int limit) {
        return this.hostEntityDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param hostEntity 实例对象
     * @return 实例对象
     */
    @Override
    public HostEntity insert(HostEntity hostEntity) {
        this.hostEntityDao.insert(hostEntity);
        return hostEntity;
    }

    /**
     * 修改数据
     *
     * @param hostEntity 实例对象
     * @return 实例对象
     */
    @Override
    public HostEntity update(HostEntity hostEntity) {
        this.hostEntityDao.update(hostEntity);
        return this.queryById(hostEntity.getHostId());
    }

    /**
     * 通过主键删除数据
     *
     * @param hostId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer hostId) {
        return this.hostEntityDao.deleteById(hostId) > 0;
    }
}