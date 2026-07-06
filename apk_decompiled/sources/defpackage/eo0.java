package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class eo0 {
    public final do0 a;
    public final boolean b;
    public final ix0 c;
    public final boolean d;
    public final Object e;
    public boolean f = true;

    public eo0(do0 do0Var, Object obj, boolean z, ix0 ix0Var, boolean z2) {
        this.a = do0Var;
        this.b = z;
        this.c = ix0Var;
        this.d = z2;
        this.e = obj;
    }

    public final Object a() {
        if (this.b) {
            return null;
        }
        Object obj = this.e;
        if (obj != null) {
            return obj;
        }
        rh.b("Unexpected form of a provided value");
        throw new RuntimeException();
    }
}
