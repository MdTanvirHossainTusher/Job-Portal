package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DbConstant.DbCV.TABLE_NAME)
public class MyCompany {
}
