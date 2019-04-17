/**
 * redis访问测试
 */
var redis = require('redis');
var client = redis.createClient(6379,'localhost');
// 如果redis设置了密码，需要验证
client.auth('hello')
// 也可如下通过uri创建client实例
//var client = redis.createClient('redis://:hello@localhost:6379/0')

// 写入key-value
client.set('hello','This is a value')

// 读取value
client.get('hello',(err,res)=>{
    console.log('value:',res)
})
