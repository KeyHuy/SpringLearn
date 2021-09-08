package com.Key.spring.ioc.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 学生实体类
 *
 * @author Key
 * @date 2021/08/17/21:23
 **/
public class Students {
    private String[] course;
    private Map<String,String> stuMap;
    private Set<String> stuSet;

    private List<Course> stuCourse;

    public void setStuCourse(List<Course> stuCourse) {
        this.stuCourse = stuCourse;
    }

    public void setCourse(String[] course) {
        this.course = course;
    }

    public void setStuMap(Map<String, String> stuMap) {
        this.stuMap = stuMap;
    }

    public void setStuSet(Set<String> stuSet) {
        this.stuSet = stuSet;
    }

    @Override
    public String toString() {
        return "Students{" +
                "course=" + Arrays.toString(course) +
                ", stuMap=" + stuMap +
                ", stuSet=" + stuSet +
                ", stuCourse=" + stuCourse +
                '}';
    }
}
