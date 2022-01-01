package cn.nadow.oauthserver.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity,Long> {

    UsersEntity findByUsername(String username);
}