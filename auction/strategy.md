
1. 아이템 정보 메뉴 세팅

2. 경매 입찰 구현 ( 트리거 포함 )
    * 입찰 시 경매 정보 업데이트 (상위 입찰자, 입찰가, 입찰 수)
    * buy now case 

3. 경매 종료 시 트리거 구현

4. 관리자 메뉴 개발

seller_id buyer_id to invoice
and join

___Prevent from seller bid for own item___

___AND bid price always greater than current bid price___

_TRIGGER_

1. 유저,아이템은 따로 삭제하지않는다.

2. 입찰시 bid -> bid_info update(bid_num, higgest bidder,cur_price)

3. 경매 종료 시bidinfo -> invoice(create) 
          

## TASKLIST

 - [ ] bid TRIGGER

 - [x] preset item
