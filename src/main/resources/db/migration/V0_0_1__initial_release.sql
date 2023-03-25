create table taxpayer
(
    id      int primary key auto_increment,
    name    VARCHAR(25)                                        not null,
    surname VARCHAR(25)                                        not null,
    email   VARCHAR(25)                                        not null,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
    deactivated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);