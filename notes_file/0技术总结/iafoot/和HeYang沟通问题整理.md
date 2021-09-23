

# 敲定问题

## date20210916
- Header传递：【token】【userUid】两个属性。











# 脚本
## 20210922
ALTER TABLE `mogu_blog`.`t_study_video`
ADD COLUMN `praise_count` int(255) NULL DEFAULT 0 COMMENT '图文点赞数' AFTER `comment_count`;


## 20210915
-- 未同步到何阳机器
-- ALTER TABLE `mogu_blog`.`t_blog`
-- ADD COLUMN `praise_count` int(255) NULL DEFAULT 0 COMMENT '图文点赞数' AFTER `article_source`;

## 20210916
-- ALTER TABLE `mogu_blog`.`t_blog`
-- ADD COLUMN `comment_count` int(255) NULL DEFAULT 0 COMMENT '图文评论数' AFTER `praise_count`;

## 20210918 
-- ALTER TABLE `mogu_blog`.`t_comment`
-- ADD COLUMN `video_uid` varchar(32) NULL COMMENT '视频UID' AFTER `blog_uid`;

ALTER TABLE `mogu_blog`.`t_study_video`
MODIFY COLUMN `click_count` int(0) NULL DEFAULT NULL COMMENT '点击数' AFTER `baidu_path`,
ADD COLUMN `collect_count` int(0) NULL COMMENT '图文收藏数' AFTER `is_publish`,
ADD COLUMN `comment_count` int(0) NULL COMMENT '评论数' AFTER `collect_count`;

ALTER TABLE `mogu_blog`.`t_study_video`
DROP COLUMN `click_count`,
DROP COLUMN `collect_count`,
DROP COLUMN `comment_count`,
ADD COLUMN `click_count` int(255) NULL DEFAULT 0  COMMENT '点击数' AFTER `baidu_path`,
ADD COLUMN `collect_count` int(255) NULL DEFAULT 0  COMMENT '图文收藏数' AFTER `click_count`,
ADD COLUMN `comment_count` int(255) NULL DEFAULT 0  COMMENT '评论数' AFTER `collect_count`;


# 接口验证

## 视频-点赞
### 通过Uid给 视频 点赞
实例》》http://localhost:8603/web/praise/praiseBlogByUid?uid=5cdb5b3f1efaa513918c364a625c6122
请求：http://localhost:8603/web/praise/praiseBlogByUid?uid=5cdb5b3f1efaa513918c364a625c6122
响应：
```json5
{"data":5,"code":"success"}
```
### 通过Uid获取 视频点赞数
实例》》http://localhost:8603/web/praise/getBlogPraiseCountByUid?uid=5cdb5b3f1efaa513918c364a625c6122
请求：http://localhost:8603/web/praise/getBlogPraiseCountByUid?uid=5cdb5b3f1efaa513918c364a625c6122
响应：
```json5
{"data":2,//点赞数
  "code":"success"}
```


## 评论
### 发表评论：接口
实例》》 http://localhost:8607/iafoot-web/web/comment/add
请求：
```json5
{
"blogUid": "11e0cace2148383e201439a682432d59",//图文UID
"source": "BLOG_APP",//写死，评论来源
"userUid": "471be7cd3dbc38ddcab879245f0a08fa",//用户UID
"content": "哈哈哈2"//评论内容。有字数限制，目前1024
}
```

