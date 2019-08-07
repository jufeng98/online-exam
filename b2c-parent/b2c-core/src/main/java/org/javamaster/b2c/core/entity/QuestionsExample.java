package org.javamaster.b2c.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class QuestionsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QuestionsExample() {
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

        public Criteria andQuestionsTitleIsNull() {
            addCriterion("questions_title is null");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleIsNotNull() {
            addCriterion("questions_title is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleEqualTo(String value) {
            addCriterion("questions_title =", value, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleNotEqualTo(String value) {
            addCriterion("questions_title <>", value, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleGreaterThan(String value) {
            addCriterion("questions_title >", value, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleGreaterThanOrEqualTo(String value) {
            addCriterion("questions_title >=", value, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleLessThan(String value) {
            addCriterion("questions_title <", value, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleLessThanOrEqualTo(String value) {
            addCriterion("questions_title <=", value, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleLike(String value) {
            addCriterion("questions_title like", value, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleNotLike(String value) {
            addCriterion("questions_title not like", value, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleIn(List<String> values) {
            addCriterion("questions_title in", values, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleNotIn(List<String> values) {
            addCriterion("questions_title not in", values, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleBetween(String value1, String value2) {
            addCriterion("questions_title between", value1, value2, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTitleNotBetween(String value1, String value2) {
            addCriterion("questions_title not between", value1, value2, "questionsTitle");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeIsNull() {
            addCriterion("questions_type is null");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeIsNotNull() {
            addCriterion("questions_type is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeEqualTo(Byte value) {
            addCriterion("questions_type =", value, "questionsType");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeNotEqualTo(Byte value) {
            addCriterion("questions_type <>", value, "questionsType");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeGreaterThan(Byte value) {
            addCriterion("questions_type >", value, "questionsType");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("questions_type >=", value, "questionsType");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeLessThan(Byte value) {
            addCriterion("questions_type <", value, "questionsType");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeLessThanOrEqualTo(Byte value) {
            addCriterion("questions_type <=", value, "questionsType");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeIn(List<Byte> values) {
            addCriterion("questions_type in", values, "questionsType");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeNotIn(List<Byte> values) {
            addCriterion("questions_type not in", values, "questionsType");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeBetween(Byte value1, Byte value2) {
            addCriterion("questions_type between", value1, value2, "questionsType");
            return (Criteria) this;
        }

        public Criteria andQuestionsTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("questions_type not between", value1, value2, "questionsType");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNull() {
            addCriterion("sort_order is null");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNotNull() {
            addCriterion("sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualTo(Integer value) {
            addCriterion("sort_order =", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualTo(Integer value) {
            addCriterion("sort_order <>", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThan(Integer value) {
            addCriterion("sort_order >", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort_order >=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThan(Integer value) {
            addCriterion("sort_order <", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("sort_order <=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderIn(List<Integer> values) {
            addCriterion("sort_order in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotIn(List<Integer> values) {
            addCriterion("sort_order not in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("sort_order between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("sort_order not between", value1, value2, "sortOrder");
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