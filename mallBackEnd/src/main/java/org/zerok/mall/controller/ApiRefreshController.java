package org.zerok.mall.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerok.mall.util.CustomJwtException;
import org.zerok.mall.util.JwtUtil;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ApiRefreshController {

    @RequestMapping("/api/member/refresh")
    public Map<String, Object> refresh (
            @RequestHeader("Authorization") String authHeader,
            String refreshToken
    ) {
        if(refreshToken == null) {
            throw new CustomJwtException("NULL_REFRESH");
        }

        if(authHeader == null || authHeader.length() < 7) {
            throw new CustomJwtException("INVALID STRING");
        }

        // 앞에 7개는 제거\
        String accessToken = authHeader.substring(7);

        //Access 토큰이 만료되지 않았다면
        if(!checkExpiredToken(accessToken)) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        //Refresh토큰 검증
        Map<String, Object> claims = JwtUtil.validateToken(refreshToken);

        log.info("refresh ... claims: " + claims);

        String newAccessToken = JwtUtil.generateToken(claims, 10);

        String newRefreshToken = checkTime((Integer) claims.get("exp")) ? JwtUtil.generateToken(claims, 60*24) : refreshToken;

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    private boolean checkExpiredToken(String token) {

        try{
            JwtUtil.validateToken(token);
        }catch(CustomJwtException ex) {
            if(ex.getMessage().equals("Expired")){
                return true;
            }
        }
        return false;
    }

    //시간이 1시간 미만으로 남았다면
    private boolean checkTime(Integer exp) {

        //JWT exp를 날짜로 변환
        java.util.Date expDate = new java.util.Date( (long)exp * (1000 ));

        //현재 시간과의 차이 계산 - 밀리세컨즈
        long gap   = expDate.getTime() - System.currentTimeMillis();

        //분단위 계산
        long leftMin = gap / (1000 * 60);

        //1시간도 안남았는지..
        return leftMin < 60;
    }

}
