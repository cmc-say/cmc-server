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
