package com.websocket.chat.repository;


import com.websocket.chat.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByReportFromAndReportTo(String reportFrom, String reportTo);
}
