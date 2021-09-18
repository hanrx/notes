

# 敲定问题

## date20210916
- Header传递：【token】【userUid】两个属性。











# 脚本

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



# 接口验证
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

### 获取评论列表 
实例》》 http://localhost:8607/iafoot-web/web/comment/getList
请求：
```json5
{
  "blogUid": "11e0cace2148383e201439a682432d59",//图文UID
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

### 删除评论
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

### 删除收藏
URL: http://localhost:8603/web/collect/delete
请求：
```json5
{
  "uid": "36ca0b8074270e64dff0b20907d033cc",
  "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a"
}
```
响应：
```json5
{"data":"删除成功","code":"success"}
```

### 获取收藏列表
URL:http://localhost:8603/web/collect/getList
请求：
```json5
{
  "userUid": "b2833edb8f46db03a4c17b3ef7d9b85a",
  "currentPage": 1,
  "pageSize": 11
}
```
响应：
```json5
{
    "data": [
        {
            "oid": 122, 
            "title": "腾讯云测试", 
            "content": "<p><img src=\"https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/abc/11111.jpg\" alt=\"1631258046250.jpg\" /></p>
", 
            "blogSortUid": "a03d7290b1c04b6eaf46659661b47032", 
            "clickCount": 2, 
            "collectCount": 1, 
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
            "updateTime": "2021-09-18 14:33:02"
        }, 
        {
            "oid": 123, 
            "title": "腾讯存储图片1", 
            "content": "<p><img src=\"https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/blog/admin/jpeg/2021/9/10/1631261955793.jpeg\" alt=\"1631261955734.jpeg\" /><img src=\"https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/blog/admin/jpg/2021/9/10/1631261938459.jpg\" alt=\"1631261938390.jpg\" /></p>
", 
            "blogSortUid": "a03d7290b1c04b6eaf46659661b47032", 
            "clickCount": 2, 
            "collectCount": 3, 
            "fileUid": "https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/blog/admin/jpeg/2021/9/10/1631261924859.jpeg", 
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
                "https://photo-2021-1307223178.cos.ap-beijing.myqcloud.com/blog/admin/jpeg/2021/9/10/1631261924859.jpeg"
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
            "uid": "b2f3ba18b73899f0a5ae67ab5184fe11", 
            "status": 1, 
            "createTime": "2021-09-10 16:19:57", 
            "updateTime": "2021-09-18 14:29:53"
        }
    ], 
    "code": "success"
}
```
















### 添加收藏















