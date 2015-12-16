
# --- !Ups

create table "kicker"."game" (g_id BIGSERIAL NOT NULL PRIMARY KEY);
create table "kicker"."player" (p_id BIGSERIAL NOT NULL PRIMARY KEY, p_user_id BIGINT NOT NULL REFERENCES user(u_id), p_game_id BIGINT NOT NULL REFERENCES game(g_id), p_position VARCHAR(254) NOT NULL, p_side VARCHAR(254) NOT NULL);


# --- !Downs

drop table "kicker"."player";
drop table "kicker"."game";
