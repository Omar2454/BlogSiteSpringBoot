create table users
(
    user_id      int auto_increment
        primary key,
    created_at   datetime(6)            null,
    email        varchar(50)            not null,
    first_name   varchar(50)            null,
    last_name    varchar(50)            null,
    password     varchar(100)           not null,
    roles        enum ('ADMIN', 'USER') not null,
    updated_at   datetime(6)            null,
    phone_number varchar(20)            null,
    bio          varchar(100)           null,
    image        varchar(256)           null,
    facebook     varchar(100)           null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email)
);

create table friendships
(
    friendshipid int auto_increment
        primary key,
    accepted_at  datetime(6)                              null,
    friend_id    int                                      null,
    requested_at datetime(6)                              null,
    status       enum ('ACCEPTED', 'PENDING', 'REJECTED') null,
    userid1      int                                      not null,
    userid2      int                                      not null,
    constraint FK1mnlps2hp64ynsxy0hgoyrex9
        foreign key (userid1) references users (user_id)
            on delete cascade,
    constraint FKbv4v0u7akycmtg2hyguqg5xi1
        foreign key (userid2) references users (user_id)
            on delete cascade
);

create table posts
(
    post_id          int auto_increment
        primary key,
    created_at       datetime(6)    null,
    image_base       varchar(255)   null,
    post_description varchar(10000) null,
    post_title       varchar(1000)  null,
    updated_at       datetime(6)    null,
    shared_post_id   int            null,
    user_id          int            not null,
    constraint FK5lidm6cqbc7u4xhqpxm898qme
        foreign key (user_id) references users (user_id)
            on delete cascade,
    constraint FKmk5q3yvwj8drh5btwuvgbc0p0
        foreign key (shared_post_id) references posts (post_id)
);

create table comments
(
    comment_id   int auto_increment
        primary key,
    comment_text varchar(10000) not null,
    created_at   datetime(6)    null,
    updated_at   datetime(6)    null,
    post_id      int            not null,
    user_id      int            not null,
    constraint FK8omq0tc18jd43bu5tjh6jvraq
        foreign key (user_id) references users (user_id)
            on delete cascade,
    constraint FKh4c7lvsc298whoyd4w9ta25cr
        foreign key (post_id) references posts (post_id)
            on delete cascade
);

create table reacts
(
    post  int                            not null,
    user  int                            not null,
    emoji enum ('NOTHING', 'LIKE', 'LOVE') not null,
    primary key (post, user),
    constraint FK6fpuwxwedqxn4dg7cp2rpua7i
        foreign key (user) references users (user_id)
            on delete cascade,
    constraint FKra3g1jhgd48imqaku1ia53qlj
        foreign key (post) references posts (post_id)
            on delete cascade
);


