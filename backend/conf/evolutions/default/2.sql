# --- !Ups

CREATE TABLE "kicker"."game" (
  g_id BIGSERIAL NOT NULL PRIMARY KEY
);
CREATE TABLE "kicker"."player" (
  p_id       BIGSERIAL    NOT NULL PRIMARY KEY,
  p_user_id  BIGINT       NOT NULL REFERENCES "kicker"."user" (u_id),
  p_game_id  BIGINT       NOT NULL REFERENCES "kicker"."game" (g_id),
  p_position VARCHAR(254) NOT NULL,
  p_side     VARCHAR(254) NOT NULL
);

CREATE TABLE "kicker"."nfcData" (
  nd_tag      TEXT PRIMARY KEY,
  nd_table_id BIGINT NOT NULL REFERENCES "kicker"."kickerTable" (kt_id),
  nd_position TEXT,
  nd_side     TEXT
);
# --- !Downs

DROP TABLE "kicker"."player";
DROP TABLE "kicker"."game";
DROP TABLE "kicker"."nfcData";
