package org.zerok.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerok.mall.entity.TodoEntity;
import org.zerok.mall.repository.search.TodoSearch;

public interface TodoRepository extends JpaRepository<TodoEntity, Long>, TodoSearch {

}
