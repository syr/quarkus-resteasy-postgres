CREATE TABLE  if not exists  Download
(
    id          BIGSERIAL       NOT NULL,
    finished    BOOLEAN,
    CONSTRAINT pk_download PRIMARY KEY (id)
);