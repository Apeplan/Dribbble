package com.simon.agiledevelop.state.network;

import com.simon.agiledevelop.utils.NetHelper;

/**
 * describe: Listener for NetWork
 *
 * @author Simon Han
 * @date 2015.08.27
 * @email hanzx1024@gmail.com
 */

public interface NetWorkListener {
    void onNetWorkConnected(NetHelper.NetType type);

    void onNetWorkDisConnect();
}
