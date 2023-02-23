SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `user` CASCADE ;
DROP TABLE IF EXISTS `avatar` CASCADE;
DROP TABLE IF EXISTS `todo` CASCADE;
DROP TABLE IF EXISTS `world_avatar` CASCADE;
DROP TABLE IF EXISTS `hashtag` CASCADE;
DROP TABLE IF EXISTS `world_hashtag` CASCADE;
DROP TABLE IF EXISTS `world` CASCADE;
DROP TABLE IF EXISTS `report` CASCADE;
DROP TABLE IF EXISTS `avatar_todo` CASCADE;
DROP TABLE IF EXISTS `recommended_alarm` CASCADE;
DROP TABLE IF EXISTS `recommended_world` CASCADE;
DROP TABLE IF EXISTS `recommended_todo` CASCADE;
DROP TABLE IF EXISTS `word_today` CASCADE;
DROP TABLE IF EXISTS `block` CASCADE;
SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE `user` (
                        `user_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE ,
                        `social_id`	bigint	NULL,
                        `social_type`	varchar(10)	NULL,
                        `refresh_token`	varchar(100)	NULL,
                        `device_token`	varchar(100)	NULL,
                        `created_at` varchar(30) NULL,
                        `modified_at` varchar(30) NULL,
                    PRIMARY KEY (`user_id`)
);

CREATE TABLE `avatar` (
                             `avatar_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                             `user_id`	bigint	NOT NULL,
                             `avatar_name`	varchar(12)	NULL,
                             `avatar_message`	varchar(30)	NULL,
                             `avatar_img`	varchar(200)	NULL,
                             `created_at` varchar(30) NULL,
                             `modified_at` varchar(30) NULL,
                             PRIMARY KEY (`avatar_id`)
);

CREATE TABLE `todo` (
                        `todo_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                        `world_id`	bigint	NOT NULL,
                        `todo_content`	varchar(50)	NULL,
                        `created_at` varchar(30) NULL,
                        `modified_at` varchar(30) NULL,
                        PRIMARY KEY (`todo_id`)
);

CREATE TABLE `world_avatar` (
                                   `world_avatar_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                   `avatar_id`	bigint	NOT NULL,
                                   `world_id`	bigint	NOT NULL,
                                   `created_at` varchar(30) NULL,
                                   `modified_at` varchar(30) NULL,
                                   PRIMARY KEY (`world_avatar_id`)
);

CREATE TABLE `hashtag` (
                           `hashtag_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                           `hashtag_name`	varchar(20)	NOT NULL UNIQUE ,
                           `created_at` varchar(30) NULL,
                           `modified_at` varchar(30) NULL,
                           PRIMARY KEY (`hashtag_id`)
);

CREATE TABLE `world_hashtag` (
                                 `world_hashtag_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                 `world_id`	bigint	NOT NULL,
                                 `hashtag_id`	bigint	NOT NULL,
                                 `created_at` varchar(30) NULL,
                                 `modified_at` varchar(30) NULL,
                                 PRIMARY KEY (`world_hashtag_id`)
);


CREATE TABLE `world` (
                         `world_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                         `world_name`	varchar(14)	NULL,
                         `world_user_limit`	tinyint	NOT NULL,
                         `world_img`	varchar(200)	NULL,
                         `world_start_date`	datetime	NULL,
                         `world_end_date`	datetime	NULL,
                         `world_notice`	varchar(200)	NULL,
                         `world_password`	varchar(20)	NULL,
                         `world_host_user_id`	bigint	NULL,
                         `created_at` varchar(30) NULL,
                         `modified_at` varchar(30) NULL,
                         PRIMARY KEY (`world_id`)
);

CREATE TABLE `report` (
                          `report_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                          `report_type`	varchar(20)	NULL,
                          `reporting_user_id`	bigint	NULL,
                          `reported_user_id`	bigint	NULL,
                          `created_at` varchar(30) NULL,
                          `modified_at` varchar(30) NULL,
                          PRIMARY KEY (`report_id`)
);

CREATE TABLE `avatar_todo` (
                                  `avatar_todo_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                  `todo_id`	bigint	NOT NULL,
                                  `avatar_id`	bigint	NOT NULL,
                                  `created_at` varchar(30) NULL,
                                  `modified_at` varchar(30) NULL,
                                  PRIMARY KEY (`avatar_todo_id`)
);

CREATE TABLE `recommended_alarm` (
                                    `recommended_alarm_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                    `recommended_alarm_content`	varchar(20)	NULL,
                                    `created_at` varchar(30) NULL,
                                    `modified_at` varchar(30) NULL,
                                    PRIMARY KEY (`recommended_alarm_id`)
);

CREATE TABLE `recommended_world` (
                                    `recommended_world_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                    `recommended_world_name`	varchar(30)	NULL,
                                    `created_at` varchar(30) NULL,
                                    `modified_at` varchar(30) NULL,
                                    PRIMARY KEY (`recommended_world_id`)
);

CREATE TABLE `recommended_todo` (
                                   `recommended_todo_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                   `recommended_world_id`	bigint	NOT NULL,
                                   `recommended_todo_content`	varchar(50)	NULL,
                                   `created_at` varchar(30) NULL,
                                   `modified_at` varchar(30) NULL,
                                   PRIMARY KEY (`recommended_todo_id`)
);

CREATE TABLE `word_today` (
                             `word_today_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                             `world_avatar_id`	bigint	NOT NULL,
                             `word_today_content`	varchar(300)	NULL,
                             `created_at` varchar(30) NULL,
                             `modified_at` varchar(30) NULL,
                             PRIMARY KEY (`word_today_id`)
);

CREATE TABLE `block` (
                         `block_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                         `blocking_user_id`	bigint	NULL,
                         `blocked_user_id`	bigint	NULL,
                         `created_at` varchar(30) NULL,
                         `modified_at` varchar(30) NULL,
                         PRIMARY KEY (`block_id`)
);