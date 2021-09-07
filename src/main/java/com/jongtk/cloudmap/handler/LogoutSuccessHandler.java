package com.jongtk.cloudmap.handler;

import com.jongtk.cloudmap.dto.AuthMemberDTO;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Log4j2
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    private String user;

    DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public LogoutSuccessHandler(String user) {
        this.user = user;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.warn("로그아웃 핸들러 정상 실행됨");
        AuthMemberDTO authMember = (AuthMemberDTO) authentication.getPrincipal();
        if(authMember.isFromSocial()) {
            String clientName = (String) authMember.getAttr().get("clientName");
            if(clientName.equals("kakao")){
                log.warn("카카오 로그아웃 실행");
                log.warn(user);
                kakaoLogout();
            }else if(clientName.equals("Google")){
                log.warn("google 로그아웃 실행");
            }
        }

        redirectStrategy.sendRedirect(request,response,"/");
    }

    private boolean kakaoLogout(){
        //URL 설정
        HttpURLConnection conn = null;
        JSONObject responseJson = null;
        try {
            URL url = new URL("https://kapi.kakao.com/v1/user/logout");

            conn = (HttpURLConnection) url.openConnection();

            // type의 경우 POST, GET, PUT, DELETE 가능
            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Transfer-Encoding", "chunked");
//            conn.setRequestProperty("Connection", "keep-alive");
            conn.setDoOutput(true);


//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            // JSON 형식의 데이터 셋팅
//            JsonObject commands = new JsonObject();
//            JsonArray jsonArray = new JsonArray();
//
//            params.addProperty("key", 1);
//            params.addProperty("age", 20);
//            params.addProperty("userNm", "홍길동");
//
//            commands.add("userInfo", params);
//            // JSON 형식의 데이터 셋팅 끝
//
//            // 데이터를 STRING으로 변경
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String jsonOutput = gson.toJson(commands);
//
//            bw.write(commands.toString());
//            bw.flush();
//            bw.close();

            // 보내고 결과값 받기
            int responseCode = conn.getResponseCode();
            log.warn("responseCode :"+responseCode);
//            if (responseCode == 200) {
//                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuilder sb = new StringBuilder();
//                String line = "";
//                while ((line = br.readLine()) != null) {
//                    sb.append(line);
//                }
//                responseJson = new JSONObject(sb.toString());
//
//                // 응답 데이터
//                System.out.println("responseJson :: " + responseJson);
//            }
        }catch (Exception e){

        }
        return true;
    }
}
