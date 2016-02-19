package cn.xn.wechat.web.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserInfoCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserInfoCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameIsNull() {
            addCriterion("user_login_name is null");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameIsNotNull() {
            addCriterion("user_login_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameEqualTo(String value) {
            addCriterion("user_login_name =", value, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameNotEqualTo(String value) {
            addCriterion("user_login_name <>", value, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameGreaterThan(String value) {
            addCriterion("user_login_name >", value, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_login_name >=", value, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameLessThan(String value) {
            addCriterion("user_login_name <", value, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameLessThanOrEqualTo(String value) {
            addCriterion("user_login_name <=", value, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameLike(String value) {
            addCriterion("user_login_name like", value, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameNotLike(String value) {
            addCriterion("user_login_name not like", value, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameIn(List<String> values) {
            addCriterion("user_login_name in", values, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameNotIn(List<String> values) {
            addCriterion("user_login_name not in", values, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameBetween(String value1, String value2) {
            addCriterion("user_login_name between", value1, value2, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserLoginNameNotBetween(String value1, String value2) {
            addCriterion("user_login_name not between", value1, value2, "userLoginName");
            return (Criteria) this;
        }

        public Criteria andUserAsIsNull() {
            addCriterion("user_as is null");
            return (Criteria) this;
        }

        public Criteria andUserAsIsNotNull() {
            addCriterion("user_as is not null");
            return (Criteria) this;
        }

        public Criteria andUserAsEqualTo(String value) {
            addCriterion("user_as =", value, "userAs");
            return (Criteria) this;
        }

        public Criteria andUserAsNotEqualTo(String value) {
            addCriterion("user_as <>", value, "userAs");
            return (Criteria) this;
        }

        public Criteria andUserAsGreaterThan(String value) {
            addCriterion("user_as >", value, "userAs");
            return (Criteria) this;
        }

        public Criteria andUserAsGreaterThanOrEqualTo(String value) {
            addCriterion("user_as >=", value, "userAs");
            return (Criteria) this;
        }

        public Criteria andUserAsLessThan(String value) {
            addCriterion("user_as <", value, "userAs");
            return (Criteria) this;
        }

        public Criteria andUserAsLessThanOrEqualTo(String value) {
            addCriterion("user_as <=", value, "userAs");
            return (Criteria) this;
        }

        public Criteria andUserAsLike(String value) {
            addCriterion("user_as like", value, "userAs");
            return (Criteria) this;
        }

        public Criteria andUserAsNotLike(String value) {
            addCriterion("user_as not like", value, "userAs");
            return (Criteria) this;
        }

        public Criteria andUserAsIn(List<String> values) {
            addCriterion("user_as in", values, "userAs");
            return (Criteria) this;
        }

        public Criteria andUserAsNotIn(List<String> values) {
            addCriterion("user_as not in", values, "userAs");
            return (Criteria) this;
        }

        public Criteria andUserAsBetween(String value1, String value2) {
            addCriterion("user_as between", value1, value2, "userAs");
            return (Criteria) this;
        }

        public Criteria andUserAsNotBetween(String value1, String value2) {
            addCriterion("user_as not between", value1, value2, "userAs");
            return (Criteria) this;
        }

        public Criteria andLoginPwdIsNull() {
            addCriterion("login_pwd is null");
            return (Criteria) this;
        }

        public Criteria andLoginPwdIsNotNull() {
            addCriterion("login_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andLoginPwdEqualTo(String value) {
            addCriterion("login_pwd =", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdNotEqualTo(String value) {
            addCriterion("login_pwd <>", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdGreaterThan(String value) {
            addCriterion("login_pwd >", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdGreaterThanOrEqualTo(String value) {
            addCriterion("login_pwd >=", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdLessThan(String value) {
            addCriterion("login_pwd <", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdLessThanOrEqualTo(String value) {
            addCriterion("login_pwd <=", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdLike(String value) {
            addCriterion("login_pwd like", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdNotLike(String value) {
            addCriterion("login_pwd not like", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdIn(List<String> values) {
            addCriterion("login_pwd in", values, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdNotIn(List<String> values) {
            addCriterion("login_pwd not in", values, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdBetween(String value1, String value2) {
            addCriterion("login_pwd between", value1, value2, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdNotBetween(String value1, String value2) {
            addCriterion("login_pwd not between", value1, value2, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andSaltIsNull() {
            addCriterion("salt is null");
            return (Criteria) this;
        }

        public Criteria andSaltIsNotNull() {
            addCriterion("salt is not null");
            return (Criteria) this;
        }

        public Criteria andSaltEqualTo(String value) {
            addCriterion("salt =", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotEqualTo(String value) {
            addCriterion("salt <>", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThan(String value) {
            addCriterion("salt >", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThanOrEqualTo(String value) {
            addCriterion("salt >=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThan(String value) {
            addCriterion("salt <", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThanOrEqualTo(String value) {
            addCriterion("salt <=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLike(String value) {
            addCriterion("salt like", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotLike(String value) {
            addCriterion("salt not like", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltIn(List<String> values) {
            addCriterion("salt in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotIn(List<String> values) {
            addCriterion("salt not in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltBetween(String value1, String value2) {
            addCriterion("salt between", value1, value2, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotBetween(String value1, String value2) {
            addCriterion("salt not between", value1, value2, "salt");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdIsNull() {
            addCriterion("payment_pwd is null");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdIsNotNull() {
            addCriterion("payment_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdEqualTo(String value) {
            addCriterion("payment_pwd =", value, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdNotEqualTo(String value) {
            addCriterion("payment_pwd <>", value, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdGreaterThan(String value) {
            addCriterion("payment_pwd >", value, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdGreaterThanOrEqualTo(String value) {
            addCriterion("payment_pwd >=", value, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdLessThan(String value) {
            addCriterion("payment_pwd <", value, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdLessThanOrEqualTo(String value) {
            addCriterion("payment_pwd <=", value, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdLike(String value) {
            addCriterion("payment_pwd like", value, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdNotLike(String value) {
            addCriterion("payment_pwd not like", value, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdIn(List<String> values) {
            addCriterion("payment_pwd in", values, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdNotIn(List<String> values) {
            addCriterion("payment_pwd not in", values, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdBetween(String value1, String value2) {
            addCriterion("payment_pwd between", value1, value2, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPaymentPwdNotBetween(String value1, String value2) {
            addCriterion("payment_pwd not between", value1, value2, "paymentPwd");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIsNull() {
            addCriterion("phone_number is null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIsNotNull() {
            addCriterion("phone_number is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberEqualTo(String value) {
            addCriterion("phone_number =", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotEqualTo(String value) {
            addCriterion("phone_number <>", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberGreaterThan(String value) {
            addCriterion("phone_number >", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberGreaterThanOrEqualTo(String value) {
            addCriterion("phone_number >=", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLessThan(String value) {
            addCriterion("phone_number <", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLessThanOrEqualTo(String value) {
            addCriterion("phone_number <=", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLike(String value) {
            addCriterion("phone_number like", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotLike(String value) {
            addCriterion("phone_number not like", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIn(List<String> values) {
            addCriterion("phone_number in", values, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotIn(List<String> values) {
            addCriterion("phone_number not in", values, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberBetween(String value1, String value2) {
            addCriterion("phone_number between", value1, value2, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotBetween(String value1, String value2) {
            addCriterion("phone_number not between", value1, value2, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(String value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(String value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(String value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(String value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(String value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(String value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLike(String value) {
            addCriterion("user_type like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotLike(String value) {
            addCriterion("user_type not like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<String> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<String> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(String value1, String value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(String value1, String value2) {
            addCriterion("user_type not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeIsNull() {
            addCriterion("lase_logintime is null");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeIsNotNull() {
            addCriterion("lase_logintime is not null");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeEqualTo(Date value) {
            addCriterion("lase_logintime =", value, "laseLogintime");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeNotEqualTo(Date value) {
            addCriterion("lase_logintime <>", value, "laseLogintime");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeGreaterThan(Date value) {
            addCriterion("lase_logintime >", value, "laseLogintime");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeGreaterThanOrEqualTo(Date value) {
            addCriterion("lase_logintime >=", value, "laseLogintime");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeLessThan(Date value) {
            addCriterion("lase_logintime <", value, "laseLogintime");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeLessThanOrEqualTo(Date value) {
            addCriterion("lase_logintime <=", value, "laseLogintime");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeIn(List<Date> values) {
            addCriterion("lase_logintime in", values, "laseLogintime");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeNotIn(List<Date> values) {
            addCriterion("lase_logintime not in", values, "laseLogintime");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeBetween(Date value1, Date value2) {
            addCriterion("lase_logintime between", value1, value2, "laseLogintime");
            return (Criteria) this;
        }

        public Criteria andLaseLogintimeNotBetween(Date value1, Date value2) {
            addCriterion("lase_logintime not between", value1, value2, "laseLogintime");
            return (Criteria) this;
        }

        public Criteria andIsCertificationIsNull() {
            addCriterion("is_certification is null");
            return (Criteria) this;
        }

        public Criteria andIsCertificationIsNotNull() {
            addCriterion("is_certification is not null");
            return (Criteria) this;
        }

        public Criteria andIsCertificationEqualTo(Integer value) {
            addCriterion("is_certification =", value, "isCertification");
            return (Criteria) this;
        }

        public Criteria andIsCertificationNotEqualTo(Integer value) {
            addCriterion("is_certification <>", value, "isCertification");
            return (Criteria) this;
        }

        public Criteria andIsCertificationGreaterThan(Integer value) {
            addCriterion("is_certification >", value, "isCertification");
            return (Criteria) this;
        }

        public Criteria andIsCertificationGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_certification >=", value, "isCertification");
            return (Criteria) this;
        }

        public Criteria andIsCertificationLessThan(Integer value) {
            addCriterion("is_certification <", value, "isCertification");
            return (Criteria) this;
        }

        public Criteria andIsCertificationLessThanOrEqualTo(Integer value) {
            addCriterion("is_certification <=", value, "isCertification");
            return (Criteria) this;
        }

        public Criteria andIsCertificationIn(List<Integer> values) {
            addCriterion("is_certification in", values, "isCertification");
            return (Criteria) this;
        }

        public Criteria andIsCertificationNotIn(List<Integer> values) {
            addCriterion("is_certification not in", values, "isCertification");
            return (Criteria) this;
        }

        public Criteria andIsCertificationBetween(Integer value1, Integer value2) {
            addCriterion("is_certification between", value1, value2, "isCertification");
            return (Criteria) this;
        }

        public Criteria andIsCertificationNotBetween(Integer value1, Integer value2) {
            addCriterion("is_certification not between", value1, value2, "isCertification");
            return (Criteria) this;
        }

        public Criteria andIsQuestionIsNull() {
            addCriterion("is_question is null");
            return (Criteria) this;
        }

        public Criteria andIsQuestionIsNotNull() {
            addCriterion("is_question is not null");
            return (Criteria) this;
        }

        public Criteria andIsQuestionEqualTo(Integer value) {
            addCriterion("is_question =", value, "isQuestion");
            return (Criteria) this;
        }

        public Criteria andIsQuestionNotEqualTo(Integer value) {
            addCriterion("is_question <>", value, "isQuestion");
            return (Criteria) this;
        }

        public Criteria andIsQuestionGreaterThan(Integer value) {
            addCriterion("is_question >", value, "isQuestion");
            return (Criteria) this;
        }

        public Criteria andIsQuestionGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_question >=", value, "isQuestion");
            return (Criteria) this;
        }

        public Criteria andIsQuestionLessThan(Integer value) {
            addCriterion("is_question <", value, "isQuestion");
            return (Criteria) this;
        }

        public Criteria andIsQuestionLessThanOrEqualTo(Integer value) {
            addCriterion("is_question <=", value, "isQuestion");
            return (Criteria) this;
        }

        public Criteria andIsQuestionIn(List<Integer> values) {
            addCriterion("is_question in", values, "isQuestion");
            return (Criteria) this;
        }

        public Criteria andIsQuestionNotIn(List<Integer> values) {
            addCriterion("is_question not in", values, "isQuestion");
            return (Criteria) this;
        }

        public Criteria andIsQuestionBetween(Integer value1, Integer value2) {
            addCriterion("is_question between", value1, value2, "isQuestion");
            return (Criteria) this;
        }

        public Criteria andIsQuestionNotBetween(Integer value1, Integer value2) {
            addCriterion("is_question not between", value1, value2, "isQuestion");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdIsNull() {
            addCriterion("is_payment_pwd is null");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdIsNotNull() {
            addCriterion("is_payment_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdEqualTo(Integer value) {
            addCriterion("is_payment_pwd =", value, "isPaymentPwd");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdNotEqualTo(Integer value) {
            addCriterion("is_payment_pwd <>", value, "isPaymentPwd");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdGreaterThan(Integer value) {
            addCriterion("is_payment_pwd >", value, "isPaymentPwd");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_payment_pwd >=", value, "isPaymentPwd");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdLessThan(Integer value) {
            addCriterion("is_payment_pwd <", value, "isPaymentPwd");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdLessThanOrEqualTo(Integer value) {
            addCriterion("is_payment_pwd <=", value, "isPaymentPwd");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdIn(List<Integer> values) {
            addCriterion("is_payment_pwd in", values, "isPaymentPwd");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdNotIn(List<Integer> values) {
            addCriterion("is_payment_pwd not in", values, "isPaymentPwd");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdBetween(Integer value1, Integer value2) {
            addCriterion("is_payment_pwd between", value1, value2, "isPaymentPwd");
            return (Criteria) this;
        }

        public Criteria andIsPaymentPwdNotBetween(Integer value1, Integer value2) {
            addCriterion("is_payment_pwd not between", value1, value2, "isPaymentPwd");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}