package cn.agtsci.channel;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class ReceiveService {

    @StreamListener("input")
    public void receiveInput1(String receiveMsg) {
        System.out.println("input receive: " + receiveMsg);
    }

}
