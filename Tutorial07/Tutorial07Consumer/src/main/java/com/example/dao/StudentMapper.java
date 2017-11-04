package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;


import com.example.model.CourseModel;
import com.example.model.StudentModel;

@Mapper
public interface StudentMapper {
	
	@Select("select npm, name, gpa from student where npm = #{npm}")
	@Results(value = {
			@Result(property = "npm", column = "npm"),
			@Result(property = "name", column = "name"),
			@Result(property = "gpa", column = "gpa"),
			@Result(property = "courses", column = "npm",
					javaType = List.class,
					many = @Many(select = "selectCourses"))
	})
	StudentModel selectStudent(@Param("npm") String npm);

	@Select("select npm, name, gpa from student")
	@Results(value = {
			@Result(property = "npm", column = "npm"),
			@Result(property = "name", column = "name"),
			@Result(property = "gpa", column = "gpa"),
			@Result(property = "courses", column = "npm",
					javaType = List.class,
					many = @Many(select = "selectCourses"))
	})
	List<StudentModel> selectAllStudents();

	@Insert("INSERT INTO student (npm, name, gpa) VALUES (#{npm}, #{name}, #{gpa})")
	void addStudent(StudentModel student);

	@Delete("DELETE FROM student WHERE npm=#{npm}")
	void deleteStudent(String npm);

	@Update("UPDATE student SET name=#{name}, gpa=#{gpa} WHERE npm=#{npm}")
	void updateStudent(StudentModel student);

	@Select("select course.id_course, name, credits " + 
			"from studentcourse join course " +
			"on studentcourse.id_course = course.id_course " + 
			"where studentcourse.npm = #{npm}")
	List<CourseModel> selectCourses(@Param("npm") String npm);
}
