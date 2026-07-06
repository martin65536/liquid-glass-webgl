package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ft {
    public final lt a;
    public final b4 b;
    public final we0 c;
    public final we0 d;
    public boolean e;

    public ft(lt ltVar, b4 b4Var) {
        this.a = ltVar;
        this.b = b4Var;
        we0 we0Var = at0.a;
        this.c = new we0();
        this.d = new we0();
    }

    public final void a() {
        if (!this.e) {
            s3 s3Var = new s3(0, this, ft.class, "invalidateNodes", "invalidateNodes()V", 0, 0, 1);
            pe0 pe0Var = this.b.B0;
            if (pe0Var.g(s3Var) < 0) {
                pe0Var.a(s3Var);
            }
            this.e = true;
        }
    }
}
