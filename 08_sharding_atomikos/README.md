# 说明

使用put接口，更新分库分表的逻辑，必须使用XA的事务，否则回滚不生效，以下是测试用例：
当userId为单数时，程序会抛异常，此时如果入参有多个，并且存在偶数执行成功的sql时，异常回滚，会全部回滚。
将事务类型改为LOCAL，不会回滚。


### 更新接口
PUT http://localhost:8080/order/xa
Content-Type: application/json

{
"infos": [
{
"id": 616050156294373377,
"userId": 8900,
"orderNo": "xa-bbbbb"
},
{
"id": 616049943945150464,
"userId": 8899,
"orderNo": "xa-ccccc"
}
]
}



### 查询接口
GET http://localhost:8080/order?id=616050156294373377

### 查询接口
GET http://localhost:8080/order?id=616049943945150464

