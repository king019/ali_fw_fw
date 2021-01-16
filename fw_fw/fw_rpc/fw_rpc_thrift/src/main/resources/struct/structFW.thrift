namespace java com.micmiu.thrift.demo
include "struct.thrift"
service  HelloWorldService {
  Student structck(1:Student stu)
}
