
package io.grpc.examples.helloworld;

import io.grpc.examples.helloworld.HelloWorldServer.GreeterImpl;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class HelloWorldServerTest1 {
    @Test
    public void greeterImpl_replyMessage() throws Exception {
        String serverName = InProcessServerBuilder.generateName();
        InProcessServerBuilder.forName(serverName).directExecutor().addService(new GreeterImpl()).build().start();
        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(InProcessChannelBuilder.forName(serverName).directExecutor().build());
        HelloReply reply = blockingStub.sayHello(HelloRequest.newBuilder().setName("test name").build());
    }
}
