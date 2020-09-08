package com.github.zmzhou.easyboot.api.system.controller;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.system.entity.SysRole;
import com.github.zmzhou.easyboot.api.system.vo.SysRoleVo;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;

/**
 * The type Role controller test.
 */
class RoleControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private RoleController controller;
	
	/**
	 * The Mock mvc.
	 */
	private MockMvc mockMvc;

	/**
	 * Sets .
	 */
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	/**
	 * List.
	 */
	@Test
	void list() {
		ApiResult<TableDataInfo> res = controller.list(params);
		Assertions.assertNotNull(res);
	}

	/**
	 * Gets one.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void getOne() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/system/role/getOne/2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		log.info(mvcResult.getResponse().getContentAsString());
	}

	/**
	 * Change status.
	 */
	@Test
	void changeStatus() {
		params.setId(2L);
		params.setStatus("1");
		ApiResult<Integer> res = controller.changeStatus(params);
		Assertions.assertEquals(1, res.getData());
	}

	/**
	 * Add.
	 */
	@Test
    void add() {
		SysRoleVo role = new SysRoleVo();
		BeanUtils.copyProperties(controller.getOne(2L).getData(), role);
		role.setId(0L);
		ApiResult<SysRole> res = controller.add(role);
		Assertions.assertNotNull(res);
		try {
			ApiResult<Integer> result = controller.update(role);
			Assertions.assertNotNull(result);
			ApiResult<Integer> result2 = controller.remove(new Long[]{role.getId()});
			Assertions.assertNotNull(result2);
			role.setRoleCode(JUNIT);
			res = controller.add(role);
			Assertions.assertNotNull(res);
			role.setRoleName(JUNIT);
			res = controller.add(role);
			Assertions.assertNotNull(res);
			role.setUpdateTime(new Date());
		} catch (BaseException e){
			Assertions.assertNotNull(e);
		}
    }
}
