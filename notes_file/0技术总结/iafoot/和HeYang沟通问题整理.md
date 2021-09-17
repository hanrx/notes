

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

### 添加收藏















