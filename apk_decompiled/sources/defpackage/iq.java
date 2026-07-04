package defpackage;

import android.content.Context;
import android.os.Build;
import android.widget.EdgeEffect;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class iq {
    public final Context a;
    public final int b;
    public long c = 0;
    public EdgeEffect d;
    public EdgeEffect e;
    public EdgeEffect f;
    public EdgeEffect g;
    public EdgeEffect h;
    public EdgeEffect i;
    public EdgeEffect j;
    public EdgeEffect k;

    public iq(Context context, int i) {
        this.a = context;
        this.b = i;
    }

    public static boolean f(EdgeEffect edgeEffect) {
        if (edgeEffect == null) {
            return false;
        }
        return !edgeEffect.isFinished();
    }

    public static boolean g(EdgeEffect edgeEffect) {
        float f;
        boolean z = false;
        if (edgeEffect == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 31) {
            f = p7.c(edgeEffect);
        } else {
            f = 0.0f;
        }
        if (f == 0.0f) {
            z = true;
        }
        return !z;
    }

    public final EdgeEffect a(dj0 dj0Var) {
        EdgeEffect cxVar;
        int i = Build.VERSION.SDK_INT;
        Context context = this.a;
        if (i >= 31) {
            cxVar = p7.a(context);
        } else {
            cxVar = new cx(context);
        }
        cxVar.setColor(this.b);
        if (!c20.a(this.c, 0L)) {
            long j = this.c;
            if (dj0Var == dj0.e) {
                cxVar.setSize((int) (j >> 32), (int) (j & 4294967295L));
                return cxVar;
            }
            cxVar.setSize((int) (4294967295L & j), (int) (j >> 32));
        }
        return cxVar;
    }

    public final EdgeEffect b() {
        EdgeEffect edgeEffect = this.e;
        if (edgeEffect == null) {
            EdgeEffect a = a(dj0.e);
            this.e = a;
            return a;
        }
        return edgeEffect;
    }

    public final EdgeEffect c() {
        EdgeEffect edgeEffect = this.f;
        if (edgeEffect == null) {
            EdgeEffect a = a(dj0.f);
            this.f = a;
            return a;
        }
        return edgeEffect;
    }

    public final EdgeEffect d() {
        EdgeEffect edgeEffect = this.g;
        if (edgeEffect == null) {
            EdgeEffect a = a(dj0.f);
            this.g = a;
            return a;
        }
        return edgeEffect;
    }

    public final EdgeEffect e() {
        EdgeEffect edgeEffect = this.d;
        if (edgeEffect == null) {
            EdgeEffect a = a(dj0.e);
            this.d = a;
            return a;
        }
        return edgeEffect;
    }
}
