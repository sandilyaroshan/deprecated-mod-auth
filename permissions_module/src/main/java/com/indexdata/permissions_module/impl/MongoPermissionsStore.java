/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indexdata.permissions_module.impl;

import com.indexdata.permissions_module.PermissionsStore;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;


/**
 *
 * @author kurt
 */
public class MongoPermissionsStore implements PermissionsStore {
  private MongoClient mongoClient;
  
  public MongoPermissionsStore(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  @Override
  public Future<Boolean> addPermission(String permission) {
    Future<Boolean> future = Future.future();
    JsonObject query = new JsonObject().put("permission_name", permission);
    mongoClient.find("permissions", query, res -> {
      if(res.succeeded()) {
        future.complete(false);
      } else {
        JsonObject insert = new JsonObject()
                .put("permission_name", permission)
                .put("sub_permissions", new JsonArray());
        mongoClient.insert("permissions", insert, res2 -> {
          if(res2.succeeded()) {
            future.complete(true);
          } else {
            future.fail(res2.result());
          }
        });
      }
    });
    return future;
  }

  @Override
  public Future<Boolean> addSubPermission(String permission, String sub) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Future<Boolean> removePermission(String permission) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Future<Boolean> removeSubPermission(String permission, String sub) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Future<Boolean> addUser(String user) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Future<Boolean> removeUser(String user) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Future<Boolean> addPermissionToUser(String user, String permission) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Future<Boolean> removePermissionFromUser(String user, String permission) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Future<JsonArray> getPermissionsForUser(String user) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Future<JsonArray> getSubPermissions(String permission) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Future<JsonArray> getExpandedPermissions(String permission) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}