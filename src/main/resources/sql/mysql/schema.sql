drop table if exists mh_admin;
drop table if exists mh_banner;
drop table if exists mh_user_account;
drop table if exists mh_user_profile;
drop table if exists mh_house;
drop table if exists mh_image;
drop table if exists mh_random_user;

create table mh_admin (
	id bigint unsigned auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	register_date timestamp not null,
	primary key (id)
)TYPE=InnoDB DEFAULT CHARSET=UTF8;

create table mh_banner (
	id bigint unsigned auto_increment,
	url varchar(128) not null,
	thumbnail varchar(128) not null,
	uid bigint unsigned default 0,
	sort mediumint unsigned default 0,
	create_time bigint unsigned default 0,
	primary key (id) 
)TYPE=InnoDB DEFAULT CHARSET=UTF8;

create table mh_user_account (
	id bigint unsigned auto_increment,
    type tinyint unsigned not null,
    username varchar(64) not null,
    password varchar(64) not null,
    salt varchar(64) not null,
    primary key (id)
)TYPE=InnoDB DEFAULT CHARSET=UTF8;

create table mh_user_profile (
	id bigint unsigned auto_increment,
    nickname varchar(64),
    telephone varchar(64) not null,
    realname varchar(64),
    email varchar(64),
    company varchar(1024),
    avatar varchar(64),
    create_time bigint unsigned default 0,
    primary key (id)
)TYPE=InnoDB DEFAULT CHARSET=UTF8;
create index telephone_index on mh_user_profile (telephone(10));
create index realname_index on mh_user_profile(realname(8));

create table mh_house (
	id bigint unsigned auto_increment,
    code varchar(64) not null,
    address varchar(255),
    price smallint unsigned default 0,
    area smallint unsigned default 0,
    bedrooms varchar(64),
    open_time bigint unsigned default 0,
    create_time bigint unsigned default 0,
    last_update_time bigint unsigned default 0,
    lastd2upload_time bigint unsigned default 0,
    lastd3upload_time bigint unsigned default 0,
    bg_music varchar(64),
    cover_img varchar(128),
    package_url varchar(128),
    photographer varchar(128),
    youtube varchar(128),
    description varchar(1024),
    uid bigint unsigned default 0,
    primary key (id)
)TYPE=InnoDB DEFAULT CHARSET=UTF8;

create table mh_image (
	id bigint unsigned auto_increment,
    type tinyint unsigned not null,
    url varchar(128),
    thumbnail varchar(128),
    name varchar(64) not null,
    sort smallint unsigned not null default 0,
    display tinyint unsigned not null default 1,
    hid bigint unsigned not null,
    primary key (id)
)TYPE=InnoDB DEFAULT CHARSET=UTF8;

create table mh_random_user (
	id bigint unsigned auto_increment,
    page_index mediumint unsigned not null,
    page_pos smallint unsigned not null,
    uid bigint unsigned not null,
    primary key (id)
)TYPE=InnoDB DEFAULT CHARSET=UTF8;