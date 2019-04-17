/**
 * node.js调用facelog接口示例
 * @author guyadong
 */
var thrift = require('thrift');

var IFacelog = require('./IFaceLog.js'),
ttypes = require('./IFaceLog_types.js');

// 创建网络连接
var connection = thrift.createConnection('localhost', 26411),

// 创建facelog服务client端实例
client = thrift.createClient(IFacelog, connection);

connection.on('error', function(err) {
  console.error(err);
});
///////////// 接口调用示例 ////////////////
// 调用version接口 
client.version(function(err, res) {
  if (err) {
    console.error(err);
  } else {
    console.log('fcelog version',res)
  }
})
// 调用loadAllPerson接口,获取所有用户ID
client.loadAllPerson(function(err, res) {
    if (err) {
      console.error(err);
    } else {
        // 调用getPersons接口获取所有用户信息
        client.getPersons(res,function(err,res){
          res.forEach(element => {
            console.log(element)
          });
      });
    }
  })

// 调用令牌申请接口
client.applyRootToken('guyadong',false,function(err,res){
    if (err) {
        console.error(err);
    } else {
        console.log('apply token success:',res)
    }
})
