/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jc.kafka.keycloak;

import java.util.List;
import java.util.Map;
import javax.security.auth.callback.Callback;
import javax.security.auth.login.AppConfigurationEntry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jean
 */
public class KeycloakLoginCallbackHandlerTest {
    
    public KeycloakLoginCallbackHandlerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of configure method, of class KeycloakLoginCallbackHandler.
     */
    @Test
    public void testConfigure() {
        System.out.println("configure");
        Map map = null;
        String string = "";
        List<AppConfigurationEntry> list = null;
        KeycloakLoginCallbackHandler instance = new KeycloakLoginCallbackHandler();
        instance.configure(map, string, list);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class KeycloakLoginCallbackHandler.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        KeycloakLoginCallbackHandler instance = new KeycloakLoginCallbackHandler();
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handle method, of class KeycloakLoginCallbackHandler.
     * @throws java.lang.Exception
     */
    @Test
    public void testHandle() throws Exception {
        System.out.println("handle");
        Callback[] callbacks = null;
        KeycloakLoginCallbackHandler instance = new KeycloakLoginCallbackHandler();
        instance.handle(callbacks);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
