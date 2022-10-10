package com.example.repositories.joinRepository;

import com.example.entities.joinTables.JoinProCat;
import com.example.entities.joinTables.JoinProImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JoinProImgRepository extends JpaRepository<JoinProImg, Long> {

    @Query(value = "select p.pid, i.iid, i.file, p.title, p.price, c.category_name, p.description from product p LEFT join image i on i.pid=p.pid left join category c on p.cid=c.cid GROUP BY p.pid;", nativeQuery = true)
    List<JoinProImg> getImagesByProduct();

    @Query(value = "select p.pid, i.iid, i.file, p.title, p.price, c.category_name, p.description from product p LEFT join image i on i.pid=p.pid left join category c on p.cid=c.cid where p.pid like ?1 GROUP BY p.pid", nativeQuery = true)
    List<JoinProImg> getImageByProductId(Long pid);

    @Query(value = "select c.cid, p.pid, i.iid, p.title, p.price, p.description, c.category_name, i.file from product p left join category c on p.cid=c.cid left join image i on i.pid=p.pid where c.cid like ?1 GROUP BY p.pid;" , nativeQuery = true)
    List<JoinProImg> getProductByCategoryId(long cid);
}