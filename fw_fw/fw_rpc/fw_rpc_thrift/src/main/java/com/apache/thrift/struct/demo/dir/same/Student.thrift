namespace java com.apache.thrift.struct.java.dep.vo
include "StudentEnum.thrift"
struct  Student {
#boolckcomment
  1:bool boolck;
  2:byte byteck;
  3:i16 i16ck;
  4:i32 i32ck;
  5:i64 i64ck;
  6:double doubleck;
  7:string stringck;

  8:list<Student> liststu;
  9:set<Student>setstu;
  10:map<string,Student>mapstu;

11:StudentEnum.StudentEnum enumck;
//const i32 INT_CONST=1234;
}
