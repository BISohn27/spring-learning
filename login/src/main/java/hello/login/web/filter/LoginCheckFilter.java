package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/members/add", "/login", "/logout", "/css/*", "/favicon.ico"};
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (isLoginCheckPath(requestURI)) {
            try {
                log.info("인증 체크 필터 시작 {}", requestURI);


                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = request.getSession(false);

                if (Objects.isNull(session) || Objects.isNull(session.getAttribute(SessionConst.LOGIN_MEMBER))) {
                    log.info("미인증 사용자 요청 {}", requestURI);

                    response.sendRedirect("/login?redirectUrl=" + requestURI);
                    return;
                }
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                throw e;
            } finally {
                log.info("인증 체크 필터 종료 {}", requestURI);
            }
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크x
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
