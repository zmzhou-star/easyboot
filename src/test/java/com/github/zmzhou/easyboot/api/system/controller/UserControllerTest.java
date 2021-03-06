package com.github.zmzhou.easyboot.api.system.controller;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.api.system.vo.SysUserParams;
import com.github.zmzhou.easyboot.api.system.vo.SysUserVo;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;

/**
 * The type User controller test.
 *
 * @description:
 * @author: Administrator
 * @date: Created in 2020/8/30 17:49
 * @version:
 */
class UserControllerTest extends EasybootApplicationTests {

    /**
     * The Controller.
     */
    @Autowired
    private UserController controller;

    /**
     * List.
     */
    @Test
    void list() {
        SysUserParams params = new SysUserParams();
        params.setPageNum(1);
        params.setPageSize(10);
        ApiResult<TableDataInfo> res = controller.list(params);
        Assertions.assertNotNull(res);
    }

    /**
     * Save.
     */
    @Test
    @Rollback
    void save() {
        ApiResult<SysUserVo> res = controller.getUser(2L);
        Assertions.assertNotNull(res);
        SysUserParams params = new SysUserParams();
        params.setPageNum(1);
        params.setPageSize(10);
        params.setStatus("1");
        params.setPassword(PD);
        params.setId(2L);
        // 根据id修改用户状态
        ApiResult<Integer> res2 = controller.changeStatus(params);
        Assertions.assertNotNull(res2);
        SysUserVo vo = new SysUserVo();
        BeanUtils.copyProperties(res.getData(), vo);
        ApiResult<SysUser> result = controller.save(null);
        Assertions.assertNotNull(result);
        result = controller.save(vo);
        Assertions.assertNotNull(result);
        vo.setId(0L);
        vo.setUsername(JUNIT);
        result = controller.save(vo);
        Assertions.assertNotNull(result);
        vo.setUpdateTime(new Date());
        result = controller.update(vo);
        Assertions.assertNotNull(result);
        result = controller.update(null);
        Assertions.assertNotNull(result);
        BeanUtils.copyProperties(result.getData(), vo);
        ApiResult<Object> result2 = controller.delete(new Long[]{vo.getId()});
        Assertions.assertNotNull(result2);
        try {
            // 重置密码
            ApiResult<SysUser> res3 = controller.resetPwd(params.getId(), PD);
            Assertions.assertNotNull(res3);
            params.setId(1L);
            res3 = controller.resetPwd(params.getId(), PD);
            Assertions.assertNotNull(res3);
            params.setId(999L);
            res3 = controller.resetPwd(params.getId(), PD);
            Assertions.assertNotNull(res3);
        } catch (BaseException e) {
            Assertions.assertNotNull(e);
        }
    }
}
