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

    public static class DbUniversity extends DbCommon {
        public static final String TABLE_NAME = "universities";
        public static final String UNIVERSITY_NAME = "university_name";
        public static final String DEGREE = "degree";
        public static final String PASSING_YEAR = "passing_year";
    }

    public static class DbSkill extends DbCommon {
        public static final String TABLE_NAME = "skills";
        public static final String SKILL_NAME = "skill_name";
    }

    public static class DbCV extends DbCommon {
        public static final String TABLE_NAME = "cvs";
        public static final String CV_FORMAT = "cv_format";
        public static final String CV_SIZE = "cv_size";
        public static final String CV_URL = "cv_url";
    }

    public static class DbJob extends DbCommon {
        public static final String TABLE_NAME = "jobs";
        public static final String JOB_DESCRIPTION = "job_description";
        public static final String SALARY = "salary";
        public static final String IS_FRAUDULENT = "is_fraudulent";
    }

    public static class DbCompany extends DbCommon {
        public static final String TABLE_NAME = "companies";
        public static final String COMPANY_NAME = "company_name";
        public static final String COMPANY_LOCATION = "company_location";
        public static final String COMPANY_TYPE = "company_type";
        public static final String WORKING_MODE = "working_mode";
    }

    public static class DbMyCompany extends DbCommon {
        public static final String TABLE_NAME = "my_companies";
    }

    public static class DbRole extends DbCommon {
        public static final String TABLE_NAME = "roles";
        public static final String ROLE = "role";
    }

    public static class DbProfile extends DbCommon {
        public static final String TABLE_NAME = "profiles";
        public static final String CV = "my_cv";
    }

    public static class DbUser extends DbCommon {
        public static final String TABLE_NAME = "users";
        public static final String USER_NAME = "name";
        public static final String USER_EMAIL = "email";
        public static final String USER_PASSWORD = "password";
        public static final String IMAGE_URL = "profile_image_url";
        public static final String TOTAL_EXPERIENCE = "total_experience";
    }
}
