package com.YH.yeohaenghama.common.apiResult;

public class ApiResult<T> extends CommonResult {
    private T data;

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult();
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> notFound(T data) {
        ApiResult<T> result = new ApiResult();
        result.setSuccess(true);
        result.setCode(CommonResult.notFound().getCode());
        result.setMsg(CommonResult.notFound().getMessage());
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> notFound(String msg) {
        ApiResult<T> result = new ApiResult();
        result.setSuccess(true);
        result.setCode(CommonResult.notFound().getCode());
        result.setMsg(msg);
        return result;
    }

    public static <T> ApiResult<T> fail(String msg) {
        ApiResult<T> result = new ApiResult();
        result.setSuccess(true);
        result.setCode(CommonResult.fail().getCode());
        result.setMsg(CommonResult.fail().getMessage()+msg);
        return result;
    }


    public ApiResult() {
    }

    public T getData() {
        return this.data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public String toString() {
        return "ApiResult(data=" + this.getData() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ApiResult)) {
            return false;
        } else {
            ApiResult<?> other = (ApiResult) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ApiResult;
    }

    public int hashCode() {
        //int PRIME = true;
        int result = 1;
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }
}