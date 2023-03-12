create table if not exists wallets
(
    id            uuid not null
        primary key,
    balance       numeric(38, 2),
    currency_type varchar(255),
    name          varchar(255)
);

create extension if not exists "uuid-ossp";

insert into wallets(id, balance, currency_type, name)
values (uuid_generate_v4(), 0.00, 'USD', 'Main wallet')