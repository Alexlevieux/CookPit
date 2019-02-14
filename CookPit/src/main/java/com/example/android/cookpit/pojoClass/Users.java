package com.example.android.cookpit.pojoClass;

/**
 * Created by alexandrelevieux on 23/04/2017.
 */

public class Users {


    public class cookPitUser {

        private String name;
        private String email;
        private String userAccount;
        private String uid;


        private boolean Admin;
        private boolean User;

        public cookPitUser() {

        }

        public cookPitUser(String name, String email, String userAccount) {

        }

        public String getname() {
            return this.name;

        }

        public void setName(String username) {
            this.name = username;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUid() {
            return this.uid;
        }


        public String getuserAccount() {
            return this.userAccount;

        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

    }


}
