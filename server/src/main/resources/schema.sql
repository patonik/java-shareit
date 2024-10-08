DROP TABLE IF EXISTS public.COMMENT;
DROP TABLE IF EXISTS public.BOOKING;
DROP TABLE IF EXISTS public.ITEM;
DROP TABLE IF EXISTS public.REQUEST;
DROP TABLE IF EXISTS public.USERS;

DROP SEQUENCE IF EXISTS USER_ID_SEQ;
CREATE SEQUENCE IF NOT EXISTS USER_ID_SEQ START WITH 1;
DROP SEQUENCE IF EXISTS ITEM_ID_SEQ;
CREATE SEQUENCE IF NOT EXISTS ITEM_ID_SEQ START WITH 1;
DROP SEQUENCE IF EXISTS BOOKING_ID_SEQ;
CREATE SEQUENCE IF NOT EXISTS BOOKING_ID_SEQ START WITH 1;
DROP SEQUENCE IF EXISTS COMMENT_ID_SEQ;
CREATE SEQUENCE IF NOT EXISTS COMMENT_ID_SEQ START WITH 1;
DROP SEQUENCE IF EXISTS REQ_ID_SEQ;
CREATE SEQUENCE IF NOT EXISTS REQ_ID_SEQ START WITH 1;

CREATE TABLE IF NOT EXISTS public.USERS
(
    ID    bigint                 NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT BY 1 ),
    NAME  character varying(128),
    EMAIL character varying(128) NOT NULL,
    CONSTRAINT USERS_pkey PRIMARY KEY (ID),
    CONSTRAINT EMAIL_UNIQUE UNIQUE (EMAIL)
);
CREATE TABLE IF NOT EXISTS public.request
(
    ID           bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT BY 1 ),
    DESCRIPTION  character varying(512),
    REQUESTER_ID bigint NOT NULL,
    CREATED      timestamp without time zone,
    CONSTRAINT request_pkey PRIMARY KEY (ID),
    CONSTRAINT REQ_USER_FK FOREIGN KEY (REQUESTER_ID)
        REFERENCES public.USERS (ID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS public.ITEM
(
    ID              bigint  NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT BY 1 ),
    NAME            character varying(256),
    DESCRIPTION     character varying(512),
    OWNER_ID        bigint  NOT NULL,
    LAST_BOOKING_ID bigint,
    NEXT_BOOKING_ID bigint,
    AVAILABLE       boolean NOT NULL,
    REQUEST_ID      bigint,
    CONSTRAINT ITEM_pkey PRIMARY KEY (ID),
    CONSTRAINT ITEM_USERS_FK FOREIGN KEY (OWNER_ID)
        REFERENCES public.USERS (ID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT ITEMS_REQUEST_FK FOREIGN KEY (REQUEST_ID)
        REFERENCES public.REQUEST (ID)
        ON UPDATE NO ACTION
        ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS public.booking
(
    id         bigint                NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT BY 1 ),
    start_date timestamp without time zone,
    end_date   timestamp without time zone,
    item_id    bigint                NOT NULL,
    booker_id  bigint                NOT NULL,
    status     character varying(64) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT item_fk FOREIGN KEY (item_id)
        REFERENCES public.item (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT user_fk FOREIGN KEY (booker_id)
        REFERENCES public.users (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS public.comment
(
    id        bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT BY 1 ),
    text      character varying(512),
    item_id   bigint,
    author_id bigint,
    created   timestamp without time zone,
    CONSTRAINT comment_pkey PRIMARY KEY (id),
    CONSTRAINT comment_item_fk FOREIGN KEY (item_id)
        REFERENCES public.item (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT comment_user_fk FOREIGN KEY (author_id)
        REFERENCES public.users (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
