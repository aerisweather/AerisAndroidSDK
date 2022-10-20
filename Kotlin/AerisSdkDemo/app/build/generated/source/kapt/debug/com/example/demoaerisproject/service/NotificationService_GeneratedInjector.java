package com.example.demoaerisproject.service;

import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ServiceComponent;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = NotificationService.class
)
@GeneratedEntryPoint
@InstallIn(ServiceComponent.class)
public interface NotificationService_GeneratedInjector {
  void injectNotificationService(NotificationService notificationService);
}
