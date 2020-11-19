package com.ruoyi.catering.service;

import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.data.RestaurantQueryData;

import java.util.List;

/**
 * 餐饮单位信息Service接口
 *
 * @author lsy
 * @date 2020-07-08
 */
public interface IRestaurantService {
    /**
     * 查询餐饮单位信息
     *
     * @param restaurantId 餐饮单位信息ID
     * @return 餐饮单位信息
     */
    public Restaurant selectRestaurantById(Long restaurantId);

    /**
     * 查询餐饮单位信息列表
     *
     * @param restaurant 餐饮单位信息
     * @return 餐饮单位信息集合
     */
    public List<Restaurant> selectRestaurantList(Restaurant restaurant);

    /**
     * 新增餐饮单位信息
     *
     * @param restaurant 餐饮单位信息
     * @return 结果
     */
    public int insertRestaurant(Restaurant restaurant);

    /**
     * 修改餐饮单位信息
     *
     * @param restaurant 餐饮单位信息
     * @return 结果
     */
    public int updateRestaurant(Restaurant restaurant);

    /**
     * 批量删除餐饮单位信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRestaurantByIds(String ids);

    /**
     * 删除餐饮单位信息信息
     *
     * @param restaurantId 餐饮单位信息ID
     * @return 结果
     */
    public int deleteRestaurantById(Long restaurantId);

    /**
     * 导入餐饮单位信息
     *
     * @param restaurantList  餐饮单位信息列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importRestaurantList(List<Restaurant> restaurantList, Boolean isUpdateSupport, String operName);

    /**
     * 查询餐馆列表
     *
     * @param
     * @return 餐饮单位信息
     */
    public List<Restaurant> canRecycle(String sqlString, Long restaurantId, String name);

    /**
     * 根据区域id查询餐馆数量
     *
     * @param deptId 区域id
     * @return 餐饮单位信息
     */
    public int countByDeptId(Long deptId);

    /**
     * 查询贴牌数量
     *
     * @param
     * @return 贴牌数
     */
    public int brandedCount(String restaurantIds);

    /**
     * 查询餐馆列表
     *
     * @param
     * @return 餐饮单位信息
     */
    public List<Restaurant> selectList(RestaurantQueryData restaurantQueryData);
}
