create table zipcodegeo (
    zip char(5) primary key,
    city varchar(64),
    state char(2),
    latitude varchar(64),
    longitude varchar(64),
    timezone int,
    dst int
);

