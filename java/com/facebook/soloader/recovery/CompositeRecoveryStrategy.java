/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.soloader.recovery;

import com.facebook.soloader.SoSource;

public class CompositeRecoveryStrategy implements RecoveryStrategy {
  private final RecoveryStrategy[] mStrategies;
  private int mCurrentStrategy;

  public CompositeRecoveryStrategy(RecoveryStrategy... strategies) {
    mStrategies = strategies;
    mCurrentStrategy = 0;
  }

  @Override
  public boolean recover(UnsatisfiedLinkError e, SoSource[] soSources) {
    while (mCurrentStrategy < mStrategies.length) {
      RecoveryStrategy currentStrategy = mStrategies[mCurrentStrategy++];
      if (currentStrategy.recover(e, soSources)) {
        return true;
      }
    }
    return false;
  }
}
