package com.example.rpt.service.service.impl;

import com.example.ext.service.model.AsDubboService;
import com.example.ext.service.model.dto.RoomDto;
import com.example.rpt.service.service.ReportService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

  @DubboReference(version = "1.0.0")
  private AsDubboService extDubboService;

  @Override
  public RoomDto testDubbo(Long id) {
    return extDubboService.findRoomById(id);
  }
}