响应：
```json5
{
"data": {
"userUid": "471be7cd3dbc38ddcab879245f0a08fa",
"content": "哈哈哈2",//评论内容。
"blogUid": "11e0cace2148383e201439a682432d59",
"source": "BLOG_INFO",
"user": {//用户信息，字段含义同之前。
"userName": "888889",
"passWord": "0a69742f8a81a2fe927d8476b3f0612f",
"nickName": "21",
"gender": "1",
"avatar": "b2833edb8f46db03a4c17b3ef7d9b85a",
"mobile": "888889",
"occupation": "12",
"loginCount": 0,
"source": "iAfoot",
"lastLoginTime": "2021-09-16 14:35:28",
"lastLoginIp": "0:0:0:0:0:0:0:1",
"commentStatus": 1,
"startEmailNotification": 0,
"os": "Windows",
"browser": "Chrome-93.0.4577.63",
"userTag": 2,
"photoUrl": "https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/blog/admin/jpg/2021/9/16/1631775173252.jpg",//头像
"uid": "471be7cd3dbc38ddcab879245f0a08fa",
"status": 1,
"createTime": "2021-09-14 18:26:38",
"updateTime": "2021-09-16 14:52:56"
},
"uid": "78221c3f3eee1e2df64b25182d610656",
"status": 1,
"createTime": "2021-09-16 14:53:17",
"updateTime": "2021-09-16 14:53:17"
},
"code": "success"
}
```

### 获取评论列表 （图文/视频）
实例》》 http://localhost:8607/iafoot-web/web/comment/getList
请求：
图文》》
```json5
{
  "blogUid": "11e0cace2148383e201439a682432d59",//图文UID
  "currentPage": 1,
  "pageSize": 10
}
```

视频》》
```json5
{
  "videoUid": "5cdb5b3f1efaa513918c364a625c6122", //视频UID
  "currentPage": 1,
  "pageSize": 10
}
```

响应：
```json5
{
  "data": {
    "records": [//评论内容记录
      {
        "userUid": "471be7cd3dbc38ddcab879245f0a08fa",
        "content": "哈哈哈2",//评论内容
        "blogUid": "11e0cace2148383e201439a682432d59",
        "source": "BLOG_INFO",
        "type": 0,
        "user": {//该条评论的用户信息
          "nickName": "21",
          "avatar": "b2833edb8f46db03a4c17b3ef7d9b85a",
          "userTag": 2,
          "photoUrl": "https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/blog/admin/jpg/2021/9/16/1631775173252.jpg",
          "uid": "471be7cd3dbc38ddcab879245f0a08fa",
          "status": 1,
          "createTime": "2021-09-16 15:38:49",
          "updateTime": "2021-09-16 15:38:49"
        },
        "replyList": [],
        "uid": "78221c3f3eee1e2df64b25182d610656",
        "status": 1,
        "createTime": "2021-09-16 14:53:17",
        "updateTime": "2021-09-16 14:53:17"
      },
      {
        "userUid": "471be7cd3dbc38ddcab879245f0a08fa",
        "content": "20210916评论一下",
        "blogUid": "11e0cace2148383e201439a682432d59",
        "source": "BLOG_INFO",
        "type": 0,
        "user": {
          "nickName": "21",
          "avatar": "b2833edb8f46db03a4c17b3ef7d9b85a",
          "userTag": 2,
          "photoUrl": "https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/blog/admin/jpg/2021/9/16/1631775173252.jpg",
          "uid": "471be7cd3dbc38ddcab879245f0a08fa",
          "status": 1,
          "createTime": "2021-09-16 15:38:49",
          "updateTime": "2021-09-16 15:38:49"
        },
        "replyList": [],
        "uid": "b896837b3e1db5756d3f3b1aea52f386",
        "status": 1,
        "createTime": "2021-09-16 14:39:04",
        "updateTime": "2021-09-16 14:39:04"
      },
      {
        "userUid": "6db32d46fd4d7a8bf130cdecf7f28e5a",
        "content": "测试12",
        "blogUid": "11e0cace2148383e201439a682432d59",
        "source": "BLOG_INFO",
        "type": 0,
        "user": {
          "nickName": "han12345",
          "avatar": "d1fb04ce80b113602b5a7e8782405353",
          "userTag": 1,
          "photoUrl": "https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/blog/admin/jpg/2021/9/13/1631495325922.jpg",
          "uid": "6db32d46fd4d7a8bf130cdecf7f28e5a",
          "status": 1,
          "createTime": "2021-09-16 15:38:49",
          "updateTime": "2021-09-16 15:38:49"
        },
        "replyList": [],
        "uid": "7b349e9d48f5baacb529bea7c5513f09",
        "status": 1,
        "createTime": "2021-08-16 16:19:09",
        "updateTime": "2021-08-16 16:19:09"
      }
    ],
    "total": 3,
    "size": 10,
    "current": 1,
    "orders": [],
    "optimizeCountSql": true,
    "isSearchCount": true
  },
  "code": "success"
}
```
视频;
```json5
{
    "data": {
        "records": [
            {
                "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a",
                "content": "视频评论测试",
                "videoUid": "5cdb5b3f1efaa513918c364a625c6122",
                "source": "BLOG_INFO",
                "type": 0,
                "replyList": [],
                "uid": "b8d934db54cc757676fd3a09891117f0",
                "status": 1,
                "createTime": "2021-09-22 14:04:55",
                "updateTime": "2021-09-22 14:04:55"
            },
            {
                "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a",
                "content": "视频评论测试",
                "videoUid": "5cdb5b3f1efaa513918c364a625c6122",
                "source": "BLOG_INFO",
                "type": 0,
                "replyList": [],
                "uid": "3637781d29042945526dfcd1703f19d2",
                "status": 1,
                "createTime": "2021-09-22 14:04:47",
                "updateTime": "2021-09-22 14:04:47"
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "isSearchCount": true
    },
    "code": "success"
}
```

