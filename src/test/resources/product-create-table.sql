CREATE TABLE `product` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NOM` varchar(46) NOT NULL,
  `PRIX` bigint(80) DEFAULT NULL,
  `PRIX_ACHAT` bigint(80) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);
