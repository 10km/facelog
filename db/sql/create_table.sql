CREATE TABLE `att_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(30) DEFAULT NULL,
  `userno` varchar(20) DEFAULT NULL,
  `isonjob` tinyint(4) DEFAULT NULL,
  `colorlen` int(11) DEFAULT NULL,
  `graylen` int(11) DEFAULT NULL,
  `updatetime` varchar(38) DEFAULT NULL,
  `permission` int(11) DEFAULT NULL,
  `colorimage` blob,
  `grayimage` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;