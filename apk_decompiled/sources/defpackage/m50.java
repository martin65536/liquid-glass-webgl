package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class m50 {
    public final /* synthetic */ int a;
    public final /* synthetic */ n50 b;
    public final /* synthetic */ Object c;

    public /* synthetic */ m50(n50 n50Var, Object obj, int i) {
        this.a = i;
        this.b = n50Var;
        this.c = obj;
    }

    public f50 b() {
        n50 n50Var = this.b;
        z40 z40Var = (z40) n50Var.n.g(this.c);
        if (z40Var != null) {
            return (f50) n50Var.j.g(z40Var);
        }
        return null;
    }

    public final boolean c() {
        hl0 hl0Var;
        switch (this.a) {
            case 0:
                return true;
            default:
                f50 b = b();
                if (b == null || (hl0Var = b.f) == null) {
                    return true;
                }
                return hl0Var.c();
        }
    }

    private final void a() {
    }
}
