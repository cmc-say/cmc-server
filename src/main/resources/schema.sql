SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `User` CASCADE ;
DROP TABLE IF EXISTS `Avatar` CASCADE;
DROP TABLE IF EXISTS `Todo` CASCADE;
DROP TABLE IF EXISTS `Character_World` CASCADE;
DROP TABLE IF EXISTS `Hashtag` CASCADE;
DROP TABLE IF EXISTS `World_Hashtag` CASCADE;
DROP TABLE IF EXISTS `World` CASCADE;
DROP TABLE IF EXISTS `Report` CASCADE;
DROP TABLE IF EXISTS `Character_Todo` CASCADE;
DROP TABLE IF EXISTS `RecommendedAlarm` CASCADE;
DROP TABLE IF EXISTS `RecommendedWorld` CASCADE;
DROP TABLE IF EXISTS `RecommendedTodo` CASCADE;
DROP TABLE IF EXISTS `WordToday` CASCADE;
DROP TABLE IF EXISTS `Block` CASCADE;
SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE `User` (
                        `user_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE ,
                        `social_id`	bigint	NULL,
                        `social_type`	varchar(10)	NULL,
                        `refresh_token`	varchar(100)	NULL,
                        `device_token`	varchar(100)	NULL,
                    PRIMARY KEY (`user_id`)
);

CREATE TABLE `Avatar` (
                             `avatar_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                             `user_id`	bigint	NOT NULL,
                             `avatar_name`	varchar(12)	NULL,
                             `avatar_message`	varchar(30)	NULL,
                             `avatar_img`	varchar(200)	NULL,
                             PRIMARY KEY (`avatar_id`)
);

CREATE TABLE `Todo` (
                        `todo_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                        `world_id`	bigint	NOT NULL,
                        `todo_content`	varchar(50)	NULL,
                        PRIMARY KEY (`todo_id`)
);

CREATE TABLE `Character_World` (
                                   `character_world_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                   `character_id`	bigint	NOT NULL,
                                   `world_id`	bigint	NOT NULL,
                                   PRIMARY KEY (`character_world_id`)
);

CREATE TABLE `Hashtag` (
                           `hashtag_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                           `hashtag_name`	varchar(20)	NULL,
                           PRIMARY KEY (`hashtag_id`)
);

CREATE TABLE `World_Hashtag` (
                                 `world_hashtag_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                 `world_id`	bigint	NOT NULL,
                                 `hashtag_id`	bigint	NOT NULL,
                                 PRIMARY KEY (`world_hashtag_id`)
);


CREATE TABLE `World` (
                         `world_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                         `world_name`	varchar(14)	NULL,
                         `world_user_limit`	bigint	NOT NULL,
                         `world_is_public`	boolean	NULL,
                         `world_img`	varchar(200)	NULL,
                         `world_start_date`	datetime	NULL,
                         `world_end_date`	datetime	NULL,
                         `world_notice`	varchar(200)	NULL,
                         `world_password`	varchar(20)	NULL,
                         `world_host_user_id`	bigint	NULL,
                         PRIMARY KEY (`world_id`)
);

CREATE TABLE `Report` (
                          `report_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                          `type`	varchar(10)	NULL,
                          `reporting_user_id`	bigint	NULL,
                          `reported_user_id`	bigint	NULL,
                          PRIMARY KEY (`report_id`)
);

CREATE TABLE `Character_Todo` (
                                  `character_todo_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                  `todo_id`	bigint	NOT NULL,
                                  `character_id`	bigint	NOT NULL,
                                  PRIMARY KEY (`character_todo_id`)
);

CREATE TABLE `RecommendedAlarm` (
                                    `recommendedAlarm_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                    `recommendedAlarm_content`	varchar(20)	NULL,
                                    PRIMARY KEY (`recommendedAlarm_id`)
);

CREATE TABLE `RecommendedWorld` (
                                    `recommended_world_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                    `recommended_world_name`	varchar(30)	NULL,
                                    PRIMARY KEY (`recommended_world_id`)
);

CREATE TABLE `RecommendedTodo` (
                                   `recommended_todo_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                                   `recommended_world_id`	bigint	NOT NULL,
                                   `recommended_todo_content`	varchar(50)	NULL,
                                   PRIMARY KEY (`recommended_todo_id`)
);

CREATE TABLE `WordToday` (
                             `word_today_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                             `character_world_id`	bigint	NOT NULL,
                             `word_today_content`	varchar(300)	NULL,
                             PRIMARY KEY (`word_today_id`)
);

CREATE TABLE `Block` (
                         `block_id`	bigint	NOT NULL AUTO_INCREMENT UNIQUE,
                         `blocking_user_id`	bigint	NULL,
                         `blocked_user_id`	bigint	NULL,
                         PRIMARY KEY (`block_id`)
);
