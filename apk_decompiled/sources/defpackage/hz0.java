package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hz0 {
    public final kz0 a;
    public n50 b;
    public final gz0 c = new gz0(this, 2);
    public final gz0 d = new gz0(this, 0);
    public final gz0 e = new gz0(this, 1);

    public hz0(kz0 kz0Var) {
        this.a = kz0Var;
    }

    public final n50 a() {
        n50 n50Var = this.b;
        if (n50Var != null) {
            return n50Var;
        }
        v7.m("SubcomposeLayoutState is not attached to SubcomposeLayout");
        return null;
    }
}
