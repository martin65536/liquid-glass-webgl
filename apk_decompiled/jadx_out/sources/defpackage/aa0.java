package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class aa0 extends z30 implements kv {
    public final /* synthetic */ int f;
    public final /* synthetic */ al g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ aa0(al alVar, int i) {
        super(2);
        this.f = i;
        this.g = alVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        r7 J;
        long v;
        int i = this.f;
        x31 x31Var = x31.a;
        al alVar = this.g;
        switch (i) {
            case 0:
                up upVar = (up) obj;
                gv gvVar = (gv) obj2;
                upVar.getClass();
                gvVar.getClass();
                float b = alVar.b();
                float z = d20.z(0.6666667f, 1.0f, b);
                float z2 = d20.z(0.0f, 1.0f, b);
                long W = upVar.W();
                J = upVar.J();
                v = J.v();
                J.q().h();
                try {
                    ((j2) J.f).o(z, z2, W);
                    gvVar.e(upVar);
                    return x31Var;
                } finally {
                }
            default:
                up upVar2 = (up) obj;
                gv gvVar2 = (gv) obj2;
                upVar2.getClass();
                gvVar2.getClass();
                float b2 = alVar.b();
                float z3 = d20.z(0.6666667f, 0.75f, b2);
                float z4 = d20.z(0.0f, 0.75f, b2);
                long W2 = upVar2.W();
                J = upVar2.J();
                v = J.v();
                J.q().h();
                try {
                    ((j2) J.f).o(z3, z4, W2);
                    gvVar2.e(upVar2);
                    return x31Var;
                } finally {
                }
        }
    }
}
