// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.openapi.extensions;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ExtensionPointChangeListener<T> {
  void extensionChanged(@NotNull T e, @NotNull PluginDescriptor pd);
}
