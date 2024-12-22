-- auto-generated definition
create table users
(
    id                                   bigint auto_increment
        primary key,
    created_at                           datetime(6)  not null,
    last_modified_at                     datetime(6)  null,
    company                              varchar(255) null,
    email                                varchar(255) not null,
    email_verification_token             varchar(255) null,
    email_verification_token_expiry_date datetime(6)  null,
    email_verified                       bit          null,
    first_name                           varchar(255) null,
    last_name                            varchar(255) null,
    location                             varchar(255) null,
    password                             varchar(255) null,
    password_reset_token                 varchar(255) null,
    password_reset_token_expiry_date     datetime(6)  null,
    position                             varchar(255) null,
    profile_complete                     bit          null,
    profile_picture                      varchar(255) null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email)
);