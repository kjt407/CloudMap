package com.jongtk.cloudmap.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class EchoHandler extends TextWebSocketHandler {


    List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
    // 로그인중인 개별유저
    Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();


    // 클라이언트가 서버로 연결시
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.warn("---------------- afterConnectionEstablished ------------------");
        String senderId = getMemberId(session); // 접속한 유저의 http세션을 조회하여 id를 얻는 함수
        log.warn("senderID = "+senderId);
        if(senderId!=null) {	// 로그인 값이 있는 경우만
            log(senderId + " 연결 됨");
            users.put(senderId, session);   // 로그인중 개별유저 저장
        }
    }
    // 클라이언트가 Data 전송 시
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.warn("---------------- handleTextMessage ------------------");
        String senderId = getMemberId(session);
        log.warn("senderID = "+senderId);
        // 특정 유저에게 보내기
        String msg = message.getPayload();
        if(msg != null) {
            String[] strs = msg.split(",");
            log(strs.toString());
            if(strs != null && strs.length == 4) {
                String type = strs[0];
                String target = strs[1]; // m_id 저장
                String content = strs[2];
                String url = strs[3];
                WebSocketSession targetSession = users.get(target);  // 메시지를 받을 세션 조회

                // 실시간 접속시
                if(targetSession!=null) {
                    // ex: [&분의일] 신청이 들어왔습니다.
                    TextMessage tmpMsg = new TextMessage("<a target='_blank' href='"+ url +"'>[<b>" + type + "</b>] " + content + "</a>" );
                    targetSession.sendMessage(tmpMsg);
                }
            }
        }
    }
    // 연결 해제될 때
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.warn("---------------- afterConnectionClosed ------------------");
        String senderId = getMemberId(session);
        log.warn("senderID = "+senderId);
        if(senderId!=null) {	// 로그인 값이 있는 경우만
            log(senderId + " 연결 종료됨");
            users.remove(senderId);
            sessions.remove(session);
        }
    }
    // 에러 발생시
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.warn("---------------- handleTransportError ------------------");
        log(session.getId() + " 익셉션 발생: " + exception.getMessage());

    }
    // 로그 메시지
    private void log(String logmsg) {
        log.warn("---------------- log ------------------");
        System.out.println(new Date() + " : " + logmsg);
    }
    // 웹소켓에 id 가져오기
    // 접속한 유저의 http세션을 조회하여 id를 얻는 함수
    private String getMemberId(WebSocketSession session) {
        log.warn("---------------- getMemberId ------------------");
        Map<String, Object> httpSession = session.getAttributes();
        log.warn("MAp = "+httpSession);
        String m_id = (String) httpSession.get("username"); // 세션에 저장된 m_id 기준 조회
        log.warn("m_id = "+m_id);
        return m_id==null? null: m_id;
    }
}
