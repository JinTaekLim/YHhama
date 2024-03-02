package com.YH.yeohaenghama.common.apiResult;

public class CommonResult {
    private boolean success;
    private String code;
    private String msg;

    public static CommonResult success() {
        return success(new CommonResult());
    }

    protected static CommonResult success(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMessage());
        return result;
    }

    public static CommonResult notFound() {
        return notFound(new CommonResult());
    }


    protected static CommonResult notFound(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.NOT_FOUND.getCode());
        result.setMsg(CommonResponse.NOT_FOUND.getMessage());
        return result;
    }

    public static CommonResult fail() {
        return fail(CommonResponse.FAIL);
    }

    public static CommonResult fail(CommonResponse commonResponse) {
        return fail(commonResponse.getCode(), commonResponse.getMessage());
    }

    public static CommonResult fail(String code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public CommonResult() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.msg;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CommonResult)) {
            return false;
        } else {
            CommonResult other = (CommonResult) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isSuccess() != other.isSuccess()) {
                return false;
            } else {
                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$msg = this.getMessage();
                Object other$msg = other.getMessage();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CommonResult;
    }

    public int hashCode() {
        //int PRIME = true;
        int result = 1;
        result = result * 59 + (this.isSuccess() ? 79 : 97);
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMessage();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        return result;
    }

    public String toString() {
        return "CommonResult(success=" + this.isSuccess() + ", resultCode=" + this.getCode() + ", resultMessage=" + this.getMessage() + ")";
    }

    public static enum CommonResponse implements Response {
        SUCCESS("200", "정상"),
        NOT_FOUND("404","해당 데이터를 찾을 수 없습니다."),
        BAD_REQUEST("4000", "검증 오류"),
        FEIGN_CONNECT_FAIL("5010", "서버 연동 중 오류가 발생했습니다."),
        UNAUTHORIZED("9401", "인증되지 않은 사용자"),
        FORBIDDEN("9403", "권한없음"),
        EXPIRED_REFRESH_TOKEN("9499", "리프레쉬 토큰 만료"),
        DUPLICATE_USER("9777", "중복로그인"),
        SESSION_EXPIRE("9790", "사용자 세션 만료"),
        FAIL("9999", "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도하시거나, 해당 오류가 지속될 경우 고객센터로 문의 바랍니다.");

        String code;
        String message;
        String description;

        private CommonResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }

        public String getDescription() {
            return this.description;
        }
    }
}
