CREATE TABLE IF NOT EXISTS "cloth"
(
    "id"         varchar PRIMARY KEY,
    "full_name"  varchar,
    "created_at" timestamp,
    "color"      varchar,
    "brand"      varchar,
    "size"       varchar,
    "is_sold"    boolean,
    "price"      int
);

CREATE TABLE IF NOT EXISTS "brand"
(
    "id"   varchar PRIMARY KEY,
    "name" varchar
);

CREATE TABLE IF NOT EXISTS "size"
(
    "id"   varchar PRIMARY KEY,
    "name" varchar,
    "size" int
);

CREATE TABLE IF NOT EXISTS "distributor"
(
    "id"    varchar PRIMARY KEY,
    "name"  varchar,
    "brand" varchar
);

CREATE TABLE IF NOT EXISTS "color"
(
    "id"           varchar PRIMARY KEY,
    "name"         varchar,
    "red"          int,
    "green"        int,
    "blue"         int,
    "transparency" int
);

ALTER TABLE "cloth"
    ADD FOREIGN KEY ("color") REFERENCES "color" ("id");

ALTER TABLE "cloth"
    ADD FOREIGN KEY ("brand") REFERENCES "brand" ("id");

ALTER TABLE "cloth"
    ADD FOREIGN KEY ("size") REFERENCES "size" ("id");

ALTER TABLE "distributor"
    ADD FOREIGN KEY ("brand") REFERENCES "brand" ("id");