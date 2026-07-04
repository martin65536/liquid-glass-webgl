package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dm implements au0 {
    public final gv a;
    public final cm b = new cm(this);
    public final nf0 c = new nf0();
    public final ik0 d;
    public final ik0 e;
    public final ik0 f;

    public dm(gv gvVar) {
        this.a = gvVar;
        Boolean bool = Boolean.FALSE;
        this.d = n30.B(bool);
        this.e = n30.B(bool);
        this.f = n30.B(bool);
    }

    @Override // defpackage.au0
    public final /* synthetic */ boolean a() {
        return true;
    }

    @Override // defpackage.au0
    public final boolean b() {
        return ((Boolean) this.d.getValue()).booleanValue();
    }

    @Override // defpackage.au0
    public final /* synthetic */ boolean c() {
        return true;
    }

    @Override // defpackage.au0
    public final Object d(gf0 gf0Var, kv kvVar, jj jjVar) {
        Object q = dl.q(new f(this, gf0Var, kvVar, null, 4), jjVar);
        if (q == ik.e) {
            return q;
        }
        return x31.a;
    }

    @Override // defpackage.au0
    public final float e(float f) {
        return ((Number) this.a.e(Float.valueOf(f))).floatValue();
    }
}
