package com.ruoyi.catering.mapper;

import java.util.List;

import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.data.RestaurantQueryData;
import org.apache.ibatis.annotations.Param;

/**
 * 餐饮单位信息Mapper接口
 *
 * @author lsy
 * @date 2020-07-08
 */
public interface RestaurantMapper {
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
     * 删除餐饮单位信息
     *
     * @param restaurantId 餐饮单位信息ID
     * @return 结果
     */
    public int deleteRestaurantById(Long restaurantId);

    /**
     * 批量删除餐饮单位信息
     *
     * @param restaurantIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRestaurantByIds(String[] restaurantIds);

    /**
     * 查询餐馆是否有权回收
     *
     * @param restaurantId 餐饮单位信息ID
     * @return 餐饮单位信息
     */
    public List<Restaurant> canRecycle(@Param("sqlString") String sqlString, @Param("restaurantId") Long restaurantId, @Param("name") String name);

    public int countByDeptId(Long deptId);

    /**
     * 查询贴牌数量
     *
     * @param
     * @return 贴牌数
     */
    public int brandedCount(@Param("restaurantIds") String[] restaurantIds);

    /**
     * 查询餐馆列表
     *
     * @return 餐饮单位信息
     */
    public List<Restaurant> selectList(@Param("restaurantQueryData") RestaurantQueryData restaurantQueryData);

}
