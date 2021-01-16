package io.grpc.examples.seria;

import io.grpc.examples.helloworld.HelloRequest;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SeriaTest {

    @Test
    public void test() throws  Exception {
        HelloRequest eagerSingleton=  HelloRequest.newBuilder().setName("nm").setName1("nm1").build();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(eagerSingleton);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        HelloRequest eagerSingletonFromSerial = (HelloRequest) ois.readObject();
        System.out.println( (eagerSingleton == eagerSingletonFromSerial));
    }

}
