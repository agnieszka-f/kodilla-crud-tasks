package com.crud.tasks.config;

import com.crud.tasks.trello.config.AdminConfig;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigTest {
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private TrelloConfig trelloConfig;
    @Test
    public void testAdminConfig(){
        Assert.assertEquals("agafal1117@gmail.com", adminConfig.getAdminMail());
    }
    @Test
    public void testTrelloConfig(){
        Assert.assertEquals("https://api.trello.com/1", trelloConfig.getTrelloApiEndpoint());
        Assert.assertEquals("679b11918627501dedb210f680f21bad", trelloConfig.getTrelloAppKey());
        Assert.assertEquals("0f860d4803fba69ac83ba9041a92069cfa52d32ddf2136ca6030843931f8deaf", trelloConfig.getTrelloToken());
        Assert.assertEquals("agaf8", trelloConfig.getTrelloUsername());
    }
}
