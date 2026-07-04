package defpackage;

import android.content.res.AssetManager;
import android.os.Build;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.concurrent.Executor;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zm {
    public final Executor a;
    public final wn0 b;
    public final byte[] c;
    public final File d;
    public final String e;
    public boolean f = false;
    public an[] g;
    public byte[] h;

    public zm(AssetManager assetManager, Executor executor, wn0 wn0Var, String str, File file) {
        this.a = executor;
        this.b = wn0Var;
        this.e = str;
        this.d = file;
        int i = Build.VERSION.SDK_INT;
        byte[] bArr = null;
        if (i >= 24) {
            if (i >= 31) {
                bArr = n20.g;
            } else {
                switch (i) {
                    case 24:
                    case 25:
                        bArr = n20.k;
                        break;
                    case 26:
                        bArr = n20.j;
                        break;
                    case 27:
                        bArr = n20.i;
                        break;
                    case 28:
                    case 29:
                    case 30:
                        bArr = n20.h;
                        break;
                }
            }
        }
        this.c = bArr;
    }

    public final FileInputStream a(AssetManager assetManager, String str) {
        try {
            return assetManager.openFd(str).createInputStream();
        } catch (FileNotFoundException e) {
            String message = e.getMessage();
            if (message != null && message.contains("compressed")) {
                this.b.d();
                return null;
            }
            return null;
        }
    }

    public final void b(int i, Serializable serializable) {
        this.a.execute(new zf(i, 1, this, serializable));
    }
}
