package com.ruoyi.catering.service;

import java.util.List;
import com.ruoyi.catering.domain.Region;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.system.domain.SysRole;

/**
 * 区域信息Service接口
 * 
 * @author lsy
 * @date 2020-07-08
 */
public interface IRegionService 
{
    /**
     * 查询区域管理数据
     *
     * @param region 区域信息
     * @return 区域信息集合
     */
    public List<Region> selectRegionList(Region region);

    /**
     * 查询区域管理树
     *
     * @param region 区域信息
     * @return 所有区域信息
     */
    public List<Ztree> selectRegionTree(Region region);

    /**
     * 查询区域管理树（排除下级）
     *
     * @param region 区域信息
     * @return 所有区域信息
     */
    public List<Ztree> selectRegionTreeExcludeChild(Region region);

//    /**
//     * 根据角色ID查询菜单
//     *
//     * @param role 角色对象
//     * @return 菜单列表
//     */
//    public List<Ztree> roleRegionTreeData(SysRole role);

    /**
     * 查询区域人数
     *
     * @param parentId 父区域ID
     * @return 结果
     */
    public int selectRegionCount(Long parentId);

    /**
     * 查询区域是否存在用户
     *
     * @param regionId 区域ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkRegionExistRestaurant(Long regionId);

    /**
     * 删除区域管理信息
     *
     * @param regionId 区域ID
     * @return 结果
     */
    public int deleteRegionById(Long regionId);

    /**
     * 新增保存区域信息
     *
     * @param region 区域信息
     * @return 结果
     */
    public int insertRegion(Region region);

    /**
     * 修改保存区域信息
     *
     * @param region 区域信息
     * @return 结果
     */
    public int updateRegion(Region region);

    /**
     * 根据区域ID查询信息
     *
     * @param regionId 区域ID
     * @return 区域信息
     */
    public Region selectRegionById(Long regionId);

    /**
     * 根据ID查询所有子区域（正常状态）
     *
     * @param regionId 区域ID
     * @return 子区域数
     */
    public int selectNormalChildrenRegionById(Long regionId);

    /**
     * 校验区域名称是否唯一
     *
     * @param region 区域信息
     * @return 结果
     */
    public String checkRegionNameUnique(Region region);
}
