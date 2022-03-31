package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SessionManagerTest {
    private SessionManager sessionManager = new SessionManager();

    @Test
    public void SessionTest() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        Object session = sessionManager.getSession(request);
        Assertions.assertThat(member).isEqualTo(session);

        sessionManager.expire(request);
        Object session1 = sessionManager.getSession(request);
        Assertions.assertThat(session1).isNull();
    }
}