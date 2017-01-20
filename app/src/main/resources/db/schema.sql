create table post(
  id bigint auto_increment primary key,
  title text not null,
  content text not null,
  posted_at timestamp not null
);
