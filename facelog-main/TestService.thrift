namespace java.swift net.gdface.facelog
namespace py gdface.thrift
namespace java com.gdface
namespace cpp gdface

include "TestBean.thrift"



service TestService {
  void fooOne(1:  binary arg0);
  TestBean.TestBean getBean();
  void setBean(1:  TestBean.TestBean arg0);
}
