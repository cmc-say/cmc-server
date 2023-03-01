create table hashtag
(
    hashtag_id   bigint auto_increment
        primary key,
    hashtag_name varchar(20) not null,
    created_at   varchar(30) null,
    modified_at  varchar(30) null,
    constraint hashtag_id
        unique (hashtag_id),
    constraint hashtag_name
        unique (hashtag_name)
);

create table recommended_alarm
(
    recommended_alarm_id      bigint auto_increment
        primary key,
    recommended_alarm_content varchar(20) null,
    created_at                varchar(30) null,
    modified_at               varchar(30) null,
    constraint recommended_alarm_id
        unique (recommended_alarm_id)
);

create table recommended_world
(
    recommended_world_id   bigint auto_increment
        primary key,
    recommended_world_name varchar(30) null,
    created_at             varchar(30) null,
    modified_at            varchar(30) null,
    constraint recommended_world_id
        unique (recommended_world_id)
);

create table recommended_todo
(
    recommended_todo_id      bigint auto_increment
        primary key,
    recommended_world_id     bigint      not null,
    recommended_todo_content varchar(50) null,
    created_at               varchar(30) null,
    modified_at              varchar(30) null,
    constraint recommended_todo_id
        unique (recommended_todo_id),
    constraint FK_Recommended_World_TO_Recommended_Todo_1
        foreign key (recommended_world_id) references recommended_world (recommended_world_id)
);

create table user
(
    user_id       bigint auto_increment
        primary key,
    social_id     bigint       null,
    social_type   varchar(10)  null,
    refresh_token varchar(100) null,
    device_token  varchar(100) null,
    created_at    varchar(30)  null,
    modified_at   varchar(30)  null,
    constraint user_id
        unique (user_id)
);

create table avatar
(
    avatar_id      bigint auto_increment
        primary key,
    user_id        bigint       not null,
    avatar_name    varchar(12)  null,
    avatar_message varchar(30)  null,
    avatar_img     varchar(200) null,
    created_at     varchar(30)  null,
    modified_at    varchar(30)  null,
    constraint avatar_id
        unique (avatar_id),
    constraint foreign_key_name
        foreign key (user_id) references user (user_id),
    constraint FK_User_TO_Avatar_1
        foreign key (user_id) references user (user_id)
);

create table block
(
    block_id         bigint auto_increment
        primary key,
    blocking_user_id bigint      null,
    blocked_user_id  bigint      null,
    created_at       varchar(30) null,
    modified_at      varchar(30) null,
    constraint UK_blocking_user_id_blocked_user_id
        unique (blocking_user_id, blocked_user_id),
    constraint block_id
        unique (block_id),
    constraint FK_User_TO_Block_1
        foreign key (blocked_user_id) references user (user_id)
);

create table report
(
    report_id         bigint auto_increment
        primary key,
    report_type       varchar(20) null,
    reporting_user_id bigint      null,
    reported_user_id  bigint      null,
    created_at        varchar(30) null,
    modified_at       varchar(30) null,
    constraint UK_reporting_user_id_reported_user_id
        unique (reported_user_id, reporting_user_id),
    constraint key_name
        unique (reported_user_id, reporting_user_id),
    constraint report_id
        unique (report_id),
    constraint FK_User_TO_Report_1
        foreign key (reported_user_id) references user (user_id),
    constraint report_user_user_id_fk
        foreign key (reported_user_id) references user (user_id)
            on update cascade on delete cascade
);

create table world
(
    world_id           bigint auto_increment
        primary key,
    world_name         varchar(14)  null,
    world_user_limit   tinyint      not null,
    world_img          varchar(200) null,
    world_start_date   datetime     null,
    world_end_date     datetime     null,
    world_notice       varchar(200) null,
    world_password     varchar(20)  null,
    world_host_user_id bigint       null,
    created_at         varchar(30)  null,
    modified_at        varchar(30)  null,
    constraint world_id
        unique (world_id)
);

create table todo
(
    todo_id      bigint auto_increment
        primary key,
    world_id     bigint      not null,
    todo_content varchar(50) null,
    created_at   varchar(30) null,
    modified_at  varchar(30) null,
    constraint todo_id
        unique (todo_id),
    constraint FK_World_TO_Todo_1
        foreign key (world_id) references world (world_id)
);

create table world_avatar
(
    world_avatar_id bigint auto_increment
        primary key,
    avatar_id       bigint      not null,
    world_id        bigint      not null,
    created_at      varchar(30) null,
    modified_at     varchar(30) null,
    constraint UK_world_id_avatar_id
        unique (world_id, avatar_id),
    constraint world_avatar_id
        unique (world_avatar_id),
    constraint FK_Avatar_TO_World_Avatar_1
        foreign key (avatar_id) references avatar (avatar_id),
    constraint FK_World_TO_World_Avatar_1
        foreign key (world_id) references world (world_id)
);

create table checked_todo
(
    checked_todo_id bigint auto_increment
        primary key,
    todo_id         bigint      not null,
    world_avatar_id bigint      not null,
    created_at      varchar(30) null,
    modified_at     varchar(30) null,
    constraint avatar_todo_id
        unique (checked_todo_id),
    constraint FK_Todo_TO_Checked_Todo_1
        foreign key (todo_id) references todo (todo_id),
    constraint FK_World_Avatar_TO_Checked_Todo_1
        foreign key (world_avatar_id) references world_avatar (world_avatar_id)
);

create table wordtoday
(
    wordtoday_id      bigint auto_increment
        primary key,
    world_avatar_id   bigint       not null,
    wordtoday_content varchar(300) null,
    created_at        varchar(30)  null,
    modified_at       varchar(30)  null,
    constraint wordtoday_id
        unique (wordtoday_id),
    constraint FK_World_Avatar_TO_Wordtoday_1
        foreign key (world_avatar_id) references world_avatar (world_avatar_id)
);

create table world_hashtag
(
    world_hashtag_id bigint auto_increment
        primary key,
    world_id         bigint      not null,
    hashtag_id       bigint      not null,
    created_at       varchar(30) null,
    modified_at      varchar(30) null,
    constraint UK_world_id_hashtag_id
        unique (world_id, hashtag_id),
    constraint world_hashtag_id
        unique (world_hashtag_id),
    constraint FK_Hashtag_TO_World_Hashtag_1
        foreign key (hashtag_id) references hashtag (hashtag_id),
    constraint FK_World_TO_World_Hashtag_1
        foreign key (world_id) references world (world_id)
);

