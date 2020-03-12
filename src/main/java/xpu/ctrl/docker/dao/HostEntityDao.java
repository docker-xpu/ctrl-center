package xpu.ctrl.docker.dao;

import org.apache.ibatis.annotations.Mapper;
import xpu.ctrl.docker.entity.HostEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (HostEntity)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-12 12:29:31
 */
@Mapper
public interface HostEntityDao {

    /**
     * 通过ID查询单条数据
     *
     * @param hostIp 主键
     * @return 实例对象
     */
    HostEntity queryById(String hostIp);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<HostEntity> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param hostEntity 实例对象
     * @return 对象列表
     */
    List<HostEntity> queryAll(HostEntity hostEntity);

    /**
     * 新增数据
     *
     * @param hostEntity 实例对象
     * @return 影响行数
     */
    int insert(HostEntity hostEntity);

    /**
     * 修改数据
     *
     * @param hostEntity 实例对象
     * @return 影响行数
     */
    int update(HostEntity hostEntity);

    /**
     * 通过主键删除数据
     *
     * @param hostIp 主键
     * @return 影响行数
     */
    int deleteById(String hostIp);

}