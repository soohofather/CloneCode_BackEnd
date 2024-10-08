package org.zerok.mall.service.Impl;


import java.util.LinkedHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.zerok.mall.dto.MemberDto;
import org.zerok.mall.repository.MemberRepository;
import org.zerok.mall.service.MemberService;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDto getKakaoMember(String accessToken) {

        // accessToken을 이용해서 사용자의 정보를 가져오기

        getEmailFromKakaoAccessToken(accessToken);



        // 기존 DB에 회원 정보가 있는 경우 or 없는 경우

        return null;
    }

    private void getEmailFromKakaoAccessToken(String accessToken) {

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

        log.info("------------------------------");
        log.info(bodyMap);

    }

}
