package com.ruoyi.catering.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysRole;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.RegionMapper;
import com.ruoyi.catering.domain.Region;
import com.ruoyi.catering.service.IRegionService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 区域信息Service业务层处理
 *
 * @author lsy
 * @date 2020-07-08
 */
@Service
public class RegionServiceImpl implements IRegionService {
    @Autowired
    private RegionMapper regionMapper;

    /**
     * 查询区域管理数据
     *
     * @param region 区域信息
     * @return 区域信息集合
     */
    @Override
//    @DataScope(regionAlias = "d")
    public List<Region> selectRegionList(Region region) {
        return regionMapper.selectRegionList(region);
    }

    /**
     * 查询区域管理树
     *
     * @param region 区域信息
     * @return 所有区域信息
     */
    @Override
//    @DataScope(regionAlias = "d")
    public List<Ztree> selectRegionTree(Region region) {
        List<Region> regionList = regionMapper.selectRegionList(region);
        List<Ztree> ztrees = initZtree(regionList);
        return ztrees;
    }

    /**
     * 查询区域管理树（排除下级）
     *
     * @param regionId 区域ID
     * @return 所有区域信息
     */
    @Override
//    @DataScope(regionAlias = "d")
    public List<Ztree> selectRegionTreeExcludeChild(Region region) {
        Long regionId = region.getRegionId();
        List<Region> regionList = regionMapper.selectRegionList(region);
        Iterator<Region> it = regionList.iterator();
        while (it.hasNext()) {
            Region d = (Region) it.next();
            if (d.getRegionId().intValue() == regionId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), regionId + "")) {
                it.remove();
            }
        }
        List<Ztree> ztrees = initZtree(regionList);
        return ztrees;
    }

//    /**
//     * 根据角色ID查询区域（数据权限）
//     *
//     * @param role 角色对象
//     * @return 区域列表（数据权限）
//     */
//    @Override
//    public List<Ztree> roleRegionTreeData(SysRole role) {
//        Long roleId = role.getRoleId();
//        List<Ztree> ztrees = new ArrayList<Ztree>();
//        List<Region> regionList = selectRegionList(new Region());
//        if (StringUtils.isNotNull(roleId)) {
//            List<String> roleRegionList = regionMapper.selectRoleRegionTree(roleId);
//            ztrees = initZtree(regionList, roleRegionList);
//        } else {
//            ztrees = initZtree(regionList);
//        }
//        return ztrees;
//    }

    /**
     * 对象转区域树
     *
     * @param regionList 区域列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Region> regionList) {
        return initZtree(regionList, null);
    }

    /**
     * 对象转区域树
     *
     * @param regionList     区域列表
     * @param roleRegionList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Region> regionList, List<String> roleRegionList) {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleRegionList);
        for (Region region : regionList) {
            if (UserConstants.DEPT_NORMAL.equals(region.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(region.getRegionId());
                ztree.setpId(region.getParentId());
                ztree.setName(region.getRegionName());
                ztree.setTitle(region.getRegionName());
                if (isCheck) {
                    ztree.setChecked(roleRegionList.contains(region.getRegionId() + region.getRegionName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    /**
     * 查询区域人数
     *
     * @param parentId 区域ID
     * @return 结果
     */
    @Override
    public int selectRegionCount(Long parentId) {
        Region region = new Region();
        region.setParentId(parentId);
        return regionMapper.selectRegionCount(region);
    }

    /**
     * 查询区域是否存在店铺
     *
     * @param regionId 区域ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkRegionExistRestaurant(Long regionId) {
        int result = regionMapper.checkRegionExistRestaurant(regionId);
        return result > 0 ? true : false;
    }

    /**
     * 删除区域管理信息
     *
     * @param regionId 区域ID
     * @return 结果
     */
    @Override
    public int deleteRegionById(Long regionId) {
        return regionMapper.deleteRegionById(regionId);
    }

    /**
     * 新增保存区域信息
     *
     * @param region 区域信息
     * @return 结果
     */
    @Override
    public int insertRegion(Region region) {
        Region info = regionMapper.selectRegionById(region.getParentId());
        // 如果父节点不为"正常"状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new BusinessException("区域停用，不允许新增");
        }
        region.setAncestors(info.getAncestors() + "," + region.getParentId());
        return regionMapper.insertRegion(region);
    }

    /**
     * 修改保存区域信息
     *
     * @param region 区域信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRegion(Region region) {
        Region newParentRegion = regionMapper.selectRegionById(region.getParentId());
        Region oldRegion = selectRegionById(region.getRegionId());
        if (StringUtils.isNotNull(newParentRegion) && StringUtils.isNotNull(oldRegion)) {
            String newAncestors = newParentRegion.getAncestors() + "," + newParentRegion.getRegionId();
            String oldAncestors = oldRegion.getAncestors();
            region.setAncestors(newAncestors);
            updateRegionChildren(region.getRegionId(), newAncestors, oldAncestors);
        }
        int result = regionMapper.updateRegion(region);
        if (UserConstants.DEPT_NORMAL.equals(region.getStatus())) {
            // 如果该区域是启用状态，则启用该区域的所有上级区域
            updateParentRegionStatus(region);
        }
        return result;
    }

    /**
     * 修改该区域的父级区域状态
     *
     * @param region 当前区域
     */
    private void updateParentRegionStatus(Region region) {
        String updateBy = region.getUpdateBy();
        region = regionMapper.selectRegionById(region.getRegionId());
        region.setUpdateBy(updateBy);
        regionMapper.updateRegionStatus(region);
    }

    /**
     * 修改子元素关系
     *
     * @param regionId     被修改的区域ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateRegionChildren(Long regionId, String newAncestors, String oldAncestors) {
        List<Region> children = regionMapper.selectChildrenRegionById(regionId);
        for (Region child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            regionMapper.updateRegionChildren(children);
        }
    }

    /**
     * 根据区域ID查询信息
     *
     * @param regionId 区域ID
     * @return 区域信息
     */
    @Override
    public Region selectRegionById(Long regionId) {
        return regionMapper.selectRegionById(regionId);
    }

    /**
     * 根据ID查询所有子区域（正常状态）
     *
     * @param regionId 区域ID
     * @return 子区域数
     */
    @Override
    public int selectNormalChildrenRegionById(Long regionId) {
        return regionMapper.selectNormalChildrenRegionById(regionId);
    }

    /**
     * 校验区域名称是否唯一
     *
     * @param region 区域信息
     * @return 结果
     */
    @Override
    public String checkRegionNameUnique(Region region) {
        Long regionId = StringUtils.isNull(region.getRegionId()) ? -1L : region.getRegionId();
        Region info = regionMapper.checkRegionNameUnique(region.getRegionName(), region.getParentId());
        if (StringUtils.isNotNull(info) && info.getRegionId().longValue() != regionId.longValue()) {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }
}
