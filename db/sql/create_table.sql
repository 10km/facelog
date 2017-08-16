DROP TABLE IF EXISTS att_user ;
CREATE TABLE att_user (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(30) DEFAULT NULL,
  `userno` varchar(20) DEFAULT NULL,
  `isonjob` tinyint(4) DEFAULT NULL,
  `colorlen` int(11) DEFAULT NULL,
  `graylen` int(11) DEFAULT NULL,
  `updatetime` timestamp(3)NULL DEFAULT NULL,
  `permission` int(11) DEFAULT NULL,
  `colorimage` blob,
  `grayimage` blob,
  PRIMARY KEY (`id`)
) ;

DROP TABLE IF EXISTS log_device ;
CREATE TABLE log_device (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `graylen` int(11) DEFAULT NULL,
  `updatetime` timestamp(3)NULL DEFAULT NULL,
  `version` varchar(38) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;