begin transaction;

drop table if exists cell;
drop table if exists calendar_date;
drop table if exists calendar;

CREATE TABLE calendar (
	id varchar(32) NOT NULL,
	name varchar(50) NOT NULL,
	description varchar(500) NULL,

	CONSTRAINT pk_calendar_id PRIMARY KEY (id),
	CONSTRAINT uq_name UNIQUE (name)
);

create table calendar_date (
	id varchar(32) NOT NULL,
	calendar_id varchar(32) NOT NULL,
	event_date date NOT NULL,

	CONSTRAINT pk_calendar_date_id PRIMARY KEY (id)
);

create table cell (
	id varchar(32) NOT NULL,
	calendar_date_id varchar(32) NOT NULL,
	text varchar(50) NULL,
	color varchar(16) NULL,
	background_color varchar(16) NULL,
	is_bold boolean NOT NULL DEFAULT false,
	sort_order integer NULL,

	CONSTRAINT pk_cell_id PRIMARY KEY (id)
);

commit;