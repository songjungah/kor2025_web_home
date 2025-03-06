package korweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링 컨테이너의 빈 등록
public class SecurityConfig {

    // [1] 시큐리티의 필터 정의하기
    @Bean // 필드 또는 메소드에 빈 등록하는 방법
    public SecurityFilterChain filterChain(HttpSecurity http ) throws Exception {

        // [3] HTTP URL 요청에 따른 부여된 권한/상태 확인후 자원 허가 제한
        http.authorizeHttpRequests( ( httpReq ) -> {
            // 3-1 : 모든 HTTP 요청에 '/**'(모든url뜻) .antMatcher('http경로' ).permitAll() : 지정한 경로에는 누가나 접근할 수 있다.
            // 3-2 : 글쓰기페이지(board/write) 에는 로그인된 회원만 접근할 수 있다.
            // 3-3 : 채팅페이지(chat) 에는 로그인회원이면서 Role이 USER 회원만 접근할 수 있다.
            // 3-4 : 관리자페이지(admni) 엔ㄴ 로로그인회원이면서 Role 이 admin 이거나 temp1 회원만 접근 할 수 있다.
            httpReq
                    .requestMatchers( AntPathRequestMatcher.antMatcher("/board/write") ).authenticated()
                    .requestMatchers( AntPathRequestMatcher.antMatcher("/chat") ).hasRole("USER")
                    .requestMatchers( AntPathRequestMatcher.antMatcher("/admin") ).hasAnyRole( "admin" , "team1" )
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/**") ).permitAll();
        } );

        // [2] http 객체를 빌드/실행하여 보안 필터 체인을 생성
        return http.build();
    }

}