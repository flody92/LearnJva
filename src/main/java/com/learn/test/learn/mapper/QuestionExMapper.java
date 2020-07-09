package com.learn.test.learn.mapper;

import com.learn.test.learn.model.Question;
import com.learn.test.learn.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExMapper {

    int incView(Question record);
}