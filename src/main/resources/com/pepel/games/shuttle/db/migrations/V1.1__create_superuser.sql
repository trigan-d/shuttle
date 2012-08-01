/**
 * @author trigan-d
 * Create superuser account with all roles. Assign partner and developer entries to that account.
 */

insert into users (name, password) values ('${superuser.login}', '${superuser.password}');

insert into user_groups (user_id, groups) select id, 0 from users where name='${superuser.login}';
insert into user_groups (user_id, groups) select id, 1 from users where name='${superuser.login}';
insert into user_groups (user_id, groups) select id, 2 from users where name='${superuser.login}';

insert into partners ("name", percent_fee) values ('${overmobile.partner}', '100');
insert into channels ("name", partner_id) select 'default', id from partners where name='${overmobile.partner}';
insert into partner_users (partner_id, user_id) values 
	((select id from partners where name='${overmobile.partner}'), (select id from users where name='${superuser.login}')); 
   
insert into developers ("name", percent_fee) values ('${overmobile.developer}', '100');
insert into developer_users (developer_id, user_id) values 
	((select id from developers where name='${overmobile.developer}'), (select id from users where name='${superuser.login}')); 
   