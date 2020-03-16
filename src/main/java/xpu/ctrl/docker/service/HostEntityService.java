package xpu.ctrl.docker.service;

import xpu.ctrl.docker.entity.HostEntity;
import xpu.ctrl.docker.vo.HostEntityVO;
import xpu.ctrl.docker.vo.HostRunningVO;
import xpu.ctrl.docker.vo.HostRunningVOArray;

import java.util.List;
import java.util.Map;

/**
 * (HostEntity)表服务接口
 *
 * @author makejava
 * @since 2020-03-12 12:29:32
 */
public interface HostEntityService {

    /**
     * 通过ID查询单条数据
     *
     * @param hostIp 主键
     * @return 实例对象
     */
    HostEntity queryById(String hostIp);

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
     * @param hostIp 主键
     * @return 是否成功
     */
    boolean deleteById(String hostIp);

    List<HostEntityVO> getAllHost();

    List<HostRunningVO> getRunningHost();
}