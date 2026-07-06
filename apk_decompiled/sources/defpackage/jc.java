package defpackage;

import java.io.Serializable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class jc implements p30, Serializable {
    public transient p30 e;
    public final Object f;
    public final Class g;
    public final String h;
    public final String i;
    public final boolean j;

    public jc(Object obj, Class cls, String str, String str2, boolean z) {
        this.f = obj;
        this.g = cls;
        this.h = str;
        this.i = str2;
        this.j = z;
    }

    public abstract p30 f();

    public final vd g() {
        boolean z = this.j;
        Class cls = this.g;
        if (z) {
            fp0.a.getClass();
            return new qj0(cls);
        }
        return fp0.a(cls);
    }
}
