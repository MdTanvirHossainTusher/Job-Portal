package com.example.job_portal.constant.db;

public class DbConstant {
    private DbConstant() {
    }

    public static class DbCommon {
        public static final String ID = "id";
        public static final String CREATED_AT = "create_at";
        public static final String LAST_UPDATED_AT = "last_updated_at";
        public static final String CREATED_BY = "create_by";
        public static final String LAST_UPDATED_BY = "last_updated_by";

        DbCommon() {
        }
    }

    public static class DbAudit extends DbCommon {
        public static final String TABLE_NAME = "tbl_audit";
    }

    public static class DbUniversity extends DbCommon {
        public static final String TABLE_NAME = "tbl_university";
        public static final String UNIVERSITY_NAME = "university_name";
        public static final String DEGREE = "degree";
        public static final String PASSING_YEAR = "passing_year";
    }

    public static class DbSkill extends DbCommon {
        public static final String TABLE_NAME = "tbl_university";
        public static final String SKILL_NAME = "skill_name";
    }
}