### 删除评论（图文/视频）
实例》》 http://localhost:8607/iafoot-web/web/comment/delete
请求：
```json5
{
  "uid": "78221c3f3eee1e2df64b25182d610656",//评论UID
  "userUid": "471be7cd3dbc38ddcab879245f0a08fa"//用户Uid
}
```
响应：
```json5
{
  "data": "删除成功",
  "code": "success"
}
```

## 收藏

### 增加收藏
URL
入参：
```json5
{
  "source": "BLOG_INFO",//先 固定写死 代表收藏来源
  "blogUid": "b2f3ba18b73899f0a5ae67ab5184fe11",//图文UID
  "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a"//用户UID
}
```
响应：
```json5

{
  "data": {
    "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a",
    "blogUid": "b2f3ba18b73899f0a5ae67ab5184fe11",
    "source": "BLOG_INFO",
    "type": 2,
    "uid": "36ca0b8074270e64dff0b20907d033cc",
    "status": 1,
    "createTime": "2021-09-18 13:37:39",
    "updateTime": "2021-09-18 13:37:39"
  },
  "code": "success" //收藏成功。
}
```

### 图文/视频 删除收藏
URL: http://localhost:8603/web/collect/delete
请求：
```json5
{
  "uid": "36ca0b8074270e64dff0b20907d033cc",//收藏UID
  "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a"//用户UID
}
```
响应：
```json5
{"data":"删除成功","code":"success"}
```

