package com.learn.test.learn.service;

import com.learn.test.learn.dto.CommentDTO;
import com.learn.test.learn.dto.PaginationDTO;
import com.learn.test.learn.dto.QuestionDTO;
import com.learn.test.learn.enums.CommentTypeEnum;
import com.learn.test.learn.exception.CustomizeErrorCode;
import com.learn.test.learn.exception.CustomizeException;
import com.learn.test.learn.mapper.CommentMapper;
import com.learn.test.learn.mapper.QuestionExMapper;
import com.learn.test.learn.mapper.QuestionMapper;
import com.learn.test.learn.mapper.UserMapper;
import com.learn.test.learn.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExMapper questionExMapper;

    @Autowired
    private CommentMapper commentMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        } else{
            totalPage = totalCount / size + 1;
        }
        if(page<1) {
            page = 1;
        }
        if(page>totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().
                andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        } else{
            totalPage = totalCount / size + 1;
        }
        if(page<1) {
            page = 1;
        }
        if(page>totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        if(page<1) {
            page = 1;
        }
        if(page>paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().
                andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question ==  null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdata(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);

            //创建问题
        }else {
            Question updataQuestion = new Question();
            updataQuestion.setGmtModified(System.currentTimeMillis());
            updataQuestion.setTitle(question.getTitle());
            updataQuestion.setDescription(question.getDescription());
            updataQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updataQuestion, example);
            if (updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //更新问题
        }
    }

    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExMapper.incView(question);
    }

    public List<CommentDTO> listByQuestionId(Integer id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer,User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
