package com.ruoyi.catering.utils;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.DataScopeAspect;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-28 16:52
 */
public class BaseUtil {
    public static String dataScopeFilter(SysUser user) {
        StringBuilder sqlString = new StringBuilder();

        for (SysRole role : user.getRoles()) {
            String dataScope = role.getDataScope();
            if (DataScopeAspect.DATA_SCOPE_ALL.equals(dataScope)) {
                sqlString = new StringBuilder();
                break;
            } else if (DataScopeAspect.DATA_SCOPE_CUSTOM.equals(dataScope)) {
                sqlString.append(StringUtils.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", 'd',
                        role.getRoleId()));
            } else if (DataScopeAspect.DATA_SCOPE_DEPT.equals(dataScope)) {
                sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", 'd', user.getDeptId()));
            } else if (DataScopeAspect.DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
                sqlString.append(StringUtils.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
                        'd', user.getDeptId(), user.getDeptId()));
            } else if (DataScopeAspect.DATA_SCOPE_SELF.equals(dataScope)) {
                // 数据权限为仅本人且没有userAlias别名不查询任何数据
                sqlString.append(" OR 1=0 ");
            }
        }

        if (StringUtils.isNotBlank(sqlString.toString())) {
            return " AND (" + sqlString.substring(4) + ")";
        }
        return "";
    }
}