### 获取收藏列表(图文和视频)
URL:http://localhost:8603/web/collect/getList
请求：
```json5
{
  "userUid": "698483167b4aefa4b16ea85292dce0e6",
  "currentPage": 1,
  "pageSize": 11
}
```
响应：
```json5
{
  "data": {
    "records": [
      {
        "oid": 122,
        "title": "腾讯云测试",
        "content": "<p><img src=\"https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/abc/11111.jpg\" alt=\"1631258046250.jpg\" /></p>\n",
        "blogSortUid": "a03d7290b1c04b6eaf46659661b47032",
        "clickCount": 2,
        "collectCount": 2,
        "fileUid": "https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/abc/11111.jpg",
        "adminUid": "1f01cd1d2f474743b241d74008b12333",
        "isPublish": "1",
        "isOriginal": "1",
        "author": "admin",
        "articlesPart": "iAfoot媒体平台",
        "level": 1,
        "sort": 0,
        "openComment": "1",
        "type": "0",
        "praiseCount": 0,
        "commentCount": 0,
        "areasCodeList": [
          "11"
        ],
        "photoList": [
          "https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/abc/11111.jpg"
        ],
        "blogSort": {
          "sortName": "公告",
          "content": "公告开发专题",
          "clickCount": 362,
          "sort": 16,
          "uid": "a03d7290b1c04b6eaf46659661b47032",
          "status": 1,
          "createTime": "2018-12-30 10:35:43",
          "updateTime": "2021-08-25 18:58:36"
        },
        "uid": "578bc9d11a0d40eeec653f4aadd8a585",
        "status": 1,
        "createTime": "2021-09-10 15:14:20",
        "updateTime": "2021-09-22 11:42:28"
      },
      {
        "uploadTime": "2021-09-18 17:06:44",
        "isPublish": "1",
        "name": "123",
        "baiduPath": "https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/blog/admin/mp4/2021/9/18/1631956348394.mp4",
        "resourceSortUid": "a442a4d5c4e07a9032af7de99f301de4",
        "clickCount": 0,
        "collectCount": 4,
        "commentCount": 0,
        "uid": "5cdb5b3f1efaa513918c364a625c6122",
        "status": 1,
        "createTime": "2021-09-18 17:13:02",
        "updateTime": "2021-09-22 11:36:40"
      }
    ],
    "total": 2,
    "size": 10,
    "current": 1,
    "orders": [],
    "optimizeCountSql": true,
    "isSearchCount": true
  },
  "code": "success"
}
```

## 收藏 - 视频
### 视频：增加收藏 
URI：http://localhost:8603/web/collect/video/add
请求：
```json5
{
  "source": "BLOG_INFO",//写死即可
  "videoUid": "5cdb5b3f1efaa513918c364a625c6122",//视频 UID
  "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a"//用户UID
}
```
响应：
```json5
{
  "data": {
    "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a",
    "videoUid": "5cdb5b3f1efaa513918c364a625c6122",
    "source": "BLOG_INFO",
    "type": 2,
    "uid": "27b5864c8c512532b01bf9a69de713d5",
    "status": 1,
    "createTime": "2021-09-18 17:21:25",
    "updateTime": "2021-09-18 17:21:25"
  },
  "code": "success"//成功
}
```
### 视频：删除收藏 参见URL: http://localhost:8603/web/collect/delete













### 添加收藏

## 评论-视频
### 视频》》添加评论
实例》》 http://localhost:8607/web/comment/video/add
请求：
```json5
{
  "source": "BLOG_INFO",//写死
  "videoUid": "62d0744ab83605ff86aebd3d83501eeb",//视频UID
  "content": "视频评论测试",//评论内容
  "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a"//用户UID
}
```
响应：
```json5
{
    "data": {
        "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a",
        "content": "视频评论测试",
        "videoUid": "62d0744ab83605ff86aebd3d83501eeb",
        "source": "BLOG_INFO",
        "user": {
            "userName": "99999",
            "passWord": "d3eb9a9233e52948740d7eb8c3062d14",
            "mobile": "99999",
            "loginCount": 0,
            "source": "iAfoot",
            "lastLoginTime": "2021-09-22 09:33:26",
            "lastLoginIp": "192.168.20.188",
            "commentStatus": 1,
            "startEmailNotification": 0,
            "os": "Windows",
            "browser": "Chrome-93.0.4577.82",
            "userTag": 0,
            "uid": "698483167b4aefa4b16ea85292dce0e6",
            "status": 1,
            "createTime": "2021-09-22 09:25:15",
            "updateTime": "2021-09-22 09:33:26"
        },
        "uid": "27e7098c00c4a0b67961850ed5fb0893",
        "status": 1,
        "createTime": "2021-09-22 14:06:05",
        "updateTime": "2021-09-22 14:06:05"
    },
    "code": "success"
}
```

