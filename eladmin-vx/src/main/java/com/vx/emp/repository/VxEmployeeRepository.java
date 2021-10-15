package com.vx.emp.repository;

import com.vx.emp.domain.VxEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * @author zmh
 * @date 2021-10-12
 **/
public interface VxEmployeeRepository extends JpaRepository<VxEmployee, Long>, JpaSpecificationExecutor<VxEmployee> {
    /**
     * 根据智能包装工号 查询
     *
     * @param znbzUid 智能包装工号
     * @return /
     */
//    @Query(value = "", nativeQuery = true)
    VxEmployee findByZnbzUid(String znbzUid);
}