/*
DROP DATABASE IF EXISTS auction_18314788;

CREATE DATABASE IF NOT EXISTS auction_18314788;

USE db18314788;
*/

CREATE TABLE admin
    (
        email NVARCHAR(320) NOT NULL,
        password NVARCHAR(20) NOT NULL,        
        PRIMARY KEY (password)
    );

CREATE TABLE user_info
    (
        user_id INT NOT NULL AUTO_INCREMENT,
        password NVARCHAR(20) NOT NULL ,
        first_name NVARCHAR(20) NOT NULL,
        last_name NVARCHAR(20) NOT NULL,
        email NVARCHAR(320) NOT NULL,
        seller_rating DECIMAL(3,2) DEFAULT 0.00,
        CONSTRAINT passc UNIQUE (password),
        CONSTRAINT emailc UNIQUE (email),
        PRIMARY KEY (user_id)
    );
    
CREATE TABLE item
    (
        item_id INT NOT NULL AUTO_INCREMENT,
        user_id INT,
        posted_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        description TEXT,
        condition_id INT,
        category_id INT,
        PRIMARY KEY(item_id),
        FOREIGN KEY (user_id) REFERENCES user_info(user_id)
    );

CREATE TABLE watchlist
    (
        user_id INT,
        item_id INT,
        PRIMARY KEY (user_id,item_id),
        FOREIGN KEY (user_id) REFERENCES user_info(user_id),
        FOREIGN KEY (item_id) REFERENCES item(item_id)
            ON DELETE CASCADE
    );


    
CREATE TABLE condition
    (
        condition_id INT NOT NULL AUTO_INCREMENT,
        item_state NVARCHAR(40),
        PRIMARY KEY(condition_id)
    );
    
CREATE TABLE category
    (
        category_id INT,
        item_category NVARCHAR(40),
        PRIMARY KEY(category_id)
    );

CREATE TABLE bid_info
    (
        item_id INT,
        bid_info_id INT NOT NULL AUTO_INCREMENT,
        buy_now_price INT,
        cur_price INT DEFAULT 0,
        bid_num INT DEFAULT 0,
        highest_bidder NVARCHAR(20),
        ending_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
        issell Boolean DEFAULT false,
        PRIMARY KEY (bid_info_id),
        FOREIGN KEY (item_id) REFERENCES item(item_id)
            ON DELETE CASCADE
    );

CREATE TABLE bid
    (
        bid_id INT NOT NULL AUTO_INCREMENT,
        user_id INT,
        bidder_name NVARCHAR(20),
        bid_info_id INT,
        bid_price INT,
        bid_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (bid_id),
        FOREIGN KEY (user_id) REFERENCES user_info(user_id),
        FOREIGN KEY (bid_info_id) REFERENCES bid_info(bid_info_id)
     );


CREATE TABLE invoice
    (
        invoice_id INT NOT NULL AUTO_INCREMENT,
        bid_info_id INT,
        seller_id INT,
        buyer_id INT,
        successful_bid_price INT NOT NULL,
        transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (invoice_id),
        FOREIGN KEY (bid_info_id) REFERENCES bid_info(bid_info_id),
        FOREIGN KEY (seller_id) REFERENCES user_info(user_id),
        FOREIGN KEY (buyer_id) REFERENCES user_info(user_id)
    );


DROP TRIGGER IF EXISTS `bid_update`;
DROP TRIGGER IF EXISTS `item_sold`;

DELIMITER $$
CREATE TRIGGER `bid_update` AFTER INSERT ON `bid` FOR EACH ROW
BEGIN
	    UPDATE bid_info 
        SET bid_num = bid_num + 1 
        WHERE bid_info_id = NEW.bid_info_id;      

		UPDATE bid_info 
        SET cur_price = NEW.bid_price,
        highest_bidder = NEW.bidder_name
        WHERE bid_info_id = NEW.bid_info_id 
        AND NEW.bid_price > cur_price;

        UPDATE bid_info
        SET ending_date = CURRENT_TIMESTAMP
        WHERE bid_info_id = NEW.bid_info_id 
        AND buy_now_price = NEW.bid_price;     

        CALL bid_end();
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `item_sold` AFTER UPDATE ON `bid_info` FOR EACH ROW
BEGIN
        DECLARE s_id,b_id INT;
        DECLARE ise Boolean;

        SELECT issell into ise from bid_info 
        where OLD.issell = false AND NEW.issell = true 
        AND bid_info_id = NEW.bid_info_id;

        IF ise = true AND NEW.bid_num <> 0 THEN

		SELECT user_id into s_id from item 
        inner join bid_info using(item_id) 
        where bid_info_id = NEW.bid_info_id;

        SELECT user_id into b_id from bid_info 
        inner join bid using(bid_info_id)
        where NEW.cur_price = bid_price 
        AND bid_info_id = NEW.bid_info_id;

		INSERT INTO 
        invoice(seller_id,buyer_id,successful_bid_price,bid_info_id) 
        VALUES(s_id,b_id,NEW.cur_price,NEW.bid_info_id); 

        END IF;
END$$
DELIMITER ;

DELIMITER $$

DROP PROCEDURE IF EXISTS bid_end;

CREATE PROCEDURE bid_end()
BEGIN	
	    UPDATE bid_info 
        SET issell = true
        where ending_date <= CURRENT_TIMESTAMP
        AND issell = false;
END $$

DELIMITER ;

#SET GLOBAL event_scheduler = ON;

DROP EVENT IF EXISTS bid_scheduler;
DELIMITER $$

create
	event bid_scheduler ON SCHEDULE EVERY 1 MINUTE STARTS '2019-01-01 00:00:00'
    DO
		BEGIN
			CALL bid_end();
		END $$

DELIMITER ;

INSERT INTO admin(email,password) values('ohsolution','2018314788');
INSERT INTO category(category_id,item_category) values(1,'Electronics');
INSERT INTO category(category_id,item_category) values(2,'Books');
INSERT INTO category(category_id,item_category) values(3,'Home');
INSERT INTO category(category_id,item_category) values(4,'Clothing');
INSERT INTO category(category_id,item_category) values(5,'Sporting Goods');



