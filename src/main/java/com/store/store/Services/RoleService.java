package com.store.store.Services;
import com.store.store.Model.Entities.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> getRoleByName(String name);
}
