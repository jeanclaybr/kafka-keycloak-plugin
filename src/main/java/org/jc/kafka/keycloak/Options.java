/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jc.kafka.keycloak;

/**
 *
 * @author jean
 */
public abstract class Options {

    public static final String KEYCLOAK_URL = "KEYCLOAK_URL";
    public static final String KEYCLOAK_CLIENT_ID = "KEYCLOAK_CLIENT_ID";
    public static final String KEYCLOAK_REALM = "KEYCLOAK_REALM";
    public static final String KEYCLOAK_CLIENT_SECRET = "KEYCLOAK_CLIENT_SECRET";
    
    private Options() {
    }
    
}
