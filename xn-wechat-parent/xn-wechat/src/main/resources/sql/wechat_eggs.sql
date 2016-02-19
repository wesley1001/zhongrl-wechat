ALTER TABLE t_activity_fans_scanrecord ADD activicty_code varchar(10);
ALTER TABLE t_activity_fans_scanrecord ADD eggs_user_id int(11);
ALTER TABLE t_activity_fans_scanrecord ADD user_type varchar(10);

ALTER TABLE t_activity_fans_changerecord ADD eggs_user_id int(11);

ALTER TABLE t_activity_fans_qrcode ADD eggs_user_id int(11);
ALTER TABLE t_activity_fans_qrcode change url qrCodeUrl varchar(200);
ALTER TABLE t_activity_fans_qrcode DROP openid;
ALTER TABLE t_activity_fans_qrcode DROP unionid;
ALTER TABLE t_activity_fans_qrcode change createDate startDate datetime;
ALTER TABLE t_activity_fans_prizeinfo ADD max_material int(5)  DEFAULT 1 COMMENT '每人最多兑换个数';


CREATE TABLE `t_activity_eggs_userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(500) DEFAULT NULL COMMENT '用户昵称',
  `unionid` varchar(100) DEFAULT NULL COMMENT '微信unionid',
  `openid` varchar(100) DEFAULT NULL COMMENT '微信openid',
  `headimgurl` varchar(200) DEFAULT NULL COMMENT '图像url',
  `referee_id` int(11) DEFAULT NULL COMMENT '推荐人Id',
  `channel` varchar(40) DEFAULT NULL COMMENT '渠道:wechat',
  `mobile` varchar(15) DEFAULT NULL COMMENT '用户手机号码',
  `material` int(11) DEFAULT NULL COMMENT '原料,金币',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `source` varchar(40) DEFAULT NULL COMMENT '来源:eggs',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `status` int(11) DEFAULT NULL COMMENT '用户状态1:可用，0禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid_unique_idx` (`openid`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='金蛋用户信息表';