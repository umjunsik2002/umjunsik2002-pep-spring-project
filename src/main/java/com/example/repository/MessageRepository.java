package com.example.repository;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Modifying
    @Query("DELETE FROM Message m WHERE m.id = :messageId")
    int deleteMessage(@Param("messageId") int messageId);

    @Modifying
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.id = :messageId")
    int updateMessage(@Param("messageId") int messageId, @Param("messageText") String messageText);

    List<Message> findByPostedBy(int postedBy);
}
