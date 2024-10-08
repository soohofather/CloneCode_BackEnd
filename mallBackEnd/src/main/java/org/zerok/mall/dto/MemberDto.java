package org.zerok.mall.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.zerok.mall.entity.MemberRole;


public class MemberDto extends User {

    private String email, pw, nickname;

    private boolean social;

    private List<String> roleNames = new ArrayList<>();

    public MemberDto(
            String email,
            String pw,
            String nickname,
            boolean social,
            List<String> roleNames
    ) {
        super(
                email,
                pw,
                roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_"+str))
                        .collect(Collectors.toList())
        );

        this.email = email;
        this.pw = pw;
        this.nickname = nickname;
        this.social = social;
        this.roleNames = roleNames;
    }

    public Map<String, Object> getClaims() {

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("email", email);
        dataMap.put("pw",pw);
        dataMap.put("nickname", nickname);
        dataMap.put("social", social);
        dataMap.put("roleNames", roleNames);

        return dataMap;
    }

    public static class KakaoUserInfo {
        private String email;
        private String nickname;

        // 생성자
        public KakaoUserInfo(String email, String nickname) {
            this.email = email;
            this.nickname = nickname;
        }

        // Getter
        public String getEmail() {
            return email;
        }

        public String getNickname() {
            return nickname;
        }
    }


}
