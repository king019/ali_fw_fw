namespace java com.apache.thrift.struct.java.dep.fw
include "Student.thrift"
service  StudentFWService {

  Student.Student structck(1:Student.Student stu)
}
