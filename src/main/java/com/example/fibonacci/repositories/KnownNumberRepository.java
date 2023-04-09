package com.example.fibonacci.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fibonacci.models.KnownNumberModel;


@Repository
public interface KnownNumberRepository extends JpaRepository<KnownNumberModel, Integer> {

}
