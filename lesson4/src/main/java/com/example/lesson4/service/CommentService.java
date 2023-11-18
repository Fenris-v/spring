package com.example.lesson4.service;

import com.example.lesson4.dto.request.CommentRequest;
import com.example.lesson4.dto.response.CommentDto;
import com.example.lesson4.exception.EntityNotFoundException;
import com.example.lesson4.exception.ForbiddenActionException;
import com.example.lesson4.model.Comment;
import com.example.lesson4.model.News;
import com.example.lesson4.model.User;
import com.example.lesson4.repository.CommentRepository;
import com.example.lesson4.repository.NewsRepository;
import com.example.lesson4.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    @Transactional
    public CommentDto createComment(Long userId, Long newsId, CommentRequest commentRequest) {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        News news = newsRepository.findFirstById(newsId).orElseThrow(EntityNotFoundException::new);
        comment.setId(UUID.randomUUID());
        comment.setUser(user);
        comment.setNews(news);

        comment = commentRepository.save(comment);
        return modelMapper.map(comment, CommentDto.class);
    }

    @Transactional
    public void deleteComment(Long userId, UUID commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException();
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public CommentDto editComment(Long userId, UUID commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException();
        }

        modelMapper.map(commentRequest, comment);
        return modelMapper.map(comment, CommentDto.class);
    }
}
