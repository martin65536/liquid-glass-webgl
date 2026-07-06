package defpackage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gu implements nq {
    public final Context e;
    public final fu f;
    public final rt g;
    public final Object h = new Object();
    public Handler i;
    public ThreadPoolExecutor j;
    public ThreadPoolExecutor k;
    public o4 l;

    public gu(Context context, fu fuVar) {
        m20.k(context, "Context cannot be null");
        this.e = context.getApplicationContext();
        this.f = fuVar;
        this.g = hu.d;
    }

    @Override // defpackage.nq
    public final void a(o4 o4Var) {
        synchronized (this.h) {
            this.l = o4Var;
        }
        c();
    }

    public final void b() {
        synchronized (this.h) {
            try {
                this.l = null;
                Handler handler = this.i;
                if (handler != null) {
                    handler.removeCallbacks(null);
                }
                this.i = null;
                ThreadPoolExecutor threadPoolExecutor = this.k;
                if (threadPoolExecutor != null) {
                    threadPoolExecutor.shutdown();
                }
                this.j = null;
                this.k = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void c() {
        synchronized (this.h) {
            try {
                if (this.l == null) {
                    return;
                }
                if (this.j == null) {
                    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), new ii("emojiCompat"));
                    threadPoolExecutor.allowCoreThreadTimeOut(true);
                    this.k = threadPoolExecutor;
                    this.j = threadPoolExecutor;
                }
                this.j.execute(new n(5, this));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final qu d() {
        try {
            rt rtVar = this.g;
            Context context = this.e;
            fu fuVar = this.f;
            rtVar.getClass();
            Object[] objArr = {fuVar};
            ArrayList arrayList = new ArrayList(1);
            Object obj = objArr[0];
            Objects.requireNonNull(obj);
            arrayList.add(obj);
            pu a = eu.a(context, Collections.unmodifiableList(arrayList));
            int i = a.a;
            if (i == 0) {
                qu[] quVarArr = (qu[]) ((List) a.b).get(0);
                if (quVarArr != null && quVarArr.length != 0) {
                    return quVarArr[0];
                }
                throw new RuntimeException("fetchFonts failed (empty result)");
            }
            throw new RuntimeException("fetchFonts failed (" + i + ")");
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("provider not found", e);
        }
    }
}
