# search by category
SELECT bid_info_id,description,bid_num,cur_price,highest_bidder,
posted_date,ending_date,buy_now_price 
FROM item INNER JOIN bid_info USING(item_id)
WHERE category_id = ? AND user_id <> ? AND ending_date > current_timestamp();

# search by keyword        
SELECT bid_info_id,description,bid_num,cur_price,highest_bidder,
posted_date,ending_date,buy_now_price
FROM item INNER JOIN bid_info USING(item_id)
WHERE description like '%?%' AND user_id <> ? AND ending_date > current_timestamp();

# search by seller
SELECT bid_info_id,description,bid_num,cur_price,highest_bidder,
posted_date,ending_date,buy_now_price
FROM item INNER JOIN bid_info USING(item_id) 
INNER JOIN user_info USING(user_id) 
WHERE first_name = ? AND user_id <> ? AND ending_date > current_timestamp();

# search by date
SELECT bid_info_id,description,bid_num,cur_price,highest_bidder,
posted_date,ending_date,buy_now_price
FROM item INNER JOIN bid_info USING(item_id)
WHERE posted_date like ? AND user_id <> ? AND ending_date > current_timestamp();

# check user item (sold,unsold)
SELECT bid_info_id,description,bid_num,cur_price,highest_bidder,
posted_date,ending_date,buy_now_price 
FROM item INNER JOIN bid_info using(item_id) 
WHERE issell = ? AND user_id = ?;

# check user bid (sold,unsold)
SELECT description,max(bid_price),cur_price,ending_date
FROM bid as B INNER JOIN
(SELECT * from item INNER JOIN bid_info USING(item_id)) as S USING(bid_info_id)
WHERE B.user_id = ? AND issell = ?
GROUP BY bid_info_id;

# check user account (sell or buy item)
SELECT description,successful_bid_price,transaction_date
FROM invoice INNER JOIN bid_info USING(bid_info_id)
INNER JOIN item USING(item_id) 
WHERE transaction_date like `-%?-%`
AND (buyer | seller)_id = ?
ORDER BY transaction_date;

# admin check summary by category
SELECT sum(successful_bid_price),count(*),item_category
FROM bid_info INNER JOIN invoice USING(bid_info_id)
INNER JOIN item USING(item_id) INNER JOIN category USING(category_id)
GROUP BY categroy_id;

# admin check top3 seller
SELECT sum(successful_bid_price) as S,count(*),email,first_name
FROM invoice INNER JOIN user_info ON seller_id = user_id
GROUP BY user_id
ORDER BY S DESC LIMIT 3;

# admin check top3 item
SELECT description,highest_bidder,bid_num,successful_bid_prcie
FROM item INNER JOIN bid_info USING(item_id)
INNER JOIN invoice USING(bid_info_id)
ORDER BY successful_bid_price DESC LIMIT 3;

# admin check montly profit
SELECT seller_id,successful_bid_price,transaction_date
FROM invoice ORDER BY seller_id;





