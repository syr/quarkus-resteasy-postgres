CREATE TABLE  if not exists  FilePart
(
    id               BIGSERIAL NOT NULL,
    idProperty1      VARCHAR(255),
    idProperty2      VARCHAR(255),
    filePartFilePath VARCHAR(255)
);
create sequence if not exists filepart_id_seq INCREMENT 1;

INSERT INTO public.filepart (idproperty1, idproperty2, filepartfilepath) VALUES ('id1', 'id2', 'path1');
INSERT INTO public.filepart (idproperty1, idproperty2, filepartfilepath) VALUES ('id11', 'id22', 'path11');
