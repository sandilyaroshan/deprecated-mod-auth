/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indexdata.authorization_module.impl;

import com.indexdata.authorization_module.PermissionsSource;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.JsonArray;

/**
 *
 * @author kurt
 */
public class ModulePermissionsSource implements PermissionsSource {

  private String okapiUrl = null;
  private Vertx vertx;
  private String requestToken;
  private String authApiKey = "";
  
  public ModulePermissionsSource(Vertx vertx) {
    //permissionsModuleUrl = url;
    this.vertx = vertx;
  }
  
  public void setOkapiUrl(String url) {
    okapiUrl = url;
  }
  
  public void setRequestToken(String token) {
    requestToken = token;
  }
  
  public void setAuthApiKey(String key) {
    authApiKey = key;
  }
  
  @Override
  public Future<JsonArray> getPermissionsForUser(String username) {
    Future<JsonArray> future = Future.future();
    HttpClientOptions options = new HttpClientOptions();
    options.setConnectTimeout(10);
    options.setIdleTimeout(10);
    HttpClient client = vertx.createHttpClient(options);
    String okapiUrlFinal = "http://localhost:9130";
    if(okapiUrl != null) {
      okapiUrlFinal = okapiUrl;
    }
    String requestUrl = okapiUrlFinal + "/perms/privileged/users/" + username + "/permissions";
    System.out.println("Requesting permissions from URL at " + requestUrl);
    HttpClientRequest req = client.getAbs(requestUrl, res-> {
      if(res.statusCode() == 200) {
        res.bodyHandler(res2 -> {
          JsonArray permissions = new JsonArray(res2.toString());
          future.complete(permissions);
        });
      } else {
        //future.fail("Unable to retrieve permissions");
        future.complete(new JsonArray());
      }
    });
    req.exceptionHandler(exception -> {
      future.fail(exception);
    });
    //req.headers().add("Authorization", "Bearer " + requestToken);
    req.headers().add("Auth_API_Key", authApiKey);
    req.end();
    return future;
  }

  
}