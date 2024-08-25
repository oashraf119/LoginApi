package com.example.loginapi27;
    public class User {
        private String name;
        private String phone;
        private String email;
        private String id;


        public User(){}
        public User(String id,String name, String phone, String email) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.email = email;
        }

        public String getName() {
            return name;
        }
        public String getId() {
            return id;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }
    }


