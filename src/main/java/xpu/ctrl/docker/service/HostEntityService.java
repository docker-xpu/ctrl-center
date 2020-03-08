package xpu.ctrl.docker.service;

import xpu.ctrl.docker.entity.HostEntity;

import java.util.List;

/**
 * (HostEntity)表服务接口
 *
 * @author makejava
 * @since 2020-03-08 13:50:26
 */
public interface HostEntityService {

    /**
     * 通过ID查询单条数据
     *
     * @param hostId 主键
     * @return 实例对象
     */
    HostEntity queryById(Integer hostId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<HostEntity> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param hostEntity 实例对象
     * @return 实例对象
     */
    HostEntity insert(HostEntity hostEntity);

    /**
     * 修改数据
     *
     * @param hostEntity 实例对象
     * @return 实例对象
     */
    HostEntity update(HostEntity hostEntity);

    /**
     * 通过主键删除数据
     *
     * @param hostId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer hostId);

}