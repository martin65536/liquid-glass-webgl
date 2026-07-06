package defpackage;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class jd0 implements id0 {
    public final Context e;
    public hj f;
    public final ek0 g = new ek0(1.0f);
    public dy0 h;

    public jd0(Context context) {
        this.e = context;
    }

    @Override // defpackage.wj
    public final xj getKey() {
        return x1.Q;
    }

    @Override // defpackage.yj
    public final yj i(yj yjVar) {
        return jc0.z(this, yjVar);
    }

    @Override // defpackage.yj
    public final wj j(xj xjVar) {
        return jc0.p(this, xjVar);
    }

    @Override // defpackage.yj
    public final Object n(kv kvVar, Object obj) {
        return kvVar.d(obj, this);
    }

    @Override // defpackage.yj
    public final yj s(xj xjVar) {
        return jc0.x(this, xjVar);
    }

    /* JADX WARN: Type inference failed for: r2v6, types: [java.lang.Object, gy0] */
    @Override // defpackage.id0
    public final float t() {
        ij ijVar;
        iy0 iy0Var;
        if (this.h == null) {
            Context context = this.e;
            ve0 ve0Var = b81.a;
            synchronized (ve0Var) {
                try {
                    Object g = ve0Var.g(context);
                    ijVar = null;
                    if (g == null) {
                        ContentResolver contentResolver = context.getContentResolver();
                        Uri uriFor = Settings.Global.getUriFor("animator_duration_scale");
                        zb c = f31.c(-1, 6, null);
                        j2 j2Var = new j2(21, new kf0(contentResolver, uriFor, new a81(c, o20.k(Looper.getMainLooper())), c, context, null));
                        f30 f30Var = new f30(null);
                        bm bmVar = mn.a;
                        g = k81.M(j2Var, new hj(jc0.z(f30Var, yb0.a)), new Object(), Float.valueOf(Settings.Global.getFloat(context.getContentResolver(), "animator_duration_scale", 1.0f)));
                        ve0Var.m(context, g);
                    }
                    iy0Var = (iy0) g;
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.g.h(((Number) iy0Var.getValue()).floatValue());
            hj hjVar = this.f;
            if (hjVar != null) {
                this.h = f31.G(hjVar, null, new d(iy0Var, this, ijVar, 12), 3);
            } else {
                v7.o("MotionDurationScale scale factor requested before recomposer loop start");
                return 0.0f;
            }
        }
        return this.g.g();
    }
}
