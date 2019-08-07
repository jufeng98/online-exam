package org.javamaster.b2c.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class OptionsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OptionsExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOptionNameIsNull() {
            addCriterion("option_name is null");
            return (Criteria) this;
        }

        public Criteria andOptionNameIsNotNull() {
            addCriterion("option_name is not null");
            return (Criteria) this;
        }

        public Criteria andOptionNameEqualTo(String value) {
            addCriterion("option_name =", value, "optionName");
            return (Criteria) this;
        }

        public Criteria andOptionNameNotEqualTo(String value) {
            addCriterion("option_name <>", value, "optionName");
            return (Criteria) this;
        }

        public Criteria andOptionNameGreaterThan(String value) {
            addCriterion("option_name >", value, "optionName");
            return (Criteria) this;
        }

        public Criteria andOptionNameGreaterThanOrEqualTo(String value) {
            addCriterion("option_name >=", value, "optionName");
            return (Criteria) this;
        }

        public Criteria andOptionNameLessThan(String value) {
            addCriterion("option_name <", value, "optionName");
            return (Criteria) this;
        }

        public Criteria andOptionNameLessThanOrEqualTo(String value) {
            addCriterion("option_name <=", value, "optionName");
            return (Criteria) this;
        }

        public Criteria andOptionNameLike(String value) {
            addCriterion("option_name like", value, "optionName");
            return (Criteria) this;
        }

        public Criteria andOptionNameNotLike(String value) {
            addCriterion("option_name not like", value, "optionName");
            return (Criteria) this;
        }

        public Criteria andOptionNameIn(List<String> values) {
            addCriterion("option_name in", values, "optionName");
            return (Criteria) this;
        }

        public Criteria andOptionNameNotIn(List<String> values) {
            addCriterion("option_name not in", values, "optionName");
            return (Criteria) this;
        }

        public Criteria andOptionNameBetween(String value1, String value2) {
            addCriterion("option_name between", value1, value2, "optionName");
            return (Criteria) this;
        }

        public Criteria andOptionNameNotBetween(String value1, String value2) {
            addCriterion("option_name not between", value1, value2, "optionName");
            return (Criteria) this;
        }

        public Criteria andCorrectIsNull() {
            addCriterion("correct is null");
            return (Criteria) this;
        }

        public Criteria andCorrectIsNotNull() {
            addCriterion("correct is not null");
            return (Criteria) this;
        }

        public Criteria andCorrectEqualTo(Boolean value) {
            addCriterion("correct =", value, "correct");
            return (Criteria) this;
        }

        public Criteria andCorrectNotEqualTo(Boolean value) {
            addCriterion("correct <>", value, "correct");
            return (Criteria) this;
        }

        public Criteria andCorrectGreaterThan(Boolean value) {
            addCriterion("correct >", value, "correct");
            return (Criteria) this;
        }

        public Criteria andCorrectGreaterThanOrEqualTo(Boolean value) {
            addCriterion("correct >=", value, "correct");
            return (Criteria) this;
        }

        public Criteria andCorrectLessThan(Boolean value) {
            addCriterion("correct <", value, "correct");
            return (Criteria) this;
        }

        public Criteria andCorrectLessThanOrEqualTo(Boolean value) {
            addCriterion("correct <=", value, "correct");
            return (Criteria) this;
        }

        public Criteria andCorrectIn(List<Boolean> values) {
            addCriterion("correct in", values, "correct");
            return (Criteria) this;
        }

        public Criteria andCorrectNotIn(List<Boolean> values) {
            addCriterion("correct not in", values, "correct");
            return (Criteria) this;
        }

        public Criteria andCorrectBetween(Boolean value1, Boolean value2) {
            addCriterion("correct between", value1, value2, "correct");
            return (Criteria) this;
        }

        public Criteria andCorrectNotBetween(Boolean value1, Boolean value2) {
            addCriterion("correct not between", value1, value2, "correct");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeIsNull() {
            addCriterion("questions_code is null");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeIsNotNull() {
            addCriterion("questions_code is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeEqualTo(String value) {
            addCriterion("questions_code =", value, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeNotEqualTo(String value) {
            addCriterion("questions_code <>", value, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeGreaterThan(String value) {
            addCriterion("questions_code >", value, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeGreaterThanOrEqualTo(String value) {
            addCriterion("questions_code >=", value, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeLessThan(String value) {
            addCriterion("questions_code <", value, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeLessThanOrEqualTo(String value) {
            addCriterion("questions_code <=", value, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeLike(String value) {
            addCriterion("questions_code like", value, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeNotLike(String value) {
            addCriterion("questions_code not like", value, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeIn(List<String> values) {
            addCriterion("questions_code in", values, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeNotIn(List<String> values) {
            addCriterion("questions_code not in", values, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeBetween(String value1, String value2) {
            addCriterion("questions_code between", value1, value2, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andQuestionsCodeNotBetween(String value1, String value2) {
            addCriterion("questions_code not between", value1, value2, "questionsCode");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameIsNull() {
            addCriterion("create_usename is null");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameIsNotNull() {
            addCriterion("create_usename is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameEqualTo(String value) {
            addCriterion("create_usename =", value, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameNotEqualTo(String value) {
            addCriterion("create_usename <>", value, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameGreaterThan(String value) {
            addCriterion("create_usename >", value, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameGreaterThanOrEqualTo(String value) {
            addCriterion("create_usename >=", value, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameLessThan(String value) {
            addCriterion("create_usename <", value, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameLessThanOrEqualTo(String value) {
            addCriterion("create_usename <=", value, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameLike(String value) {
            addCriterion("create_usename like", value, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameNotLike(String value) {
            addCriterion("create_usename not like", value, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameIn(List<String> values) {
            addCriterion("create_usename in", values, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameNotIn(List<String> values) {
            addCriterion("create_usename not in", values, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameBetween(String value1, String value2) {
            addCriterion("create_usename between", value1, value2, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateUsenameNotBetween(String value1, String value2) {
            addCriterion("create_usename not between", value1, value2, "createUsename");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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