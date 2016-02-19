# Host: 10.10.16.221  (Version: 5.6.23-log)
# Date: 2016-02-19 10:55:20
# Generator: MySQL-Front 5.3  (Build 4.214)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "sys_menu"
#

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `parentId` int(5) DEFAULT NULL,
  `text` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `permisCode` varchar(20) DEFAULT NULL,
  `enable` tinyint(1) NOT NULL,
  `systemCode` varchar(20) DEFAULT NULL,
  `menuOrder` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "sys_permission"
#

DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `permis_name` varchar(20) NOT NULL,
  `permis_code` varchar(20) NOT NULL,
  `parentCode` varchar(20) DEFAULT NULL,
  `parentPermisId` int(8) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_Id` int(8) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `update_user_Id` int(10) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "sys_role"
#

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `createId` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateId` int(11) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `systemCode` varchar(20) DEFAULT NULL,
  `defaultFlag` tinyint(1) NOT NULL,
  `permisNames` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "sys_role_permis"
#

DROP TABLE IF EXISTS `sys_role_permis`;
CREATE TABLE `sys_role_permis` (
  `role_id` int(11) NOT NULL DEFAULT '0',
  `permis_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`permis_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "sys_user"
#

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `userName` varchar(10) NOT NULL,
  `password` varchar(40) NOT NULL,
  `realName` varchar(30) NOT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `createId` int(8) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateId` int(8) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `isTipsRead` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "t_activity"
#

DROP TABLE IF EXISTS `t_activity`;
CREATE TABLE `t_activity` (
  `id` varchar(36) NOT NULL,
  `activity_name` varchar(36) DEFAULT NULL COMMENT '活动名称',
  `start_date` datetime DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间',
  `productIds` varchar(1000) DEFAULT NULL COMMENT '产品Id,多个用逗号分隔',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `status` varchar(36) DEFAULT NULL COMMENT '活动状态(new=新建|executing=执行中|pause=暂停|finished=已完成|discarded=已作废)',
  `activity_type` varchar(255) DEFAULT NULL COMMENT '活动类型',
  `activity_rule_desc` text COMMENT '活动规则描述',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_activitytype_idx` (`activity_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "t_activity_eggs_changercord"
#

DROP TABLE IF EXISTS `t_activity_eggs_changercord`;
CREATE TABLE `t_activity_eggs_changercord` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `orderNmuber` varchar(60) DEFAULT NULL COMMENT '订单编号',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `carrieroperator` varchar(30) DEFAULT NULL COMMENT '运营商',
  `orderNoLM` varchar(60) DEFAULT NULL COMMENT '流米单号',
  `satats` varchar(10) DEFAULT NULL COMMENT '发送流量状态',
  `goldenEggsNumber` int(11) DEFAULT NULL COMMENT '消耗兑换金蛋',
  `createDate` varchar(30) DEFAULT NULL COMMENT '创建时间',
  `eggsId` int(11) DEFAULT NULL COMMENT '用户ID',
  `updateDate` varchar(30) DEFAULT NULL COMMENT '修改时间',
  `prizeId` varchar(100) DEFAULT NULL COMMENT '奖品ID',
  `flowValue` varchar(100) DEFAULT NULL COMMENT '流量包值',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='流米兑换记录';

#
# Structure for table "t_activity_eggs_userinfo"
#

DROP TABLE IF EXISTS `t_activity_eggs_userinfo`;
CREATE TABLE `t_activity_eggs_userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(500) DEFAULT NULL COMMENT '昵称',
  `unionid` varchar(100) DEFAULT NULL,
  `openid` varchar(100) DEFAULT NULL,
  `headimgurl` varchar(200) DEFAULT NULL COMMENT '图像url',
  `referee_id` int(11) DEFAULT NULL COMMENT '推荐人Id',
  `channel` varchar(40) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `material` int(11) DEFAULT '0' COMMENT '原料',
  `create_date` datetime DEFAULT NULL,
  `source` varchar(40) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '用户状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid_unique_idx` (`openid`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='金蛋用户信息表';

#
# Structure for table "t_activity_fans_changerecord"
#

DROP TABLE IF EXISTS `t_activity_fans_changerecord`;
CREATE TABLE `t_activity_fans_changerecord` (
  `id` varchar(36) NOT NULL COMMENT '主键自动生成',
  `prize_Id` varchar(36) DEFAULT NULL COMMENT '产品Id',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户Id',
  `change_date` datetime DEFAULT NULL COMMENT '兑换时间',
  `status` varchar(30) DEFAULT NULL COMMENT '订单状态(Apply申请兑换，SUCCESS兑换成功，Fail兑换失败)',
  `address` varchar(500) DEFAULT NULL COMMENT '实物为收货地址',
  `prizeName` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `material` int(255) DEFAULT NULL COMMENT '原料',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `isVirtual` int(255) DEFAULT NULL COMMENT '1实物物品，2虚拟物品',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `mobile` varchar(255) DEFAULT NULL COMMENT '虚拟产品才有值',
  `username` varchar(255) DEFAULT NULL COMMENT '用户姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='兑换记录';

#
# Structure for table "t_activity_fans_prizeinfo"
#

DROP TABLE IF EXISTS `t_activity_fans_prizeinfo`;
CREATE TABLE `t_activity_fans_prizeinfo` (
  `id` varchar(36) NOT NULL,
  `prize_name` varchar(50) DEFAULT NULL COMMENT '产品名称',
  `buy_total` int(10) DEFAULT NULL COMMENT '产品总数',
  `buy_material` int(10) DEFAULT NULL COMMENT '兑换所需原料',
  `buyed_total` int(10) DEFAULT NULL COMMENT '已兑换总数',
  `show_index` int(10) DEFAULT NULL COMMENT '排序字段',
  `image_url` varchar(1000) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `prize_description` varchar(255) DEFAULT NULL COMMENT '产品描述',
  `status` int(10) DEFAULT NULL COMMENT '产品状态(1=待审核(数据初始化)|2=审核通过(上架)|3=已售完,4=删除',
  `typeId` varchar(36) DEFAULT NULL COMMENT '产品类型ID',
  `isVirtual` int(11) DEFAULT NULL COMMENT '1实物，2虚物',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='礼品信息表';

#
# Structure for table "t_activity_fans_qrcode"
#

DROP TABLE IF EXISTS `t_activity_fans_qrcode`;
CREATE TABLE `t_activity_fans_qrcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(8) NOT NULL,
  `qrCodeUrl` varchar(1000) NOT NULL DEFAULT '' COMMENT '二维码url',
  `startDate` datetime DEFAULT NULL COMMENT '开始时间',
  `endDate` datetime DEFAULT NULL COMMENT '二维码结束日期',
  `activityType` varchar(10) NOT NULL COMMENT '活动编码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `qrcode_userId_index` (`userId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户二维码信息表';

#
# Structure for table "t_activity_fans_scanrecord"
#

DROP TABLE IF EXISTS `t_activity_fans_scanrecord`;
CREATE TABLE `t_activity_fans_scanrecord` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_id` int(8) DEFAULT NULL COMMENT '邀请者Id',
  `openid` varchar(50) DEFAULT NULL COMMENT '被邀请者的openid',
  `material` int(8) DEFAULT NULL COMMENT '用户原料',
  `create_date` datetime DEFAULT NULL COMMENT '扫码时间',
  `activicty_code` varchar(10) DEFAULT NULL COMMENT '活动编码',
  `user_type` varchar(10) DEFAULT NULL COMMENT 'NEW新用户 NO老用户',
  `eggs_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='扫码记录';

#
# Structure for table "t_activity_fans_ticket_mzw"
#

DROP TABLE IF EXISTS `t_activity_fans_ticket_mzw`;
CREATE TABLE `t_activity_fans_ticket_mzw` (
  `F_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `F_INVESTID` varchar(36) DEFAULT NULL COMMENT '兑换记录Id',
  `F_TICKET` varchar(20) DEFAULT NULL COMMENT '电影票号码',
  `F_MOBILE` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `F_STATUS` varchar(8) DEFAULT NULL COMMENT '状态(UNSEND=未派送|SEND=已派送)',
  `F_INIT_DATE` datetime DEFAULT NULL COMMENT '初始时间',
  `F_UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `F_REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `F_USERNAME` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`F_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12013 DEFAULT CHARSET=utf8 COMMENT='电影票';

#
# Structure for table "t_activity_fans_userinfo"
#

DROP TABLE IF EXISTS `t_activity_fans_userinfo`;
CREATE TABLE `t_activity_fans_userinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userId` varchar(36) NOT NULL,
  `nickname` varchar(500) DEFAULT NULL COMMENT '昵称',
  `unionid` varchar(100) DEFAULT NULL,
  `openid` varchar(100) DEFAULT NULL,
  `headimgurl` varchar(200) DEFAULT NULL COMMENT '图像url',
  `referee_id` varchar(36) DEFAULT NULL COMMENT '推荐人Id',
  `channel` varchar(40) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `material` int(11) DEFAULT NULL COMMENT '原料',
  `create_date` datetime DEFAULT NULL,
  `source` varchar(40) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户信息表';

#
# Structure for table "t_eggs_ratetoken"
#

DROP TABLE IF EXISTS `t_eggs_ratetoken`;
CREATE TABLE `t_eggs_ratetoken` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(255) DEFAULT NULL,
  `createDate` varchar(30) DEFAULT NULL,
  `updateTime` varchar(30) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='token 表';

#
# Structure for table "t_im_token"
#

DROP TABLE IF EXISTS `t_im_token`;
CREATE TABLE `t_im_token` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(1000) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='环信客服token';

#
# Structure for table "t_keyword"
#

DROP TABLE IF EXISTS `t_keyword`;
CREATE TABLE `t_keyword` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(30) DEFAULT NULL COMMENT '类型',
  `keyName` text COMMENT '关键词',
  `Title` varchar(255) DEFAULT NULL COMMENT '标题',
  `Description` text COMMENT '描述',
  `PicUrl` varchar(1000) DEFAULT NULL COMMENT '图片URL',
  `Url` varchar(1000) DEFAULT NULL COMMENT '图文URL',
  `text` text COMMENT '文本',
  `wechatName` varchar(255) DEFAULT NULL COMMENT '微信名称',
  `createTime` varchar(255) DEFAULT NULL COMMENT '时间',
  `imageName` varchar(100) DEFAULT NULL COMMENT '图片名字',
  `mediaId` varchar(100) DEFAULT NULL,
  `times` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

#
# Structure for table "t_lm_api_log"
#

DROP TABLE IF EXISTS `t_lm_api_log`;
CREATE TABLE `t_lm_api_log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `orderNumber` varchar(100) DEFAULT NULL COMMENT '订单编号',
  `param` text COMMENT '请求参数',
  `result` text,
  `url` varchar(500) DEFAULT NULL,
  `createDate` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=514 DEFAULT CHARSET=utf8 COMMENT='流米接口日志';

#
# Structure for table "t_lm_number"
#

DROP TABLE IF EXISTS `t_lm_number`;
CREATE TABLE `t_lm_number` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` varchar(30) DEFAULT NULL,
  `number` int(11) DEFAULT NULL COMMENT '总数，每兑换一次减去',
  `updateDate` varchar(30) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `totle` int(11) DEFAULT NULL COMMENT '总数',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='流量总数';

#
# Structure for table "t_material"
#

DROP TABLE IF EXISTS `t_material`;
CREATE TABLE `t_material` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `media_id` varchar(255) DEFAULT NULL COMMENT '素材ID',
  `update_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "t_wechat"
#

DROP TABLE IF EXISTS `t_wechat`;
CREATE TABLE `t_wechat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(100) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT '',
  `sex` varchar(10) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `headimgurl` varchar(1000) DEFAULT NULL,
  `unionid` varchar(255) DEFAULT NULL,
  `subscribe` varchar(100) DEFAULT NULL,
  `language` varchar(30) DEFAULT NULL,
  `province` varchar(80) DEFAULT NULL,
  `subscribe_time` varchar(100) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `groupid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22663 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='微信用户表';

#
# Structure for table "t_wechat_share_log"
#

DROP TABLE IF EXISTS `t_wechat_share_log`;
CREATE TABLE `t_wechat_share_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `userId` varchar(36) DEFAULT NULL COMMENT '分享者用户ID',
  `ipAddress` varchar(30) DEFAULT NULL COMMENT '分享者Ip地址',
  `createDate` datetime DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `activityType` varchar(5) DEFAULT NULL COMMENT '活动类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='分享日志';

#
# Structure for table "test_info"
#

DROP TABLE IF EXISTS `test_info`;
CREATE TABLE `test_info` (
  `nickname` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "wechat_click_number"
#

DROP TABLE IF EXISTS `wechat_click_number`;
CREATE TABLE `wechat_click_number` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `actualClickNumber` int(11) DEFAULT '0' COMMENT '真实点击次数',
  `showClickNumber` int(11) DEFAULT '0' COMMENT '展示的点击次数',
  `activityType` varchar(100) DEFAULT NULL COMMENT '活动类型: 1.GOODSOUND 好声音',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "wechat_config"
#

DROP TABLE IF EXISTS `wechat_config`;
CREATE TABLE `wechat_config` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket` text COMMENT 'js签名属性字段',
  `token` text COMMENT '微信的token',
  `type` varchar(255) DEFAULT NULL COMMENT 'ticket类型区分 微信获取这些字段值',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "wechat_details"
#

DROP TABLE IF EXISTS `wechat_details`;
CREATE TABLE `wechat_details` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` varchar(500) DEFAULT NULL COMMENT '微信appId',
  `appSecret` varchar(255) DEFAULT NULL COMMENT '微信密钥',
  `wechatName` varchar(100) DEFAULT NULL COMMENT '微信名称',
  `wechatType` varchar(50) DEFAULT NULL COMMENT '微信类型',
  `wecahtStatus` varchar(10) DEFAULT 'NO' COMMENT '微信启用状态',
  `createTime` varchar(30) DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `userId` int(11) DEFAULT NULL COMMENT '用户ID',
  `bound` varchar(100) DEFAULT NULL COMMENT '拥有者',
  `account` varchar(100) DEFAULT NULL COMMENT '账户',
  `accountPassword` varchar(100) DEFAULT NULL COMMENT '密码',
  `bindPhone` varchar(255) DEFAULT NULL COMMENT '绑定者手机号',
  `wechatAccount` varchar(255) DEFAULT NULL COMMENT '拥有者微信号',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "wechat_menu"
#

DROP TABLE IF EXISTS `wechat_menu`;
CREATE TABLE `wechat_menu` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL COMMENT '微信公众号',
  `json` text COMMENT 'json 字段',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='菜单';

#
# Structure for table "wechat_menu_tree"
#

DROP TABLE IF EXISTS `wechat_menu_tree`;
CREATE TABLE `wechat_menu_tree` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(20) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `parentId` int(255) DEFAULT NULL,
  `showIndex` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "wechat_push_message_log"
#

DROP TABLE IF EXISTS `wechat_push_message_log`;
CREATE TABLE `wechat_push_message_log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL COMMENT '用户ID',
  `type` varchar(20) DEFAULT NULL COMMENT '消息类型',
  `message` text COMMENT '消息主体',
  `mq_topic` varchar(50) DEFAULT NULL COMMENT '消息主题',
  `push_time` varchar(50) DEFAULT NULL COMMENT '发送时间',
  `openId` varchar(100) DEFAULT NULL,
  `unionId` varchar(150) DEFAULT NULL,
  `real_name` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `state` varchar(10) DEFAULT NULL COMMENT '发送状态',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='推送消息日志';

#
# Structure for table "wechat_qr_details"
#

DROP TABLE IF EXISTS `wechat_qr_details`;
CREATE TABLE `wechat_qr_details` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `FqrImageUrl` varchar(1200) DEFAULT NULL COMMENT '二维码地址',
  `ticket` varchar(1000) DEFAULT NULL,
  `channel` varchar(255) DEFAULT NULL COMMENT '渠道',
  `createDate` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='二维码信息';

#
# Structure for table "wechat_qrcode_event"
#

DROP TABLE IF EXISTS `wechat_qrcode_event`;
CREATE TABLE `wechat_qrcode_event` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ToUserName` varchar(255) DEFAULT NULL COMMENT '开发者微信号',
  `FromUserName` varchar(500) DEFAULT NULL COMMENT '发送方帐号（一个OpenID）',
  `CreateTime` varchar(255) DEFAULT NULL COMMENT '消息创建时间 （整型）',
  `MsgType` varchar(120) DEFAULT NULL COMMENT '消息类型，event',
  `Event` varchar(255) DEFAULT NULL COMMENT '事件类型，subscribe未关注,SCAN关注',
  `EventKey` varchar(255) DEFAULT NULL COMMENT '事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id',
  `Ticket` varchar(1000) DEFAULT NULL COMMENT '二维码的ticket，可用来换取二维码图片',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2158 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "wechat_replymessage"
#

DROP TABLE IF EXISTS `wechat_replymessage`;
CREATE TABLE `wechat_replymessage` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '公众号名称',
  `type` varchar(255) DEFAULT NULL COMMENT '消息类型',
  `channel` varchar(255) DEFAULT NULL COMMENT '渠道',
  `text` text COMMENT '文本消息',
  `Title` varchar(255) DEFAULT NULL COMMENT '标题',
  `Description` text COMMENT '描述',
  `PicUrl` varchar(1000) DEFAULT NULL COMMENT '图片的URL',
  `Url` varchar(1000) DEFAULT NULL COMMENT '图文URL',
  `createTime` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `mediaId` varchar(100) DEFAULT NULL COMMENT 'mediaId',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='自定义回复消息';

#
# Structure for table "wechat_test"
#

DROP TABLE IF EXISTS `wechat_test`;
CREATE TABLE `wechat_test` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Structure for table "wechat_user_info"
#

DROP TABLE IF EXISTS `wechat_user_info`;
CREATE TABLE `wechat_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户id',
  `user_login_name` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户 登录账号',
  `user_as` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户别名',
  `login_pwd` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '登录密码',
  `salt` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码盐',
  `payment_pwd` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付密码',
  `phone_number` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `user_type` char(5) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户类型',
  `status` int(11) DEFAULT '1' COMMENT '状态:(0/1,不可用/可用)',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `lase_logintime` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `is_certification` int(5) DEFAULT '0' COMMENT '是否实名认证(0/1)',
  `is_question` int(5) DEFAULT '0' COMMENT '是否设置密保问题(0/1)',
  `is_payment_pwd` int(5) DEFAULT '0' COMMENT '是否修改支付密码(0/1)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `indx_user_info_user_id` (`user_id`),
  UNIQUE KEY `uq_user_info_user_name` (`user_login_name`),
  UNIQUE KEY `uq_user_info_email` (`email`),
  UNIQUE KEY `uq_user_info_phone` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户信息';
