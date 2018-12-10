package com.yogee.youge.common.weixin.web;

import com.yogee.youge.common.weixin.msg.in.*;
import com.yogee.youge.common.weixin.msg.in.event.InLocationEvent;
import com.yogee.youge.common.weixin.msg.in.event.InMenuEvent;
import com.yogee.youge.common.weixin.msg.in.event.InQrCodeEvent;
import com.yogee.youge.common.weixin.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.yogee.youge.common.weixin.msg.out.OutTextMsg;

import javax.servlet.http.HttpServletResponse;

/**
 * ����ͬ���͵�����
 */
public class WeiXinProcess {

    private static final String helpStr = " �ο�";
    // ������յ����ı���Ϣ
    protected  static  void processInTextMsg(InTextMsg inTextMsg,HttpServletResponse response){
        String msgContent = inTextMsg.getContent().trim();

            OutTextMsg outMsg = new OutTextMsg(inTextMsg);
            outMsg.setContent("\t�ı���Ϣ�ѳɹ����գ�����Ϊ�� " + inTextMsg.getContent() + "\n\n" + helpStr);
            WeiXinMsgHandle.render(outMsg, response);
    }


    // ������յ���ͼƬ��Ϣ
    protected static void processInImageMsg(InImageMsg inImageMsg,HttpServletResponse response){

    }

    // ������յ���������Ϣ
    protected static void processInVoiceMsg(InVoiceMsg inVoiceMsg,HttpServletResponse response){

    }

    // ������յ�����Ƶ��Ϣ
    protected static void processInVideoMsg(InVideoMsg inVideoMsg,HttpServletResponse response){

    }

    // ������յ��ĵ�ַλ����Ϣ
    protected static void processInLocationMsg(InLocationMsg inLocationMsg,HttpServletResponse response){

    }

    // ������յ���������Ϣ
    protected static void processInLinkMsg(InLinkMsg inLinkMsg,HttpServletResponse response){

    }


    // ������յ���ɨ���������ά���¼�
    protected static void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent,HttpServletResponse response){

    }

    // ������յ����ϱ�����λ���¼�
    protected static void processInLocationEvent(InLocationEvent inLocationEvent,HttpServletResponse response){

    }

    // ������յ����Զ���˵��¼�
    protected static void processInMenuEvent(InMenuEvent inMenuEvent,HttpServletResponse response){

    }

    // ������յ�������ʶ����
    protected static void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults,HttpServletResponse response){

    }
}
