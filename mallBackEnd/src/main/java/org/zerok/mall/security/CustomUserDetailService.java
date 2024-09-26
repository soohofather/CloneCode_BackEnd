package org.zerok.mall.security;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerok.mall.dto.MemberDto;
import org.zerok.mall.entity.MemberEntity;
import org.zerok.mall.repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("----------------loadUserByUsername---------------");

        MemberEntity member = memberRepository.getWithRoles(username);

        if(member == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        MemberDto memberDto = new MemberDto(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList()
                        .stream()
                        .map(memberRole -> memberRole.name())
                        .collect(Collectors.toList())
        );
        return memberDto;
    }
}
