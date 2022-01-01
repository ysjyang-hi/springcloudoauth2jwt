package cn.nadow.oauthserver.services;

import cn.nadow.oauthserver.entity.UsersEntity;
import cn.nadow.oauthserver.entity.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    /**
     * 根据username查询出该用户的所有信息，封装成UserDetails类型的对象返回，至于密码，框架会自动匹配
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity user= usersRepository.findByUsername(username);
        if(user==null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return new org.springframework.security.core.userdetails.User(username,
                user.getPassword(),
                true,
                true,
                true,
                true,
                getGrantedAuthority(user));


    }
    public List<GrantedAuthority> getGrantedAuthority(UsersEntity user){
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return list;
    }

}

