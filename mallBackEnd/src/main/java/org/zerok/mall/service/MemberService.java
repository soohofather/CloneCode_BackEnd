package org.zerok.mall.service;


import org.springframework.transaction.annotation.Transactional;
import org.zerok.mall.dto.MemberDto;

@Transactional
public interface MemberService {

    MemberDto getKakaoMember(String accessToken);

}
