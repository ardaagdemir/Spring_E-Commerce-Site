package com.example.repositories.joinRepository;

import com.example.entities.joinTables.JoinProCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JoinProCatRepository extends JpaRepository<JoinProCat, Long> {

    @Query(value = "select p.pid, c.cid, p.title, p.price, c.category_name, p.description from product p  inner join category c on p.cid=c.cid ", nativeQuery = true)
    List<JoinProCat> getProducts();
    @Query(value = "SELECT p.pid,c.cid, p.title, p.price, c.category_name, p.description FROM product as p INNER JOIN category as c ON p.cid = c.cid WHERE p.title LIKE ?1 ", nativeQuery = true)
    List<JoinProCat> searchProduct(String q);
    @Query(value = "SELECT p.pid,c.cid, p.title, p.price, c.category_name, p.description FROM product as p INNER JOIN category as c ON p.cid = c.cid WHERE p.pid LIKE ?1 ", nativeQuery = true)
    Optional<JoinProCat> getProductById(int pid);


}