package com.example.repositories.joinRepository;

import com.example.entities.joinTables.JoinOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface JoinOrderDetailsRepository extends JpaRepository<JoinOrderDetails, Integer> {

    @Query(value = "SELECT o.oid, p.pid, u.uid, u.first_name, u.last_name, p.title, p.price " +
            "FROM orders as o " +
            "INNER JOIN product as p on p.pid=o.pid " +
            "INNER JOIN users as u on o.uid=u.uid ORDER BY u.uid ASC", nativeQuery = true)
    List<JoinOrderDetails> getOrders();

    @Query(value = "SELECT o.oid, p.pid, u.uid, u.first_name, u.last_name, p.title, p.price " +
            "FROM orders as o " +
            "INNER JOIN product as p on p.pid=o.pid " +
            "INNER JOIN users as u on o.uid=u.uid where o.uid like ?1", nativeQuery = true)
    List<JoinOrderDetails> getOrderById(int oid);
}
