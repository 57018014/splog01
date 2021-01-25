DROP TABLE IF EXISTS `pb_user_operator_log`;
CREATE TABLE `pb_user_operator_log` (
  `id` bigint(100) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(100) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `interface_uri` varchar(200) DEFAULT NULL COMMENT '接口地址',
  `interface_comment` varchar(100) DEFAULT NULL COMMENT '接口操作信息',
  `request_args` varchar(500) DEFAULT NULL COMMENT '参数值如下name:1,age:10',
  `sqls` varchar(500) DEFAULT NULL COMMENT '当前接口执行的sql',
  `operator_time` varchar(50) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
