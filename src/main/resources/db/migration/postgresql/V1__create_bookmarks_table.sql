create sequence bm_id_seq start with 1 increment by 50;


create table bookmarks (
    id bigint default nextval('bm_id_seq') not null,
    created_at timestamp,
    description varchar(255) not null,
    url varchar(255) not null,
    primary key (id)
    );
    
    
insert into bookmarks (created_at, description, url) values 
    (CURRENT_TIMESTAMP, 'Sample Description 1', 'https://example1.com'),
    (CURRENT_TIMESTAMP, 'Sample Description 2', 'https://example2.com'),
    (CURRENT_TIMESTAMP, 'Sample Description 3', 'https://example3.com'),
    (CURRENT_TIMESTAMP, 'Sample Description 4', 'https://example4.com'),
    (CURRENT_TIMESTAMP, 'Sample Description 5', 'https://example5.com'),
    (CURRENT_TIMESTAMP, 'Sample Description 6', 'https://example6.com'),
    (CURRENT_TIMESTAMP, 'Sample Description 7', 'https://example7.com'),
    (CURRENT_TIMESTAMP, 'Sample Description 8', 'https://example8.com'),
    (CURRENT_TIMESTAMP, 'Sample Description 9', 'https://example9.com'),
    (CURRENT_TIMESTAMP, 'Sample Description 10', 'https://example10.com');

