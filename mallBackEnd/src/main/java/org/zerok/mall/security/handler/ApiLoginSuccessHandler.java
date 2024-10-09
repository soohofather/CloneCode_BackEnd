package org.zerok.mall.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerok.mall.dto.MemberDto;
import org.zerok.mall.util.JwtUtil;

@Log4j2
public class ApiLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        MemberDto memberDto = (MemberDto) authentication.getPrincipal();

        Map<String, Object> claims = memberDto.getClaims();

        String jwtAccessToken = JwtUtil.generateToken(claims, 10);
        String jwtRefreshToken = JwtUtil.generateToken(claims, 60*24);

        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);

        Gson gson = new Gson();

        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }

}
