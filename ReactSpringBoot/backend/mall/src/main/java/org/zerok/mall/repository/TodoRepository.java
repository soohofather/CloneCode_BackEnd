package org.zerok.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerok.mall.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

}
