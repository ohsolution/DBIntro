DROP DATABASE IF EXISTS auction_18314788;

CREATE DATABASE IF NOT EXISTS auction_18314788;

USE auction_18314788;

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
        ns_item INT UNSIGNED DEFAULT 0,
        np_item INT UNSIGNED DEFAULT 0,
        seller_rating DECIMAL(3,2) DEFAULT 0.00,
        CONSTRAINT passc UNIQUE (password),
        CONSTRAINT emailc UNIQUE (email),
        PRIMARY KEY (user_id)
    );

/*
CREATE TABLE seller
    (
        user_id INT,
        item_id INT,
        PRIMARY KEY (user_id,item_id),
        FOREIGN KEY (user_id) REFERENCES user_info(user_id)
    );

CREATE TABLE buyer
    (
        user_id INT,
        bid_info_id INT,
        PRIMARY KEY (user_id,bid_info_id),
        FOREIGN KEY (user_id) REFERENCES user_info(user_id)
    );
*/
    
CREATE TABLE item
    (
        item_id INT NOT NULL AUTO_INCREMENT,
        user_id INT,
        posted_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        description TEXT,
        condition_id INT,
        category_id INT,
        #status NVARCHAR(20),
        #history_id INT,
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


    
CREATE TABLE condi
    (
        condition_id INT NOT NULL AUTO_INCREMENT,
        item_state NVARCHAR(40),
        PRIMARY KEY(condition_id)
    );

    /*
CREATE TABLE status
    (
        status_id INT NOT NULL AUTO_INCREMENT,
        bid_state NVARCHAR(8),
        PRIMARY KEY(status_id)
    );
    */

    
CREATE TABLE category
    (
        category_id INT NOT NULL AUTO_INCREMENT,
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
        ending_date TIMESTAMP,
        invoice_id INT,
        PRIMARY KEY (bid_info_id),
        FOREIGN KEY (item_id) REFERENCES item(item_id)
            ON DELETE CASCADE
    );

CREATE TABLE bid
    (
        user_id INT,
        bidder_name NVARCHAR(20),
        bid_info_id INT,
        bid_price INT,
        bid_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (user_id,bid_info_id,bid_price),
        FOREIGN KEY (user_id) REFERENCES user_info(user_id)
     );


CREATE TABLE invoice
    (
        invoice_id INT NOT NULL AUTO_INCREMENT,
        seller_id INT,
        buyer_id INT,
        successful_bid_price INT NOT NULL,
        buyer_price INT NOT NULL,
        seller_price INT NOT NULL,
        fees INT NOT NULL,
        transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (invoice_id),
        FOREIGN KEY (seller_id) REFERENCES user_info(user_id),
        FOREIGN KEY (buyer_id) REFERENCES user_info(user_id)
    );






















































