package com.ruoyi.catering.mapper;

import java.util.List;
import com.ruoyi.catering.domain.Region;
import org.apache.ibatis.annotations.Param;

/**
 * 区域信息Mapper接口
 * 
 * @author lsy
 * @date 2020-07-08
 */
public interface RegionMapper 
{
    /**
     * 查询区域人数
     *
     * @param region 区域信息
     * @return 结果
     */
    public int selectRegionCount(Region region);

    /**
     * 查询区域是否存在用户
     *
     * @param regionId 区域ID
     * @return 结果
     */
    public int checkRegionExistRestaurant(Long regionId);

    /**
     * 查询区域管理数据
     *
     * @param region 区域信息
     * @return 区域信息集合
     */
    public List<Region> selectRegionList(Region region);

    /**
     * 删除区域管理信息
     *
     * @param regionId 区域ID
     * @return 结果
     */
    public int deleteRegionById(Long regionId);

    /**
     * 新增区域信息
     *
     * @param region 区域信息
     * @return 结果
     */
    public int insertRegion(Region region);

    /**
     * 修改区域信息
     *
     * @param region 区域信息
     * @return 结果
     */
    public int updateRegion(Region region);

    /**
     * 修改子元素关系
     *
     * @param regions 子元素
     * @return 结果
     */
    public int updateRegionChildren(@Param("regions") List<Region> regions);

    /**
     * 根据区域ID查询信息
     *
     * @param regionId 区域ID
     * @return 区域信息
     */
    public Region selectRegionById(Long regionId);

    /**
     * 校验区域名称是否唯一
     *
     * @param regionName 区域名称
     * @param parentId 父区域ID
     * @return 结果
     */
    public Region checkRegionNameUnique(@Param("regionName") String regionName, @Param("parentId") Long parentId);

//    /**
//     * 根据角色ID查询区域
//     *
//     * @param roleId 角色ID
//     * @return 区域列表
//     */
//    public List<String> selectRoleRegionTree(Long roleId);

    /**
     * 修改所在区域的父级区域状态
     *
     * @param region 区域
     */
    public void updateRegionStatus(Region region);

    /**
     * 根据ID查询所有子区域
     *
     * @param regionId 区域ID
     * @return 区域列表
     */
    public List<Region> selectChildrenRegionById(Long regionId);

    /**
     * 根据ID查询所有子区域（正常状态）
     *
     * @param regionId 区域ID
     * @return 子区域数
     */
    public int selectNormalChildrenRegionById(Long regionId);
}
