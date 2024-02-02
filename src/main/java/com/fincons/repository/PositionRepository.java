package com.fincons.repository;

import com.fincons.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PositionRepository extends JpaRepository <Position, Long> {

    Position findPositionByPositionId(String idPosition);
}
