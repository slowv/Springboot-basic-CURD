package com.slowv.youtuberef.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomPermissionEvaluator {

    public boolean hasPermission(Authentication authentication, Object entity, String action) {
        // Logic kiểm tra quyền và hành động
        // Giả sử bạn có danh sách các quyền của người dùng trong authentication
        // Bạn sẽ kiểm tra xem người dùng có quyền thực hiện hành động trên entity hay không
        return true; // Thay đổi điều kiện này dựa trên logic của bạn
    }
}
