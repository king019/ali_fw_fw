package com.apache.thrift.struct.java.dep.fw.impl;
import com.apache.thrift.struct.java.dep.fw.StudentFWService;
import com.apache.thrift.struct.java.dep.vo.Student;
public class StudentFWServiceImpl implements StudentFWService.Iface {
  @Override
  public Student structck(Student stu) {
    try {
      // Thread.sleep(100000);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return stu;
  }
}
