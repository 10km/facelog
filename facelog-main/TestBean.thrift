namespace java.swift net.gdface.facelog
namespace py gdface.thrift
namespace java com.gdface
namespace cpp gdface



struct TestBean1 {
  1:  binary blob;
  2:  i32 id;
}

struct TestBean2 {
  1:  binary blob;
}

struct DeviceBean {
  1:  bool new;
  2:  i64 modified;
  3:  i64 initialized;
  4:  i32 id;
  5:  string name;
  6:  i32 groupId;
  7:  string version;
  8:  string serialNo;
  9:  string mac;
  10:  i64 createTime;
  11:  i64 updateTime;
}

service TestService {
  void fooOne(1:  binary arg0);
  TestBean1 getBean();
  TestBean2 getBean2();
  DeviceBean getBean3();
  void setBean(1:  TestBean1 arg0);
  i64 testLong(1:  string arg0, 2:  i32 arg1);
}
