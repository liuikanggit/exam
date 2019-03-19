CREATE TABLE `user` (
  `id` VARCHAR(32) NOT NULL COMMENT '用户id' PRIMARY KEY,
  `openid` VARCHAR(64) NOT NULL COMMENT 'openid',
  `name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `username` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '登录名',
  `password` VARCHAR(16) NOT NULL DEFAULT '123456' COMMENT '登录密码',
  `avatar` VARCHAR(256) NOT NULL DEFAULT '' COMMENT '用户头像',
  `type` TINYINT(1) NOT NULL COMMENT '用户类型',
  `sex` TINYINT(1) NOT NULL DEFAULT -1 COMMENT '性别',
  `nid` VARCHAR(32) NOT NULL DEFAULT -1 COMMENT '学号',
  `phone` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '联系方式',
  `user_desc` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '用户个人说明',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  UNIQUE KEY `openid2type` (`openid`,`type`),
  KEY `type` (`type`)
) ENGINE=InnoDB COMMENT '用户表';

CREATE TABLE `class`(
  `id` VARCHAR(6) NOT NULL COMMENT '班级id' PRIMARY KEY ,
  `create_id` VARCHAR(32) NOT NULL COMMENT '创建人',
  `name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '班级名称',
  `avatar` VARCHAR(256) NOT NULL DEFAULT '' COMMENT '班级头像',
  `password` VARCHAR(16) NOT NULL DEFAULT '' COMMENT '密码',
  `grade` TINYINT(2) NOT NULL DEFAULT 0 COMMENT '年级',
  `class_desc` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '班级说明',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  foreign key (`create_id`) references user(id) on delete cascade on update cascade
) ENGINE=InnoDB COMMENT '班级表';

CREATE TABLE `user2class` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键' ,
  `user_id` VARCHAR(32) NOT NULL COMMENT '用户id',
  `user_type` TINYINT(1) NOT NULL COMMENT '用户类型',
  `class_id` VARCHAR(32) NOT NULL COMMENT '班级id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  foreign key (`user_id`) references user(id) on delete cascade on update cascade,
  foreign key (`class_id`) references class(id) on delete cascade on update cascade,
  UNIQUE KEY `user_id2class_id` (`user_id`,`class_id`),
  KEY `user_type` (`user_type`)
) ENGINE=InnoDB COMMENT '用户对应班级表';

CREATE TABLE `subject` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  `name` VARCHAR(32) NOT NULL COMMENT '科目名称',
  `icon` VARCHAR(256) COMMENT '科目图标',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB COMMENT  '科目';

CREATE TABLE `question` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  `subject_id` INT NOT NULL COMMENT '科目id',
  `creator_id` VARCHAR(32) NOT NULL COMMENT '创建人',
  `grade` TINYINT(3) NOT NULL DEFAULT 0 COMMENT '年级代码',
  `type` TINYINT(1) NOT NULL COMMENT '题目类型 0:选择题 1:判断题 2:填空题',
  `title` VARCHAR(256) NOT NULL DEFAULT "" COMMENT '题目标题',
  `title_image` VARCHAR(512) comment '标题的图片',
  `answer_0` VARCHAR(256) NOT NULL DEFAULT "" COMMENT '正确答案',
  `answer_image_0` VARCHAR(256) comment '正确答案的图片',
  `answer_1` VARCHAR(256)  DEFAULT "" COMMENT '错误答案1',
  `answer_image_1` VARCHAR(256) comment '错误答案1图片',
  `answer_2` VARCHAR(256)  DEFAULT "" COMMENT '错误答案2',
  `answer_image_2` VARCHAR(256) comment '错误答案2图片',
  `answer_3` VARCHAR(256)  DEFAULT "" COMMENT '错误答案3',
  `answer_image_3` VARCHAR(256) comment '错误答案3图片',
  `analysis` varchar(256) comment '题目解析',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  foreign key (`subject_id`) references subject(id) on delete cascade on update cascade,
  foreign key (`create_id`) references user(id) on delete cascade on update cascade,
  KEY `subject_id` (`subject_id`),
  KEY `create_id` (`create_id`),
  FULLTEXT(`title`)
) ENGINE=InnoDB COMMENT '题目';

