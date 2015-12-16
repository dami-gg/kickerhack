
# --- !Ups

create schema "kicker";
create table "kicker"."user" ("user_id" BIGSERIAL NOT NULL PRIMARY KEY, "name" VARCHAR(254) NOT NULL);

# --- !Downs

drop table "kicker"."user";
drop schema "kicker";