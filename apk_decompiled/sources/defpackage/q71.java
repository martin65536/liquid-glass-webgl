package defpackage;

import android.graphics.Rect;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class q71 {
    public final qa a;
    public final float b;

    public q71(Rect rect, float f) {
        this.a = new qa(rect);
        this.b = f;
    }

    public final Rect a() {
        qa qaVar = this.a;
        qaVar.getClass();
        return new Rect(qaVar.a, qaVar.b, qaVar.c, qaVar.d);
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
        if (!q71.class.equals(cls)) {
            return false;
        }
        obj.getClass();
        q71 q71Var = (q71) obj;
        if (o20.e(this.a, q71Var.a) && this.b == q71Var.b) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.b) + (this.a.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("WindowMetrics(_bounds=");
        sb.append(this.a);
        sb.append(", density=");
        return d3.v(sb, this.b, ')');
    }

    public q71(qa qaVar, float f) {
        this.a = qaVar;
        this.b = f;
    }
}
