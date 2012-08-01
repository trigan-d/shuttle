/**
 * @author trigan-d
 * Create game for testing purposes
 */

insert into games("name", description, main_url, payment_url, default_image_url, secret_key, xsolla_rate, developer_id)
values ('test game', 'this game is created for testing purposes', '${application.url}/debug/TestGame.jsp', 
        '${application.url}/debug/TestGamePay.jsp', 'http://static.barbars.ru/images/logo_120x160.png', 
        'secret', 1, (select id from developers where name='${overmobile.developer}'));	
   
   