### 是否点赞过 是否收藏过(图文/视频)
实例》》http://localhost:8603/web/collect/getComment?userUid=b2833edb8f46db03a4c17b3ef7d9b85a&blogUid=b2f3ba18b73899f0a5ae67ab5184fe11&videoUid=5cdb5b3f1efaa513918c364a625c6122
参数说明：
- userUid：不可为空，用户UID。
- blogUid【图文UID】和videoUid【视频UID】：不可同时传参。
请求：
实例1：查询图文 点赞收藏情况》》http://localhost:8603/web/collect/getComment?userUid=b2833edb8f46db03a4c17b3ef7d9b85a&blogUid=b2f3ba18b73899f0a5ae67ab5184fe11

实例2：查询视频 点赞收藏情况》》http://localhost:8603/web/collect/getComment?userUid=b2833edb8f46db03a4c17b3ef7d9b85a&videoUid=5cdb5b3f1efaa513918c364a625c6122

响应：实例1的响应
```json5
{
    "data": [
        {
            "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a",
            "blogUid": "578bc9d11a0d40eeec653f4aadd8a585",
            "source": "BLOG_INFO",
            "type": 2,//点赞1、收藏2
            "uid": "d84d4191377c50ca4692ec8c8ec67c4d",
            "status": 1,
            "createTime": "2021-09-18 14:33:02",
            "updateTime": "2021-09-18 14:33:02"
        }
    ],
    "code": "success"
}
```







## 登录管理相关接口
### 用户注册
URI：/login/register
入参：
```json5
{
  "mobile": "99999",//手机号
  "passWord": "99999",//密码 暂时明文，后面要改成MD5加密
  "userName": "99999",//用户名，和手机号保持一致
  "validation": "12345678"//验证码
}
```
响应：
```json5
{
  "data": {
    "user": {//返回注册用户信息
      "userName": "99999",
      "passWord": "",//密码返回数据置空处理，防止密码泄露
      "mobile": "99999",
      "source": "iAfoot",
      "lastLoginIp": "192.168.20.188",
      "os": "Windows",
      "browser": "Chrome-93.0.4577.82",
      "uid": "698483167b4aefa4b16ea85292dce0e6",//用户UID
      "status": 1,
      "createTime": "2021-09-22 09:25:15",
      "updateTime": "2021-09-22 09:25:15"
    }
  },
  "code": "success"
}
```



### 获取验证码：接口暂未实现，返回假数据
URI: /login/validation
入参：手机号
```json5
curl -X POST "http://localhost:8603/login/validation?mobile=99999" -H "accept: */*" -d ""
```
响应：
```json5
{
  "data": "12345678",//验证码信息
  "code": "success"
}
```

### 用户登录
URI: /login/login
入参：、
验证码形式》
```json5

{
"userName": "99999",//用户名，目前为注册用户手机号
"validation": "12345678"//验证码，必须提前调用获取验证码接口获取
}
```
密码形式》
```json5
{
  "passWord": "99999",
  "userName": "99999"
}
```
响应：
```json5
{
  "data": {
    "user": {//用户信息
      "userName": "99999",
      "passWord": "",
      "mobile": "99999",
      "loginCount": 0,
      "source": "iAfoot",
      "lastLoginTime": "2021-09-22 09:33:25",
      "lastLoginIp": "192.168.20.188",
      "commentStatus": 1,
      "startEmailNotification": 0,
      "os": "Windows",
      "browser": "Chrome-93.0.4577.82",
      "userTag": 0,
      "uid": "698483167b4aefa4b16ea85292dce0e6",
      "status": 1,
      "createTime": "2021-09-22 09:25:15",
      "updateTime": "2021-09-22 09:33:25"
    },
    "token": "cf861b61eef249da9d95100dada9d355"//用户登录唯一标识token
  },
  "code": "success"
}
```















