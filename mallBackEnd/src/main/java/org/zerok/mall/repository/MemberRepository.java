package org.zerok.mall.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerok.mall.entity.MemberEntity;


public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    @EntityGraph(attributePaths = {"memberRoleList"})
    @Query("select m from MemberEntity m where m.email = :email")
    MemberEntity getWithRoles(@Param("email") String email);

}
