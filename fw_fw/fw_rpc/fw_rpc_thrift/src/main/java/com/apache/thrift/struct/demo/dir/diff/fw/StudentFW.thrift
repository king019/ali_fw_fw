namespace java com.apache.thrift.struct.java.fw
include "../vo/Student.thrift"
service  HelloWorldService {
  Student.Student structck(1:Student.Student stu)
}
