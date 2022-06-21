drop table if exists oauth_client_token;
create table oauth_client_token (
  token_id VARCHAR(255),
  token LONGBLOB,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

drop table if exists oauth_client_details;
CREATE TABLE oauth_client_details (
  client_id varchar(255) NOT NULL,
  resource_ids varchar(255) DEFAULT NULL,
  client_secret varchar(255) DEFAULT NULL,
  scope varchar(255) DEFAULT NULL,
  authorized_grant_types varchar(255) DEFAULT NULL,
  web_server_redirect_uri varchar(255) DEFAULT NULL,
  authorities varchar(255) DEFAULT NULL,
  access_token_validity integer(11) DEFAULT NULL,
  refresh_token_validity integer(11) DEFAULT NULL,
  additional_information varchar(255) DEFAULT NULL,
  autoapprove varchar(255) DEFAULT NULL
);

drop table if exists oauth_access_token;
create table `oauth_access_token` (
  token_id VARCHAR(255),
  token LONGBLOB,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication LONGBLOB,
  refresh_token VARCHAR(255)
);

drop table if exists oauth_refresh_token;
create table `oauth_refresh_token`(
  token_id VARCHAR(255),
  token LONGBLOB,
  authentication LONGBLOB
);

drop table if exists authority;
CREATE TABLE authority (
  id  integer,
  authority varchar(255),
  primary key (id)
);

drop table if exists credentials;
CREATE TABLE credentials (
  id  integer,
  enabled boolean not null,
  name varchar(255) not null,
  password varchar(255) not null,
  version integer,
  primary key (id)
);

drop table if exists credentials_authorities;
CREATE TABLE credentials_authorities (
  credentials_id bigint not null,
  authorities_id bigint not null
);

drop table if exists oauth_code;
create table oauth_code (
  code VARCHAR(255), authentication LONGBLOB
);

drop table if exists oauth_approvals;
create table oauth_approvals (
    userId VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt DATETIME,
    lastModifiedAt DATETIME
);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL DEFAULT 0,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `sex` int(11) NULL DEFAULT NULL COMMENT '0:unknown 1：Male 2：Female',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(255) NULL DEFAULT 0 COMMENT '0：normal user 1：banned user',
  `last_signin_time` datetime(0) NULL DEFAULT NULL,
  `last_signout_time` datetime(0) NULL DEFAULT NULL,
  `update_at` datetime(0) NULL DEFAULT NULL,
  `create_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

#$2a$10$utkxMg3/7GYyihwMgL6EOuVpUa7KFQo3SLu2OLKec/dcaMvAVshka --> 123456

INSERT INTO `oauth_client_details` VALUES ('client_1', '', '$2a$10$utkxMg3/7GYyihwMgL6EOuVpUa7KFQo3SLu2OLKec/dcaMvAVshka', 'all,read,write', 'client_credentials', '', 'client_credentials', 7200, NULL, '{}', '');
INSERT INTO `oauth_client_details` VALUES ('client_2', '', '$2a$10$utkxMg3/7GYyihwMgL6EOuVpUa7KFQo3SLu2OLKec/dcaMvAVshka', 'all,read,write', 'password,refresh_token', '', 'password', 7200, 10000, '{}', '');
INSERT INTO `oauth_client_details` VALUES ('client_3', '', '$2a$10$utkxMg3/7GYyihwMgL6EOuVpUa7KFQo3SLu2OLKec/dcaMvAVshka', 'all,read,write', 'authorization_code', 'http://localhost:8080/signin,http://localhost:8080/login', 'authorization_code,refresh_token', 7200, 10000, '{}', '');
INSERT INTO `oauth_client_details` VALUES ('client_4', '', '$2a$10$utkxMg3/7GYyihwMgL6EOuVpUa7KFQo3SLu2OLKec/dcaMvAVshka', 'all,read,write', 'all flow,authorization_code,client_credentials,refresh_token,password,implicit', 'http://localhost:8080/signin,http://localhost:8080/login', '', 7200, 10000, '{}', '');

INSERT INTO `users` VALUES (0, 'jamie', 'Jamie Zhang', '$2a$10$utkxMg3/7GYyihwMgL6EOuVpUa7KFQo3SLu2OLKec/dcaMvAVshka', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);