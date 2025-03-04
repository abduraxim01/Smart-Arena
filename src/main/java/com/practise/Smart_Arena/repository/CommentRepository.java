package com.practise.Smart_Arena.repository;

import com.practise.Smart_Arena.model.owner.Polya;
import com.practise.Smart_Arena.model.player.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface   CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findCommentsByPolya(Polya polya);
}
