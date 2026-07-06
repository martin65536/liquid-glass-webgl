package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class jg0 {
    public bd0 a;
    public int b;
    public ef0 c;
    public ef0 d;
    public boolean e;
    public final /* synthetic */ lg0 f;

    public jg0(lg0 lg0Var, bd0 bd0Var, int i, ef0 ef0Var, ef0 ef0Var2, boolean z) {
        this.f = lg0Var;
        this.a = bd0Var;
        this.b = i;
        this.c = ef0Var;
        this.d = ef0Var2;
        this.e = z;
    }

    public final boolean a(int i, int i2) {
        ef0 ef0Var = this.c;
        int i3 = this.b;
        ad0 ad0Var = (ad0) ef0Var.e[i + i3];
        ad0 ad0Var2 = (ad0) this.d.e[i3 + i2];
        if (o20.e(ad0Var, ad0Var2) || ad0Var.getClass() == ad0Var2.getClass()) {
            return true;
        }
        return false;
    }
}
