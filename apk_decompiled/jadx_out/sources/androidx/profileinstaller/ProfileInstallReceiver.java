package androidx.profileinstaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import defpackage.j2;
import defpackage.n20;
import defpackage.o4;
import java.io.File;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class ProfileInstallReceiver extends BroadcastReceiver {
    /* JADX WARN: Type inference failed for: r8v11, types: [java.util.concurrent.Executor, java.lang.Object] */
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Bundle extras;
        File cacheDir;
        Context createDeviceProtectedStorageContext;
        Context createDeviceProtectedStorageContext2;
        if (intent != null) {
            String action = intent.getAction();
            if ("androidx.profileinstaller.action.INSTALL_PROFILE".equals(action)) {
                o4.b0(context, new Object(), new j2(19, this), true);
                return;
            }
            if ("androidx.profileinstaller.action.SKIP_FILE".equals(action)) {
                Bundle extras2 = intent.getExtras();
                if (extras2 != null) {
                    String string = extras2.getString("EXTRA_SKIP_FILE_OPERATION");
                    if ("WRITE_SKIP_FILE".equals(string)) {
                        j2 j2Var = new j2(19, this);
                        try {
                            o4.Q(context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0), context.getFilesDir());
                            j2Var.e(10, null);
                            return;
                        } catch (PackageManager.NameNotFoundException e) {
                            j2Var.e(7, e);
                            return;
                        }
                    }
                    if ("DELETE_SKIP_FILE".equals(string)) {
                        new File(context.getFilesDir(), "profileinstaller_profileWrittenFor_lastUpdateTime.dat").delete();
                        Log.d("ProfileInstaller", "RESULT_DELETE_SKIP_FILE_SUCCESS");
                        setResultCode(11);
                        return;
                    }
                    return;
                }
                return;
            }
            if ("androidx.profileinstaller.action.SAVE_PROFILE".equals(action)) {
                j2 j2Var2 = new j2(19, this);
                if (Build.VERSION.SDK_INT >= 24) {
                    Process.sendSignal(Process.myPid(), 10);
                    j2Var2.e(12, null);
                    return;
                } else {
                    j2Var2.e(13, null);
                    return;
                }
            }
            if ("androidx.profileinstaller.action.BENCHMARK_OPERATION".equals(action) && (extras = intent.getExtras()) != null) {
                String string2 = extras.getString("EXTRA_BENCHMARK_OPERATION");
                j2 j2Var3 = new j2(19, this);
                if ("DROP_SHADER_CACHE".equals(string2)) {
                    int i = Build.VERSION.SDK_INT;
                    if (i >= 34) {
                        createDeviceProtectedStorageContext2 = context.createDeviceProtectedStorageContext();
                        cacheDir = createDeviceProtectedStorageContext2.getCacheDir();
                    } else if (i >= 24) {
                        createDeviceProtectedStorageContext = context.createDeviceProtectedStorageContext();
                        cacheDir = createDeviceProtectedStorageContext.getCodeCacheDir();
                    } else if (i == 23) {
                        cacheDir = context.getCodeCacheDir();
                    } else {
                        cacheDir = context.getCacheDir();
                    }
                    if (n20.q(cacheDir)) {
                        j2Var3.e(14, null);
                        return;
                    } else {
                        j2Var3.e(15, null);
                        return;
                    }
                }
                j2Var3.e(16, null);
            }
        }
    }
}
