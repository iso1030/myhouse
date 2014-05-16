insert into mh_admin (id, login_name, name, password, salt, roles, register_date) 
	values(1,'admin','Admin','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-04 01:00:00');

insert into mh_user_account (id, type, username, password, salt)
    values(1, 1, 'qatest1', '691b14d79bf0fa2215f155235df5e670b64394cc', '7efbd59d9741d34f');
insert into mh_user_account (id, type, username, password, salt)
    values(2, 1, 'qatest2', '691b14d79bf0fa2215f155235df5e670b64394cc', '7efbd59d9741d34f');

insert into mh_user_profile (id, nickname, telephone, realname, email, company, avatar, create_time)
	values(1, 'qatest1', '057189852516', '林德', 'qatest@163.com', '', '', 1398606158221);
insert into mh_user_profile (id, nickname, telephone, realname, email, company, avatar, create_time)
	values(2, 'qatest2', '057289858888', 'jerry lin', 'jerrylin@gmail.com', '', '', 1398606158221);

insert into mh_house (id, code, address, price, area, bedrooms, open_time, create_time, last_update_time, lastd2upload_time, lastd3upload_time, bg_music, youtube, cover_img, uid) 
	values(1, 'f7f96f8f5c914ddea072cc814df87fbd', 'test adress1', 1000, 100, '2room1ting', 0, 0, 0, 0, 0, '', 'Va29YFkchXo', '', 1);
insert into mh_house (id, code, address, price, area, bedrooms, open_time, create_time, last_update_time, lastd2upload_time, lastd3upload_time, bg_music, youtube, cover_img, uid) 
	values(2, '69e896b50ba9434b924bed1595aa8a2e', 'test adress2', 1010, 110, '2room1ting', 0, 0, 0, 0, 0, '', 'Va29YFkchXo', '', 2);
insert into mh_house (id, code, address, price, area, bedrooms, open_time, create_time, last_update_time, lastd2upload_time, lastd3upload_time, bg_music, youtube, cover_img, uid) 
	values(3, '69e896b50ba9434b924bed1595aa8a2e', 'test adress3', 1010, 110, '2room1ting', 0, 0, 0, 0, 0, '', 'Va29YFkchXo', '', 2);

insert into mh_image (id, type, url, thumbnail, name, sort, hid)
	values(1, 2, 'http://p3.music.126.net/nbGDU0br-epDdhswQ1jjmQ==/5683375604082858.jpg', '', 'test1.jpg', 0, 1);
insert into mh_image (id, type, url, thumbnail, name, sort, hid)
	values(2, 2, 'http://p3.music.126.net/nbGDU0br-epDdhswQ1jjmQ==/5683375604082858.jpg', '', 'test2.jpg', 0, 1);
insert into mh_image (id, type, url, thumbnail, name, sort, hid)
	values(3, 2, 'http://p3.music.126.net/nbGDU0br-epDdhswQ1jjmQ==/5683375604082858.jpg', '', 'test3.jpg', 0, 2);
insert into mh_image (id, type, url, thumbnail, name, sort, hid)
	values(4, 2, 'http://p3.music.126.net/nbGDU0br-epDdhswQ1jjmQ==/5683375604082858.jpg', '', 'test4.jpg', 0, 2);
insert into mh_image (id, type, url, thumbnail, name, sort, hid)
	values(5, 2, 'http://p3.music.126.net/nbGDU0br-epDdhswQ1jjmQ==/5683375604082858.jpg', '', 'test5.jpg', 0, 2);


