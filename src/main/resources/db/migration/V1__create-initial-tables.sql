/**
 * @author mitch-warrenburg
 */

CREATE TYPE attraction_type AS ENUM ('ATTRACTION', 'RESTAURANT');
CREATE TYPE attraction_status AS ENUM ('Down', 'Closed', 'Operating', 'Refurbishment');
CREATE TABLE wait_time_notification
(
    id           SERIAL PRIMARY KEY,
    name         TEXT   UNIQUE,
    status       attraction_status,
    type         attraction_type,
    wait_time    SMALLINT           DEFAULT 0,
    active       BOOLEAN            DEFAULT FALSE,
    fast_pass    BOOLEAN            DEFAULT FALSE,
    single_rider BOOLEAN            DEFAULT FALSE,
    last_update  TIMESTAMP,
    created_ts   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_ts  TIMESTAMP
);

CREATE UNIQUE INDEX attraction_name_index ON wait_time_notification (name);