package defpackage;

import android.content.Context;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class f5 {
    public final Context a;
    public final mm b;
    public final long c;
    public final tj0 d;

    public f5(Context context, mm mmVar, long j, tj0 tj0Var) {
        this.a = context;
        this.b = mmVar;
        this.c = j;
        this.d = tj0Var;
    }

    public final boolean equals(Object obj) {
        Class<?> cls;
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            cls = obj.getClass();
        } else {
            cls = null;
        }
        if (!f5.class.equals(cls)) {
            return false;
        }
        obj.getClass();
        f5 f5Var = (f5) obj;
        if (o20.e(this.a, f5Var.a) && o20.e(this.b, f5Var.b) && se.c(this.c, f5Var.c) && o20.e(this.d, f5Var.d)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.d.hashCode() + ((se.i(this.c) + ((this.b.hashCode() + (this.a.hashCode() * 31)) * 31)) * 31);
    }
}
