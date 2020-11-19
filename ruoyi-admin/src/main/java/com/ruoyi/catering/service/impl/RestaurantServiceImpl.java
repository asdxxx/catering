package com.ruoyi.catering.service.impl;

import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.mapper.RestaurantMapper;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.data.RestaurantQueryData;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 餐饮单位信息Service业务层处理
 *
 * @author lsy
 * @date 2020-07-08
 */
@Service
public class RestaurantServiceImpl implements IRestaurantService {
    @Autowired
    private RestaurantMapper restaurantMapper;

    /**
     * 查询餐饮单位信息
     *
     * @param restaurantId 餐饮单位信息ID
     * @return 餐饮单位信息
     */
    @Override
    public Restaurant selectRestaurantById(Long restaurantId) {
        return restaurantMapper.selectRestaurantById(restaurantId);
    }

    /**
     * 查询餐饮单位信息列表
     *
     * @param restaurant 餐饮单位信息
     * @return 餐饮单位信息
     */
    @Override
    public List<Restaurant> selectRestaurantList(Restaurant restaurant) {
        return restaurantMapper.selectRestaurantList(restaurant);
    }

    /**
     * 新增餐饮单位信息
     *
     * @param restaurant 餐饮单位信息
     * @return 结果
     */
    @Override
    public int insertRestaurant(Restaurant restaurant) {
        restaurant.setCreateTime(DateUtils.getNowDate());
        return restaurantMapper.insertRestaurant(restaurant);
    }

    /**
     * 修改餐饮单位信息
     *
     * @param restaurant 餐饮单位信息
     * @return 结果
     */
    @Override
    public int updateRestaurant(Restaurant restaurant) {
        restaurant.setUpdateTime(DateUtils.getNowDate());
        return restaurantMapper.updateRestaurant(restaurant);
    }

    /**
     * 删除餐饮单位信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRestaurantByIds(String ids) {
        return restaurantMapper.deleteRestaurantByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除餐饮单位信息信息
     *
     * @param restaurantId 餐饮单位信息ID
     * @return 结果
     */
    @Override
    public int deleteRestaurantById(Long restaurantId) {
        return restaurantMapper.deleteRestaurantById(restaurantId);
    }

    /**
     * 查询餐馆是否有权回收
     *
     * @param restaurantId 餐饮单位信息ID
     * @return 餐饮单位信息
     */
    @Override
    public List<Restaurant> canRecycle(String sqlString, Long restaurantId, String name) {
        return restaurantMapper.canRecycle(sqlString, restaurantId, name);
    }

    /**
     * 导入餐饮单位信息
     *
     * @param restaurantList  餐饮单位信息列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importRestaurantList(List<Restaurant> restaurantList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(restaurantList) || restaurantList.size() == 0) {
            throw new BusinessException("导入餐饮单位信息数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        int count = 1;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Restaurant restaurant : restaurantList) {
            count++;
            if (StringUtils.isEmpty(restaurant.getName())) {
                failureNum++;
                failureMsg.append("<br/>" + failureNum + "、第 " + count + " 行店名为空");
                continue;
            }
            try {
                Restaurant query = new Restaurant();
//                query.setRestaurantNo(restaurant.getRestaurantNo());
                query.setName(restaurant.getName());
                query.setLegalPerson(restaurant.getLegalPerson());
                query.setTel(restaurant.getTel());
//                query.setDeptId(restaurant.getDeptId());
                List<Restaurant> restaurants = restaurantMapper.selectRestaurantList(query);
                if (restaurants == null || restaurants.size() == 0) {
                    restaurant.setCreateBy(operName);
                    this.insertRestaurant(restaurant);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、店铺 " + restaurant.getName() + " 导入成功");
                } else if (isUpdateSupport) {
                    restaurant.setRestaurantId(restaurants.get(0).getRestaurantId());
                    restaurant.setUpdateBy(operName);
                    this.updateRestaurant(restaurant);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、店铺 " + restaurant.getName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、店铺 " + restaurant.getName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、店铺 " + restaurant.getName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public int countByDeptId(Long deptId) {
        return restaurantMapper.countByDeptId(deptId);
    }

    @Override
    public int brandedCount(String restaurantIds) {
        return restaurantMapper.brandedCount(Convert.toStrArray(restaurantIds));
    }

    @Override
    public List<Restaurant> selectList(RestaurantQueryData restaurantQueryData) {
        return restaurantMapper.selectList(restaurantQueryData);
    }
}
