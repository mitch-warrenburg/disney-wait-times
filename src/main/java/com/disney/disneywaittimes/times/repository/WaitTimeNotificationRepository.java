package com.disney.disneywaittimes.times.repository;

import com.disney.disneywaittimes.times.entity.WaitTimeNotification;
import com.disney.disneywaittimes.times.model.AttractionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mitch-warrenburg
 */

@Repository
public interface WaitTimeNotificationRepository extends JpaRepository<WaitTimeNotification, Long> {

  void deleteAllByName(String attractionName);

  boolean existsByName(String attractionName);

  boolean existsByNameAndStatus(String attractionName, AttractionStatus status);
}
