package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class v60 implements vu {
    public final /* synthetic */ int e;
    public final /* synthetic */ x60 f;

    public /* synthetic */ v60(x60 x60Var, int i) {
        this.e = i;
        this.f = x60Var;
    }

    @Override // defpackage.vu
    public final Object a() {
        float f;
        long f2;
        int i = this.e;
        x60 x60Var = this.f;
        switch (i) {
            case 0:
                m70 m70Var = x60Var.t.b;
                return Float.valueOf((((fk0) m70Var.e.b).g() * 500) + ((fk0) m70Var.e.c).g());
            case 1:
                m70 m70Var2 = x60Var.t.b;
                int g = ((fk0) m70Var2.e.b).g();
                int g2 = ((fk0) m70Var2.e.c).g();
                if (m70Var2.c()) {
                    f = (g * 500) + g2 + 100.0f;
                } else {
                    f = (g * 500) + g2;
                }
                return Float.valueOf(f);
            default:
                m70 m70Var3 = x60Var.t.b;
                if (m70Var3.g().o == dj0.e) {
                    f2 = m70Var3.g().f() & 4294967295L;
                } else {
                    f2 = m70Var3.g().f() >> 32;
                }
                int i2 = (int) f2;
                m70 m70Var4 = x60Var.t.b;
                return Float.valueOf(i2 - ((-m70Var4.g().l) + m70Var4.g().p));
        }
    }
}
