package org.zerok.mall.service.Impl;


import java.util.LinkedHashMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.zerok.mall.dto.MemberDto;
import org.zerok.mall.dto.MemberDto.KakaoUserInfo;
import org.zerok.mall.entity.MemberEntity;
import org.zerok.mall.entity.MemberRole;
import org.zerok.mall.repository.MemberRepository;
import org.zerok.mall.service.MemberService;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto getKakaoMember(String accessToken) {

        // accessToken을 이용해서 사용자의 정보를 가져오기

        MemberDto.KakaoUserInfo kakaoUserInfo = getEmailFromKakaoAccessToken(accessToken);

        Optional<MemberEntity> result = memberRepository.findById(kakaoUserInfo.getEmail());

        //기존의 회원
        if(result.isPresent()){

            return entityToDTO(result.get());
        }

        //회원이 아니었다면
        //닉네임은 '소셜회원'으로
        //패스워드는 임의로 생성
        MemberEntity socialMember = makeSocialMember(kakaoUserInfo);
        memberRepository.save(socialMember);

        return entityToDTO(socialMember);
    }

    private MemberDto.KakaoUserInfo getEmailFromKakaoAccessToken(String accessToken) {

        String kakaoGetUserUrl = "https://kapi.kakao.com/v2/user/me";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserUrl).build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(uriBuilder.toUri(), HttpMethod.GET, entity, LinkedHashMap.class);

        log.info(response);

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");
        LinkedHashMap<String, String> profile = bodyMap.get("profile");

        log.info("------------------------------");

        String email = kakaoAccount.get("email");
        String nickname = profile.get("nickname");

        return new MemberDto.KakaoUserInfo(email, nickname);
    }

    private MemberEntity makeSocialMember(MemberDto.KakaoUserInfo kakaoUserInfo) {

        String tempPassword = makeTempPassword();

        log.info("tempPassword: " + tempPassword);

        String nickname = kakaoUserInfo.getNickname();

        MemberEntity member = MemberEntity.builder()
                .email(kakaoUserInfo.getEmail())
                .pw(passwordEncoder.encode(tempPassword))
                .nickname(nickname)
                .social(true)
                .build();

        member.addRole(MemberRole.USER);

        return member;
    }

    private String makeTempPassword() {

        StringBuffer buffer = new StringBuffer();

        for(int i = 0;  i < 10; i++){
            buffer.append(  (char) ( (int)(Math.random()*55) + 65  ));
        }
        return buffer.toString();
    }
}
