package com.galaxycodes.springsecurity.repo;

import com.galaxycodes.springsecurity.model.Referrals;
import com.galaxycodes.springsecurity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralsRepo extends JpaRepository<Referrals, Integer> {
    List<Referrals> findAllByUserInReferrals_id(Integer staff_id);
    List<Referrals> findAllByToStaffId(Integer toStaffId);
    List<Referrals> findAllByPatient_id(Integer toStaffId);
}
