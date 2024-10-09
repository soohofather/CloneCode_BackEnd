package org.zerok.mall.service;


import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.zerok.mall.dto.MemberDto;
import org.zerok.mall.entity.MemberEntity;

@Transactional
public interface MemberService {

    MemberDto getKakaoMember(String accessToken);

    default MemberDto entityToDto(MemberEntity member) {

        return new MemberDto(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList()));
    }

}
