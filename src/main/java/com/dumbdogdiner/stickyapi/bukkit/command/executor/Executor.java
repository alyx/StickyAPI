/**
 * Copyright (c) 2020 DumbDogDiner <dumbdogdiner.com>. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickyapi.bukkit.command.executor;

import com.dumbdogdiner.stickyapi.common.arguments.Arguments;
import com.dumbdogdiner.stickyapi.common.command.ExitCode;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@FunctionalInterface
public interface Executor {
    ExitCode execute(@NotNull CommandSender sender, @NotNull Arguments args, @NotNull String label, @NotNull Map<String, String> vars);
}