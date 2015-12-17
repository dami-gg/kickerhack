# --- !Ups

CREATE SCHEMA "kicker";
CREATE TABLE "kicker"."user" (
  u_id   BIGSERIAL PRIMARY KEY,
  u_name VARCHAR(254) NOT NULL
);
CREATE TABLE "kicker"."kickerTable" (
  kt_id               BIGSERIAL PRIMARY KEY,
  kt_name             TEXT,
  kt_building         TEXT NOT NULL,
  kt_floor            TEXT NOT NULL,
  kt_color_home       TEXT NOT NULL,
  kt_color_away       TEXT NOT NULL,
  kt_last_goal_scored TIMESTAMPTZ
);

# --- !Downs

DROP TABLE "kicker"."user";
DROP TABLE "kicker"."kickerTable";
DROP SCHEMA "kicker";
