package com.yogee.youge.common.weixin.web;


import com.yogee.youge.common.weixin.msg.OutMsgXmlBuilder;
import com.yogee.youge.common.weixin.msg.in.*;
import com.yogee.youge.common.weixin.msg.in.event.InLocationEvent;
import com.yogee.youge.common.weixin.msg.in.event.InMenuEvent;
import com.yogee.youge.common.weixin.msg.in.event.InQrCodeEvent;
import com.yogee.youge.common.weixin.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.yogee.youge.common.weixin.msg.out.OutMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * �ж���Ϣ����,������Ϣ
 */
public class WeiXinMsgHandle {
    private static Logger log = LoggerFactory.getLogger(WeiXinMsgHandle.class);


    public static void handle(InMsg msg,HttpServletResponse response) {
        if (msg instanceof InTextMsg)
            WeiXinProcess.processInTextMsg((InTextMsg) msg,response);
        else if (msg instanceof InImageMsg)
            WeiXinProcess.processInImageMsg((InImageMsg) msg,response);
        else if (msg instanceof InVoiceMsg)
            WeiXinProcess.processInVoiceMsg((InVoiceMsg) msg,response);
        else if (msg instanceof InVideoMsg)
            WeiXinProcess.processInVideoMsg((InVideoMsg) msg,response);
        else if (msg instanceof InLocationMsg)
            WeiXinProcess.processInLocationMsg((InLocationMsg) msg,response);
        else if (msg instanceof InLinkMsg)
            WeiXinProcess.processInLinkMsg((InLinkMsg) msg,response);
//        else if (msg instanceof InFollowEvent)
//            WeiXinProcess.processInFollowEvent((InFollowEvent) msg,response);
        else if (msg instanceof InQrCodeEvent)
            WeiXinProcess.processInQrCodeEvent((InQrCodeEvent) msg,response);
        else if (msg instanceof InLocationEvent)
            WeiXinProcess.processInLocationEvent((InLocationEvent) msg,response);
        else if (msg instanceof InMenuEvent)
            WeiXinProcess.processInMenuEvent((InMenuEvent) msg,response);
        else if (msg instanceof InSpeechRecognitionResults)
            WeiXinProcess.processInSpeechRecognitionResults((InSpeechRecognitionResults) msg,response);
        else
            log.error("δ��ʶ�����Ϣ���͡�" );
    }


    /**
     * �ڽ��յ�΢�ŷ������� InMsg ��Ϣ�����Ӧ OutMsg ��Ϣ
     */
    public static void render(OutMsg outMsg,HttpServletResponse response) {
        String outMsgXml = OutMsgXmlBuilder.build(outMsg);
        // ����ģʽ�����̨����������͵� OutMsg ��Ϣ�� xml ����
        log.debug("������Ϣ: =" +outMsgXml);

        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("utf-8");
            writer = response.getWriter();
            writer.write(outMsgXml);
             writer.flush();
        } catch (IOException e) {

        }
        finally {
            if (writer != null)
                writer.close();
        }
    }
}

