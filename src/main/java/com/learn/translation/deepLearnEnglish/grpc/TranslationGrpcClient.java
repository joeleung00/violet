package com.learn.translation.deepLearnEnglish.grpc;

import com.learn.translation.deepLearnEnglish.model.dto.TextPair;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;



@Component
@Scope(value= ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TranslationGrpcClient {
    private final TranslationServiceGrpc.TranslationServiceBlockingStub blockingStub;

    @Autowired
    public TranslationGrpcClient(ChannelWrapper channelWrapper){
        blockingStub = TranslationServiceGrpc.newBlockingStub(channelWrapper.getChannel());
    }

    public String translateVocab(String vocab) {
        VocabRequest request = VocabRequest.newBuilder().setVocab(vocab).build();
        ExplainResponse response;
        response = blockingStub.translateVocab(request);
        return response.getText();
    }

    public TextPair translateText(String text) {
        TextRequest request = TextRequest.newBuilder().setText(text).build();
        TextResponse response = blockingStub.translateText(request);
        TextPair textPair = new TextPair();
        textPair.setTextEng(response.getInput());
        textPair.setTextChin(response.getOutput());
        return textPair;
    }

    public static void main(String[] args) throws Exception{
        ChannelWrapper channelWrapper = new ChannelWrapper();
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50051")
                .usePlaintext()
                .build();
        channelWrapper.setChannel(channel);
        TranslationGrpcClient client = new TranslationGrpcClient(channelWrapper);
        String hi = client.translateVocab("bye");
        System.out.println(hi);
    }

}
