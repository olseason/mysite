-- board 테이블 생성
CREATE TABLE board (
    no        NUMBER,
    PRIMARY KEY ( no ),
    title     VARCHAR2(500) NOT NULL,
    content   VARCHAR2(4000),
    hit       NUMBER,
    reg_date  DATE NOT NULL,
    user_no   NUMBER NOT NULL,
    CONSTRAINT user_fk FOREIGN KEY ( user_no )
        REFERENCES users ( no )
);


-- board 시퀀스 생성
CREATE SEQUENCE seq_board_no INCREMENT BY 1 START WITH 1 NOCACHE;

-- 조회수 시퀀스 생성
CREATE SEQUENCE seq_hit INCREMENT BY 1 START WITH 1 NOCACHE;

-- insert
INSERT INTO board VALUES (
    seq_board_no.NEXTVAL,
    '안녕하세요',
    '테스트용 게시글입니다',
    0,
    sysdate,
    1
);


--커밋
commit;

--롤백
rollback;