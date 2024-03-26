create table review (
                         id BIGINT AUTO_INCREMENT primary key,
                         title varchar(255) not null,
                         content varchar(255) not null,
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                        rating double not null
--                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table comment (
                         id BIGINT AUTO_INCREMENT primary key,
                         review_id BIGINT not null,
                         content varchar(255) not null,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (review_id) REFERENCES review(id)
);