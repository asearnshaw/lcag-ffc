CREATE TABLE IF NOT EXISTS `i7b0_ffc_contributions` (
  `id` int(10) unsigned AUTO_INCREMENT NOT NULL,
  `user_id` int(10) unsigned,
  `username` varchar(120),
  `hash` varchar(120),
  `membership_token` varchar(120),
  `first_name` varchar(120),
  `last_name` varchar(120),
  `email_address` varchar(120),
  `amount` decimal(13,2) NOT NULL,
  `date` int(10) unsigned NOT NULL DEFAULT 0,
  `stripe_token` varchar(120) NOT NULL default '',
  `reference` varchar(120) NOT NULL default '',
  `status` varchar(120) NOT NULL default '',
  `type` varchar(20) NOT NULL DEFAULT 0,
  `email_sent` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;