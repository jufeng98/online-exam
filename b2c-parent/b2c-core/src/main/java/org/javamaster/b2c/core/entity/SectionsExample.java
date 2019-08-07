package org.javamaster.b2c.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class SectionsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SectionsExample() {
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

        public Criteria andSectionsCodeIsNull() {
            addCriterion("sections_code is null");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeIsNotNull() {
            addCriterion("sections_code is not null");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeEqualTo(String value) {
            addCriterion("sections_code =", value, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeNotEqualTo(String value) {
            addCriterion("sections_code <>", value, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeGreaterThan(String value) {
            addCriterion("sections_code >", value, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sections_code >=", value, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeLessThan(String value) {
            addCriterion("sections_code <", value, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeLessThanOrEqualTo(String value) {
            addCriterion("sections_code <=", value, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeLike(String value) {
            addCriterion("sections_code like", value, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeNotLike(String value) {
            addCriterion("sections_code not like", value, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeIn(List<String> values) {
            addCriterion("sections_code in", values, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeNotIn(List<String> values) {
            addCriterion("sections_code not in", values, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeBetween(String value1, String value2) {
            addCriterion("sections_code between", value1, value2, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsCodeNotBetween(String value1, String value2) {
            addCriterion("sections_code not between", value1, value2, "sectionsCode");
            return (Criteria) this;
        }

        public Criteria andSectionsNameIsNull() {
            addCriterion("sections_name is null");
            return (Criteria) this;
        }

        public Criteria andSectionsNameIsNotNull() {
            addCriterion("sections_name is not null");
            return (Criteria) this;
        }

        public Criteria andSectionsNameEqualTo(String value) {
            addCriterion("sections_name =", value, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andSectionsNameNotEqualTo(String value) {
            addCriterion("sections_name <>", value, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andSectionsNameGreaterThan(String value) {
            addCriterion("sections_name >", value, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andSectionsNameGreaterThanOrEqualTo(String value) {
            addCriterion("sections_name >=", value, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andSectionsNameLessThan(String value) {
            addCriterion("sections_name <", value, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andSectionsNameLessThanOrEqualTo(String value) {
            addCriterion("sections_name <=", value, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andSectionsNameLike(String value) {
            addCriterion("sections_name like", value, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andSectionsNameNotLike(String value) {
            addCriterion("sections_name not like", value, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andSectionsNameIn(List<String> values) {
            addCriterion("sections_name in", values, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andSectionsNameNotIn(List<String> values) {
            addCriterion("sections_name not in", values, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andSectionsNameBetween(String value1, String value2) {
            addCriterion("sections_name between", value1, value2, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andSectionsNameNotBetween(String value1, String value2) {
            addCriterion("sections_name not between", value1, value2, "sectionsName");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeIsNull() {
            addCriterion("topics_code is null");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeIsNotNull() {
            addCriterion("topics_code is not null");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeEqualTo(String value) {
            addCriterion("topics_code =", value, "topicsCode");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeNotEqualTo(String value) {
            addCriterion("topics_code <>", value, "topicsCode");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeGreaterThan(String value) {
            addCriterion("topics_code >", value, "topicsCode");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeGreaterThanOrEqualTo(String value) {
            addCriterion("topics_code >=", value, "topicsCode");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeLessThan(String value) {
            addCriterion("topics_code <", value, "topicsCode");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeLessThanOrEqualTo(String value) {
            addCriterion("topics_code <=", value, "topicsCode");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeLike(String value) {
            addCriterion("topics_code like", value, "topicsCode");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeNotLike(String value) {
            addCriterion("topics_code not like", value, "topicsCode");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeIn(List<String> values) {
            addCriterion("topics_code in", values, "topicsCode");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeNotIn(List<String> values) {
            addCriterion("topics_code not in", values, "topicsCode");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeBetween(String value1, String value2) {
            addCriterion("topics_code between", value1, value2, "topicsCode");
            return (Criteria) this;
        }

        public Criteria andTopicsCodeNotBetween(String value1, String value2) {
            addCriterion("topics_code not between", value1, value2, "topicsCode");
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