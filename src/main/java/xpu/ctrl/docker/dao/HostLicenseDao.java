package xpu.ctrl.docker.dao;

import org.apache.ibatis.annotations.Mapper;
import xpu.ctrl.docker.entity.HostLicense;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (HostLicense)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-08 14:34:07
 */
@Mapper
public interface HostLicenseDao {

    /**
     * 通过ID查询单条数据
     *
     * @param licenseId 主键
     * @return 实例对象
     */
    HostLicense queryById(Integer licenseId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<HostLicense> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param hostLicense 实例对象
     * @return 对象列表
     */
    List<HostLicense> queryAll(HostLicense hostLicense);

    /**
     * 新增数据
     *
     * @param hostLicense 实例对象
     * @return 影响行数
     */
    int insert(HostLicense hostLicense);

    /**
     * 修改数据
     *
     * @param hostLicense 实例对象
     * @return 影响行数
     */
    int update(HostLicense hostLicense);

    /**
     * 通过主键删除数据
     *
     * @param licenseId 主键
     * @return 影响行数
     */
    int deleteById(Integer licenseId);

}