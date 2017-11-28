package org.gan.exception;

import org.gan.VO.UserVO;

public class YouShouldLogin extends Exception {
    private UserVO origin;
    public YouShouldLogin(UserVO origin){
        this.origin = origin;
    }

    public UserVO getOrigin() {
        return origin;
    }

    public void setOrigin(UserVO origin) {
        this.origin = origin;
    }

    @Override
    public String getMessage() {
        return "계정이 벌써 존재합니다. 로그인 하세요";
    }
}
