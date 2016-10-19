/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecg.refdata.core;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EjbConnectorTest {

    public EjbConnectorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
        try {
            Properties properties = new Properties();

            properties.put("java.naming.factory.initial",
                    "org.apache.openejb.client.RemoteInitialContextFactory");

//            properties.put("java.naming.factory.url.pkgs",
//                    "org.jboss.naming rg.jnp.interfaces");
//            http://127.0.0.1:8080/tomee/ejb
            properties.put("java.naming.provider.url", "http://localhost:8080/tomee/ejb");

            InitialContext ctx = new InitialContext(properties);

            ctx.lookup("");
        } catch (NamingException ex) {
            Logger.getLogger(EjbConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
