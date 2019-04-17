/**
 * redis访问测试
 */
var redis = require('redis');
// var client = redis.createClient(6379,'localhost');
var client = redis.createClient('redis://:hello@localhost:6379/0')
client.set('hello','This is a value',(err,res)=>{
    client.get('hello',(err,res)=>{
        console.log('value:',res)
    })
});
