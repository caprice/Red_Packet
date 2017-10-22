package com.fan.service.response;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/10/11.22:39
 */

public class RegisterResponse {

    /**
     * err : 333
     * msg : 注册成功
     * data : {"inviter":null,"uid":42,"token":"O8iJlFifuqsUjLQ9l072YEa4E9Uec4lvN6viECwG"}
     */

    private int err;
    private String msg;
    private DataBean data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * inviter : null
         * uid : 42
         * token : O8iJlFifuqsUjLQ9l072YEa4E9Uec4lvN6viECwG
         */

        private Object inviter;
        private int uid;
        private String token;

        public Object getInviter() {
            return inviter;
        }

        public void setInviter(Object inviter) {
            this.inviter = inviter;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
