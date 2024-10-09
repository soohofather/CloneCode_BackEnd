package org.zerok.mall.controller;


import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerok.mall.dto.MemberDto;
import org.zerok.mall.service.MemberService;
import org.zerok.mall.util.JwtUtil;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    @GetMapping("/api/member/kakao")
    public Map<String, Object> getMemberFromKakao(String accessToken) {

        log.info("accessToken: {}", accessToken);

        MemberDto memberDto = memberService.getKakaoMember(accessToken);

        Map<String, Object> claims = memberDto.getClaims();

        String jwtAccessToken = JwtUtil.generateToken(claims, 10);
        String jwtRefreshToken = JwtUtil.generateToken(claims, 60*24);

        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);

        return claims;
    }
}
