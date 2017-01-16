/*
 * Copyright (C) 2015 Qiujuer <qiujuer@live.cn>
 * WebSite http://www.qiujuer.net
 * Created 08/07/2015
 * Changed 08/07/2015
 * Version 3.0.0
 * Author Qiujuer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.simon.agiledevelop.state.loading;

/**
 * describe: Interface that drawables supporting animations should implement
 *
 * @author Simon Han
 * @date 2016.10.19
 * @email hanzx1024@gmail.com
 */

public interface Animatable extends android.graphics.drawable.Animatable {
    /**
     * This is drawable animation frame duration
     */
    public static final int FRAME_DURATION = 16;

    /**
     * This is drawable animation duration
     */
    public static final int ANIMATION_DURATION = 250;
}
