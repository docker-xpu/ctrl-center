package xpu.ctrl.docker.service.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import xpu.ctrl.docker.entity.HostEntity;
import xpu.ctrl.docker.dao.HostEntityDao;
import xpu.ctrl.docker.enums.RunStatusEnum;
import xpu.ctrl.docker.repository.HostEntityRepository;
import xpu.ctrl.docker.service.HostEntityService;
import org.springframework.stereotype.Service;
import xpu.ctrl.docker.util.EnumUtil;
import xpu.ctrl.docker.vo.HostEntityVO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (HostEntity)表服务实现类
 *
 * @author makejava
 * @since 2020-03-12 12:29:32
 */
@Service
public class HostEntityServiceImpl implements HostEntityService {
    @Resource
    private HostEntityDao hostEntityDao;

    @Autowired
    private HostEntityRepository hostEntityRepository;

    /**
     * 通过ID查询单条数据
     *
     * @param hostIp 主键
     * @return 实例对象
     */
    @Override
    public HostEntity queryById(String hostIp) {
        return this.hostEntityDao.queryById(hostIp);
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
        return this.queryById(hostEntity.getHostIp());
    }

    /**
     * 通过主键删除数据
     *
     * @param hostIp 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String hostIp) {
        return this.hostEntityDao.deleteById(hostIp) > 0;
    }

    @Override
    public List<HostEntityVO> getAllHost() {
        return toVO(hostEntityRepository.findAll());
    }

    private List<HostEntityVO> toVO(List<HostEntity> hostEntityList){
        ArrayList<HostEntityVO>  hostEntityVOArrayList= Lists.newArrayListWithCapacity(hostEntityList.size());
        for(HostEntity hostEntity: hostEntityList){
            hostEntityVOArrayList.add(toVO(hostEntity));
        }
        return hostEntityVOArrayList;
    }
    private HostEntityVO toVO(HostEntity hostEntity){
        HostEntityVO hostEntityVO = new HostEntityVO();
        BeanUtils.copyProperties(hostEntity, hostEntityVO);
        hostEntityVO.setHostStatusStr(EnumUtil.getByCode(hostEntity.getHostStatus(), RunStatusEnum.class).getMessage());
        return hostEntityVO;
    }


}