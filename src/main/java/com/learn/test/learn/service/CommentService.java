package com.learn.test.learn.service;

import com.learn.test.learn.enums.CommentTypeEnum;
import com.learn.test.learn.exception.CustomizeErrorCode;
import com.learn.test.learn.exception.CustomizeException;
import com.learn.test.learn.mapper.CommentMapper;
import com.learn.test.learn.mapper.QuestionExMapper;
import com.learn.test.learn.mapper.QuestionMapper;
import com.learn.test.learn.model.Comment;
import com.learn.test.learn.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExMapper questionExMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId()==null ||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }
        else{
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            question.setCommentCount(1);
            questionExMapper.incCommentCount(question);
        }

    }
}
