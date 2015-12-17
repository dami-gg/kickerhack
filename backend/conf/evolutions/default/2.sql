# --- !Ups

CREATE TABLE "kicker"."game" (
  g_id BIGSERIAL NOT NULL PRIMARY KEY,
  g_table_id BIGINT NOT NULL REFERENCES "kicker"."kickerTable" (kt_id),
  g_goals_home SMALLINT NOT NULL,
  g_goals_away SMALLINT NOT NULL,
  g_started_on TIMESTAMP NOT NULL,
  g_finished_on TIMESTAMP
);
CREATE TABLE "kicker"."player" (
  p_id       BIGSERIAL    NOT NULL PRIMARY KEY,
  p_user_id  BIGINT       NOT NULL REFERENCES "kicker"."user" (u_id),
  p_game_id  BIGINT       NOT NULL REFERENCES "kicker"."game" (g_id),
  p_position VARCHAR(254) NOT NULL,
  p_side     VARCHAR(254) NOT NULL
);


# --- !Downs

DROP TABLE "kicker"."game";
DROP TABLE "kicker"."player";
