-- user
INSERT INTO User (user_id, device_token, refresh_token, social_id, social_type) VALUES (1, '1' , 'refresh', '123', 'KAKAO');
INSERT INTO User (user_id, device_token, refresh_token, social_id, social_type) VALUES (2, '2' , 'refresh', '123', 'KAKAO');
INSERT INTO User (user_id, device_token, refresh_token, social_id, social_type) VALUES (3, '3' , 'refresh', '123', 'KAKAO');

-- avatar
INSERT INTO avatar (avatar_id, user_id, avatar_name, avatar_message, avatar_img) VALUES (1, 1, '아바타', '메세지', 'url');
INSERT INTO avatar (avatar_id, user_id, avatar_name, avatar_message, avatar_img) VALUES (2, 1, '아바타', '메세지', 'url');
INSERT INTO avatar (avatar_id, user_id, avatar_name, avatar_message, avatar_img) VALUES (3, 1, '아바타', '메세지', 'url');
INSERT INTO avatar (avatar_id, user_id, avatar_name, avatar_message, avatar_img) VALUES (4, 2, '아바타', '메세지', 'url');
INSERT INTO avatar (avatar_id, user_id, avatar_name, avatar_message, avatar_img) VALUES (5, 2, '아바타', '메세지', 'url');
INSERT INTO avatar (avatar_id, user_id, avatar_name, avatar_message, avatar_img) VALUES (6, 2, '아바타', '메세지', 'url');
INSERT INTO avatar (avatar_id, user_id, avatar_name, avatar_message, avatar_img) VALUES (7, 3, '아바타', '메세지', 'url');

-- world
INSERT INTO world (world_id, world_name, world_user_limit, world_img, world_start_date, world_end_date, world_notice, world_password, world_host_user_id)
    VALUES (1, "세계관", 10, "url", "2023-02-21", "2023-03-21", "공지", "123", 1);
INSERT INTO world (world_id, world_name, world_user_limit, world_img, world_start_date, world_end_date, world_notice, world_password, world_host_user_id)
    VALUES (2, "세계관", 10, "url", "2023-02-21", "2023-03-21", "공지", "123", 1);
INSERT INTO world (world_id, world_name, world_user_limit, world_img, world_start_date, world_end_date, world_notice, world_password, world_host_user_id)
    VALUES (3, "세계관", 10, "url", "2023-02-21", "2023-03-21", "공지", "123", 2);
-- world_avatar
INSERT INTO world_avatar (world_avatar_id, avatar_id, world_id) VALUES (1, 1, 1);
INSERT INTO world_avatar (world_avatar_id, avatar_id, world_id) VALUES (2, 1, 2);
INSERT INTO world_avatar (world_avatar_id, avatar_id, world_id) VALUES (3, 2, 1);
INSERT INTO world_avatar (world_avatar_id, avatar_id, world_id) VALUES (4, 3, 1);

-- hashtag
INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES (1, "해시태그1");
INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES (2, "해시태그2");
INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES (3, "해시태그3");
INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES (4, "해시태그4");
INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES (5, "해시태그5");
INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES (6, "해시태그6");
INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES (7, "해시태그7");

-- world_hashtag
INSERT INTO world_hashtag (world_hashtag_id, world_id, hashtag_id) VALUES (1, 1, 1);
INSERT INTO world_hashtag (world_hashtag_id, world_id, hashtag_id) VALUES (2, 1, 2);
INSERT INTO world_hashtag (world_hashtag_id, world_id, hashtag_id) VALUES (3, 1, 3);
INSERT INTO world_hashtag (world_hashtag_id, world_id, hashtag_id) VALUES (4, 2, 1);
INSERT INTO world_hashtag (world_hashtag_id, world_id, hashtag_id) VALUES (5, 2, 4);
INSERT INTO world_hashtag (world_hashtag_id, world_id, hashtag_id) VALUES (6, 2, 5);
INSERT INTO world_hashtag (world_hashtag_id, world_id, hashtag_id) VALUES (7, 3, 6);
INSERT INTO world_hashtag (world_hashtag_id, world_id, hashtag_id) VALUES (8, 3, 7);
INSERT INTO world_hashtag (world_hashtag_id, world_id, hashtag_id) VALUES (9, 3, 1);
INSERT INTO world_hashtag (world_hashtag_id, world_id, hashtag_id) VALUES (10, 3, 2);
INSERT INTO world_hashtag (world_hashtag_id, world_id, hashtag_id) VALUES (11, 3, 3);



