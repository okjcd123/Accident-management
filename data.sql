CREATE TABLE AccidentCase (
cscode int NOT NULL AUTO_INCREMENT,
province varchar(11),
town varchar(11),
year varchar(11),
month varchar(11),
day varchar(10),
dead int(4),
injured int(4),
actype varchar(11),
latitude double,
longitude double,
PRIMARY KEY (cscode)
);

