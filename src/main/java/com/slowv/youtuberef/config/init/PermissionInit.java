package com.slowv.youtuberef.config.init;

import com.slowv.youtuberef.entity.ActionEntity;
import com.slowv.youtuberef.entity.PermissionEntity;
import com.slowv.youtuberef.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionInit implements CommandLineRunner {
    private final PermissionRepository permissionRepository;

    @Override
    public void run(final String... args) throws Exception {
        if (permissionRepository.count() <= 0) {
            final var actions = List.of(
                    new ActionEntity()
                            .setName("READ_ALL"),
                    new ActionEntity()
                            .setName("READ_DETAIL"),
                    new ActionEntity()
                            .setName("UPDATE"),
                    new ActionEntity()
                            .setName("DELETE")
            );
           permissionRepository.saveAll(
                   List.of(
                           new PermissionEntity()
                                   .setName("VIDEO_MANAGER")
                                   .setActions(
                                           actions
                                   ),
                           new PermissionEntity()
                                   .setName("VIDEO")
                                   .setActions(
                                           actions
                                   )
                   )
           );
        }
    }
}
