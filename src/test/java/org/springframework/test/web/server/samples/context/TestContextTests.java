package org.springframework.test.web.server.samples.context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultActions.response;

/**
 * Scenarios for setting up MockMVC with {@link org.springframework.test.context.TestContext}'s ApplicationContext.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestContextTests {

    @Autowired
    ApplicationContext context;

    @Test
    public void responseBodyHandler() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.applicationContextMvcSetup(context)
                .configureWebAppRootDir("src/test/webapp", false).build();

		mockMvc.perform(get("/form"))
			.andExpect(response().status().isOk());

        mockMvc.perform(get("/wrong"))
            .andExpect(response().status().isNotFound());
    }

    @Controller
    static class TestController {

        @RequestMapping("/form")
        public @ResponseBody
        String form(){
            return "hello";
        }

    }
}