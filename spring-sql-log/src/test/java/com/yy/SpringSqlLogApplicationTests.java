package com.yy;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.bean.User;
import com.yy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
class SpringSqlLogApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void contextLoads() throws Exception {
        /**
         * 1、mockMvc.perform执行一个请求。
         * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
         * 3、ResultActions.param添加请求传值
         * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
         * 5、ResultActions.andExpect添加执行完成后的断言。
         * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
         *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
         * 5、ResultActions.andReturn表示执行完成后返回相应的结果。
         */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/userById")
                .param("name", "lvgang")
                .accept(MediaType.APPLICATION_JSON))
                //等同于Assert.assertEquals(200,status);
                .andExpect(MockMvcResultMatchers.status().isOk())
                //等同于 Assert.assertEquals("hello world!",content);
//                .andExpect(MockMvcResultMatchers.content().string("hello world!"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //得到返回代码
        int status = mvcResult.getResponse().getStatus();
        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString();
        //&#x65ad;&#x8a00;&#xff0c;&#x5224;&#x65ad;&#x8fd4;&#x56de;&#x4ee3;&#x7801;&#x662f;&#x5426;&#x6b63;&#x786e;
        assertEquals(200, status);
        //断言，判断返回的值是否正确
        assertEquals("hello world!", content);
    }


    @Test
    void testSql() {
        Page page = new Page(1, 2);
        Page userPage = userService.page(page, null);
        log.info("user size:{}", userPage.getRecords().size());
        System.out.println(userPage.getRecords());
    }
    @Test
    void testSaveSqlLog(){
        User user = userService.getUserById(1L);
        log.info("user info:{}",user);
    }



}
