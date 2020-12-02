
~~1. 아이템 정보 메뉴 세팅~~

~~2. 경매 입찰 구현 ( 트리거 포함 )~~

    * 입찰 시 경매 정보 업데이트 (상위 입찰자, 입찰가, 입찰 수)

    * buy now case

~~3. 경매 종료 시 트리거 구현~~

~~4. 관리자 메뉴 개발~~
    * 전체 아이템 검색

    * 회사 월 총 수익 합계

    * 아이템 카테고리 별 거래량 순위

    * 최고가 거래 순위 ( 아이템 ) 

    * top seller


~~5. 서버 테스팅~~

* 모든 기능


6. 도식화 

7. 리포트 작성

* 서론

* diagram info

* TRIGGER, EVENT, PROCEDURE

* DML statement

* User Manual






*** 

seller_id buyer_id to invoice
and join

___Prevent from seller bid for own item___

___AND bid price always greater than current bid price___

_TRIGGER_

1. 유저,아이템은 따로 삭제하지않는다.

2. 입찰시 bid -> bid_info update(bid_num, higgest bidder,cur_price)

3. 경매 종료 시bidinfo -> invoice(create) 
          

## TASKLIST

 - [x] bid TRIGGER

 - [x] preset item

 - [ ] makefile query
